package com.ppteam.dao.impl;

import com.ppteam.dao.BaseDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.spring4.expression.Fields;

import javax.swing.text.html.parser.Entity;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * BaseDao实现类，通过反射及泛型实现复用<br>
 *     一具体实体类的DaoImpl需继承此基类<br>
 *     为了使用事务可由BaseDao接口继承出具体Dao接口，同时具体的DaoImpl类也需实现该接口
 * For example:
 * public class UserDaoImpl extends BaseDaoImpl &lt; UserDto &gt implements UserDao{}
 *
 * @param <T>
 *     传入实体类，默认根据属性名进行数据库字段操作
 *
 * @see BaseDao
 * @author Jeremy Tang
 */

public class BaseDaoImpl<T> implements BaseDao<T> {

    private Class<T> entityType;
    private JdbcTemplate jdbcTemplate;
    private String idFieldName;//id字段的名称

    /**
     * @param entityType
     *   实体类的Class对象，以进行实例化等操作
     * @param jdbcTemplate
     *   JdbcTemplate对象，用于数据库操作
     * @param idFieldName
     *   主键字段对应实体类的属性名
     */
    public BaseDaoImpl(Class<T> entityType, JdbcTemplate jdbcTemplate,String idFieldName) {
        this.entityType=entityType;
        this.jdbcTemplate=jdbcTemplate;
        this.idFieldName=idFieldName;
    }

