//package com.shop.service.user.impl;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.shop.entity.basic.User;
//import com.shop.service.BaseTest;
//import com.shop.service.user.UserService;
//
//import junit.framework.Assert;
//
//public class UserServiceImplTest extends BaseTest{
//
//	@Autowired
//	private UserService userService;
//
//	@Test
//	public void test() {
//
//		User user = userService.queryUserById(132l);
//
//		if (user == null) {
//			System.err.println("dasfad");
//		} else {
//			Assert.assertEquals("123", user.getId().toString());
//		}
//
//	}
//
//}
