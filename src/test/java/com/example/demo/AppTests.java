package com.example.demo;

import com.example.demo.aws.kms.IKms;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.core.SdkBytes;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class AppTests {

	@Autowired
	IKms kms;

	@Test
	void kms_test() {

		String key_id = "alias/demo_cmk";

		try {

			List<Object> list = kms.encode(key_id, "kms_helloworldf");
			Optional<String> optStrData = kms.decode((SdkBytes)list.get(0), (String)list.get(1));
			String strData = optStrData.get();
			System.out.println("strData: %s".format(strData));

		}catch(Exception e){
			e.printStackTrace();
		}

	}
}