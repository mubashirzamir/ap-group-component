package com.sedm.app;

import com.sedm.app.api.CitizenController;
import com.sedm.app.bean.Reading;
import com.sedm.app.service.CustomerService;
import com.sedm.app.service.ReadingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CitizenAppApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	@Autowired
	private CitizenController citizenController;

	@Mock
	private CustomerService customerService;

	@Mock
	@Autowired
	private ReadingService readingService;
	@Mock
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testgenerateApi(){

		String msg = citizenController.generateApi();
		Assertions.assertEquals("HELLO WORLD",msg);

	}




}
