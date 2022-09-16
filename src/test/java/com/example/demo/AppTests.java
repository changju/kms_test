package com.example.demo;

import com.example.demo.aws.kms.IKms;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class AppTests {

	@Autowired
	IKms kms;

	@Test
	void kms_test() {
		String key_id = "alias/demo_cmk";
		log.debug("kms_test");
		kms.encode(key_id);

	}
}