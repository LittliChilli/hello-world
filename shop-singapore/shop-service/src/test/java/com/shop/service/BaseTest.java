package com.shop.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基本类
 * 
 * @version
 * @author zousheng
 * @date:2016年11月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/applicationContext*.xml")
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

}
