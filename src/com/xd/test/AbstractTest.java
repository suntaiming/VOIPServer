package com.xd.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public abstract class AbstractTest {
	
	/**
	 * 这是测试逻辑执行前要执行的代码
	 */
	@Before
	abstract public void before();
	
	/**
	 * 这是测试逻辑执行后要执行的代码
	 */
	@After
	abstract public void after();

}
