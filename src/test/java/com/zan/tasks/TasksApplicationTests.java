package com.zan.tasks;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TasksApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void SimpleTest(){
		assertFalse(false);
	}
	
}
