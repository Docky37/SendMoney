package com.paymybuddy.sendmoney.security;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.sendmoney.security.controller.UserController;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SendmoneyApplicationTests {

	@Autowired
	private UserController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
