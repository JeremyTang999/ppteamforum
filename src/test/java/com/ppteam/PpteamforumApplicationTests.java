package com.ppteam;

import com.ppteam.dao.UserDao;
import com.ppteam.entity.User;
import com.ppteam.entity.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PpteamforumApplicationTests {

	@Autowired
	UserDao userDao;

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

}
