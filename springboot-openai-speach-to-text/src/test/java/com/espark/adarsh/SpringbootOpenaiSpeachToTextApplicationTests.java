package com.espark.adarsh;

import com.espark.adarsh.bean.SpeechToTextResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootOpenaiSpeachToTextApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	public void test() throws JsonProcessingException {
		String input = """
				{
				  "text" : "Hello, Adarsh is my name, Shakti is my daughter.",
				  "usage" : {
				    "type" : "duration",
				    "seconds" : 12
				  }
				}
				""";
		SpeechToTextResponse speechToTextResponse = new ObjectMapper().readValue(input, new TypeReference<SpeechToTextResponse>() {
		});
		System.out.println(speechToTextResponse);
	}
}
