package com.ppteam.dao;

import com.ppteam.dao.exceptions.*;

/**
 * BaseDao，封装基本的增删查改方法，一实体类对应一具体Dao接口，可能有多个实现类
 * @param <T> 具体Dao接口对应的实体类的类型，必须为Javabean
 * @author Jeremy Tang
 */
public interface BaseDao<T> {
    /**
     * 增方法
     * @param t 需插入对象
     * @return 返回自增主键
     * @throws DaoUpdateFailException
     */
    Integer add(T t) throws DaoUpdateFailException,MoreThanOneResultException;

    /**
     * 删方法
     * @param id
     * @throws DaoUpdateFailException
     */
    void delete(int id) throws DaoUpdateFailException;

    /**
     * 查方法
     * @param id
     * @return
     * @throws DaoUpdateFailException
     */
    T get(int id) throws DaoQueryFailException,MoreThanOneResultException;

    /**
     * 改方法
     * @param t
     * @throws DaoUpdateFailException
     */
    void update(T t) throws DaoUpdateFailException;

}
