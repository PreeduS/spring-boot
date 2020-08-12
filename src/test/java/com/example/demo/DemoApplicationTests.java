package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@BeforeAll
	public static void beforeAll(){
		System.out.println("BeforeAll");
	}
	@AfterAll
	public static void afterAll(){
		System.out.println("AfterAll");
	}
	@BeforeEach 
	public void beforeEach(){
		System.out.println("BeforeEach");
	}
	@AfterEach
	public void afterEach(){
		System.out.println("AfterEach");
	}

	@Test
	void contextLoads() {
	}
	@Test
	void test() {
		assertEquals(10, 10);
		
 
	}

}
