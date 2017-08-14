package com.ppteam;

import com.ppteam.dao.*;
import com.ppteam.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PpteamforumApplicationTests {

	@Autowired
	UserDao userDao;

	@Autowired
	UserInfoDao userInfoDao;

	@Autowired
	UserSecurityDao userSecurityDao;

	@Test
	public void contextLoads() {
	}

	@Test
	public void userDaoTest(){
		try {

			int i=userDao.add(new User(null,"tmh",System.currentTimeMillis(),UserRole.USER));
			User u=userDao.get(i);
			System.out.println(u.getRole());
			u.setRole(UserRole.ADMIN);
			userDao.update(u);
			u=userDao.get(i);
			System.out.println(u.getRole());
			//userDao.delete(i);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void userInfoDaoTest(){
		try{
			int id=9;
			userInfoDao.add(new UserInfo(id, Gender.OTHER, "sds",null));
			UserInfo ui=userInfoDao.get(id);
			System.out.println("id:"+ui.getUserId()+";gender:"+ui.getGender()+";path:"+ui.getAvatarPath());
			ui.setGender(Gender.MALE);
			userInfoDao.update(ui);
			ui=userInfoDao.get(id);
			System.out.println("gender:"+ui.getGender());
			//userInfoDao.add(new UserInfo(null,Gender.MALE,"",null));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void transactionTest(){
		try{
			User u=new User(null,"test",System.currentTimeMillis(),UserRole.USER);
			int id=userDao.add(u);
			UserInfo ui=new UserInfo(id,Gender.FEMALE,"","");
			userInfoDao.add(ui);
			if(true) throw new Exception();
			UserSecurity us=new UserSecurity(id,"qwerty","","","","","","");
			userSecurityDao.add(us);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