    /**
     * @param t 传入的实体类对象
     * @return 返回自增id，若字段不为自增则返回值未定
     * @throws DaoUpdateFailException
     * 数据库更新失败则抛出
     */
    @Override
    public Integer add(T t) throws DaoUpdateFailException ,MoreThanOneResultException{
        try {
            //实体类的所有属性名及属性值
            Map<String,Object> namesAndFields=getFields(t);



            //用于生成请求字段名
            String queryFieldNames="";

            //用于生成问号占位符
            String marks="";

            //用于存放请求实体
            Object[] queryFieldObjects=new Object[namesAndFields.size()];

            //用于迭代
            Iterator<Map.Entry<String,Object>> iterator=namesAndFields.entrySet().iterator();
            Map.Entry<String,Object> entry;
            int i=0;
            while(iterator.hasNext()){
                entry=iterator.next();

                //拼接成请求字段名字符串
                queryFieldNames+=(entry.getKey()+",");
                //拼接成问号
                marks+="?,";

                //获取属性值
                queryFieldObjects[i]=entry.getValue();
                i++;
                //测试键值是否对应
                //System.out.println("key:"+entry.getKey()+";value:"+entry.getValue());
            }
            queryFieldNames=queryFieldNames.substring(0,queryFieldNames.length()-1);//删最后一个逗号
            marks=marks.substring(0,marks.length()-1);  //删最后一个逗号

            //测试生成的字符串及对象数组是否对应
            //System.out.println(queryFieldNames);
            //System.out.println(queryFieldObjects);

            //sql语句
            String sql="INSERT into "+
                    pascalToUnderling(entityType.getSimpleName())+
                    "("+queryFieldNames+") "+
                    "values("+marks+")";

            jdbcTemplate.update(sql,queryFieldObjects);
            System.out.println(sql);

            //查询自增id
            List<Integer> l=jdbcTemplate.query("SELECT last_insert_id()", new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getInt("last_insert_id()");
                }
            });
            if(l.size()==1){
                return l.get(0);
            }
            else if(l.size()==0){
                return null;
            }else{
                throw new MoreThanOneResultException();
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            throw  new DaoUpdateFailException();
        }
    }

    /**
     * @param id
     *   主键
     * @throws DaoUpdateFailException
     *   数据库更新失败则抛出
     */
    @Override
    public void delete(int id) throws DaoUpdateFailException {
        try {
            String sql="delete from " +
                    pascalToUnderling(entityType.getSimpleName()) +
                    " where "+humpToUnderling(idFieldName)+"=?";
            jdbcTemplate.update(sql,id);
        }catch (DataAccessException e){
            e.printStackTrace();
            throw new DaoUpdateFailException();
        }
    }

    /**
     * @param id
     *   主键
     * @return
     *   获取到的实体类对象，若无则返回null
     * @throws DaoUpdateFailException
     *   查询失败则抛出
     */
    @Override
    public T get(int id) throws MoreThanOneResultException {
        List<T> l;

        String sql = "select * from " +
                pascalToUnderling(entityType.getSimpleName()) +
                " where " + humpToUnderling(idFieldName) + "=?";
        l = jdbcTemplate.query(sql, new Object[]{id},new MyRowMapper(getTypes()));
        if(l.size()==1){
            return l.get(0);
        }
        else if(l.size()==0){
            return null;
        }
        else{
            throw new MoreThanOneResultException();
        }



    }

    /**
     * 用于查询的RowMapper
     */
    private class MyRowMapper implements RowMapper<T>{
        private Map<String,Class> namesAndTypes;

        /**
         * @param namesAndTypes 传入实体类属性名及类型
         */
        public MyRowMapper(Map<String,Class> namesAndTypes) {
            this.namesAndTypes=namesAndTypes;
        }

        @Override
        public T mapRow(ResultSet rs, int i) throws SQLException {
            T t=null;

            //用于实体类setter方法，用于resultSet的get方法
            String entityMethodName,resultSetMethodName;
            Method entityMethod,resultSetMethod,enumFromStringMethod;

            Map.Entry entry;
            Object obj;
            Iterator<Map.Entry<String,Class>> iterator=namesAndTypes.entrySet().iterator();

            try{
                t=entityType.newInstance();
                while(iterator.hasNext()){
                    entry=iterator.next();

                    //拼接成setter方法名并生成Method对象
                    entityMethodName="set"+humpToPascal(entry.getKey().toString());
                    entityMethod=entityType.getMethod(entityMethodName,(Class)entry.getValue());

                    //如果实体类某属性为枚举类，则从数据库获取String，并调用枚举类的fromString()方法
                    if(((Class)entry.getValue()).getSuperclass().getSimpleName().equals("Enum")){
                        //构成getString方法
                        resultSetMethodName="getString";
                        resultSetMethod=rs.getClass().getMethod(resultSetMethodName,String.class);

                        //从resultSet中获取对象
                        obj=resultSetMethod.invoke(rs,humpToUnderling(entry.getKey().toString()));

                        //调用枚举类的fromString方法将获得的字符串转为对应枚举类型
                        enumFromStringMethod=((Class)entry.getValue()).getMethod("fromString",String.class);
                        obj=enumFromStringMethod.invoke(null,obj);

                        //最终调用实体类的setter方法
                        entityMethod.invoke(t,obj);
                    }
                    else{
                        //某属性为Integer，则调用resultSet的getInt方法
                        if(((Class)entry.getValue()).getSimpleName().equals("Integer")){
                            resultSetMethodName="getInt";
                        }
                        else{
                            //否则直接从属性类型名拼接成get方法
                            resultSetMethodName="get"+((Class)entry.getValue()).getSimpleName();
                        }
                        //调用resultSet的get方法并放入实体类
                        resultSetMethod=rs.getClass().getMethod(resultSetMethodName,String.class);
                        obj=resultSetMethod.invoke(rs,humpToUnderling(entry.getKey().toString()));
                        entityMethod.invoke(t,obj);
                    }

                }

            }catch (InstantiationException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (NoSuchMethodException e){
                e.printStackTrace();
            }catch (InvocationTargetException e){
                e.printStackTrace();
            }
            return t;
        }
    }

    /**
     * @param t
     *   需更新的实体类对象，根据id更新
     * @throws DaoUpdateFailException
     *   更新失败则抛出
     */
    @Override
    public void update(T t) throws DaoUpdateFailException {
        try{
            Map<String,Object> namesAndFields=getFields(t);
            Iterator<Map.Entry<String,Object>> iterator=namesAndFields.entrySet().iterator();

            Object[] queryFieldObjects=new Object[namesAndFields.size()];
            Map.Entry<String,Object> entry;

            String sql="UPDATE "+ pascalToUnderling(entityType.getSimpleName())+ " SET ";
            int i=0;
            while(iterator.hasNext()){
                entry=iterator.next();
                //字段名等于id字段名则跳过
                if(entry.getKey().equals(humpToUnderling(idFieldName))){
                    //id字段放置于最后
                    queryFieldObjects[queryFieldObjects.length-1]=entry.getValue();
                    continue;
                }
                //组装sql语句
                queryFieldObjects[i]=entry.getValue();
                sql+=entry.getKey()+"=?,";
                i++;
            }
            //去掉最后一个逗号并拼接where条件语句
            sql=sql.substring(0,sql.length()-1)+" where "+humpToUnderling(idFieldName)+"=?";

            jdbcTemplate.update(sql,queryFieldObjects);
            System.out.println(sql);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            throw new DaoUpdateFailException();
        }
    }


    /**
     * 根据实体类返回表字段名与属性对象的Map，包括id
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected Map<String,Object> getFields(T t)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        Map<String,Object> namesAndFields=new LinkedHashMap<String,Object>();
        Field[] fields=entityType.getDeclaredFields();
        String methodName;
        Method method;
        Object fieldObject;
        for(Field f:fields){
            methodName="get"+humpToPascal(f.getName());
            method=entityType.getMethod(methodName);
            fieldObject=method.invoke(t);
            if(fieldObject!=null){
                if(fieldObject.getClass().getSuperclass().getSimpleName().equals("Enum")){
                    fieldObject=fieldObject.toString();
                }
            }
            namesAndFields.put(humpToUnderling(f.getName()),fieldObject);
        }
        return namesAndFields;
    }
    /*protected Object[] getFields(T t) throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        Field[] fields=entityType.getDeclaredFields();
        Object[] fieldObjects=new Object[fields.length];
        String methodName;
        Method method;
        Type type;
        Object field;
        for(int i=0;i<fields.length;i++){
            methodName="get"+humpToPascal(fields[i].getName());
            type=fields[i].getType();
            method=entityType.getMethod(methodName);
            field=method.invoke(t);
            if (field==null){

            }
            else if(field.getClass().getSuperclass().getSimpleName().equals("Enum")){
                fieldObjects[i]=field.toString();
            }
            else{
                fieldObjects[i]=field;
            }
        }
        return fieldObjects;
    }*/

    /**
     * 返回实体类字段名及对应类型名，
     * @return Map，键为实体类属性名，值为对应属性的类型名，默认只有String，基本类型包装类及枚举，枚举需转换为String
     */
    protected Map<String,Class> getTypes(){
        Field[] fields=entityType.getDeclaredFields();
        Map<String,Class> nameAndType=new LinkedHashMap<String,Class>();
        for(Field f:fields){
            nameAndType.put(f.getName(),f.getType());
        }
        return nameAndType;
    }

    /**
     * 驼峰命名法转换成下划线法，用于实体类属性名转换为表字段名
     * @param string
     * @return
     */
    protected String humpToUnderling(String string){
        StringBuffer s=new StringBuffer(string);
        for(int i=0;i<s.length();){
            if(Character.isUpperCase(s.charAt(i))){
                s.replace(i,i+1,"_"+Character.toLowerCase(s.charAt(i)));
                i+=2;
            }
            else{
                i++;
            }
        }
        return s.toString();
    }

    /**
     * 帕斯卡命名法转换为下划线法，用于类名转换为表名
     * @param string
     * @return
     */
    protected String pascalToUnderling(String string){
        String s=Character.toLowerCase(string.charAt(0))+string.substring(1);
        return humpToUnderling(s);
    }

    /**
     * 驼峰命名法转换为帕斯卡命名法，用于类属性名转换，便于拼接成getter或setter方法名
     * @param string
     * @return
     */
    protected String humpToPascal(String string){
        return Character.toUpperCase(string.charAt(0))+string.substring(1);
    }
}
