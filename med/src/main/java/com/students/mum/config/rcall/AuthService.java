package com.students.mum.config.rcall;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

	public String getToken() {
//		String username = "2";
//		String password = "2";

		String url = "http://localhost:8081/login";
		HttpHeaders requestHeaders = new HttpHeaders();
//		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("username", "2");
		body.add("password", "2");
		// request entity is created with request headers
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity.getHeaders().getFirst("token");
		}
		return null;
	}
}
