//package com.graduate.HealthProtector.global.template;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class CustomRestTemplate {
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public CustomRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public <T> T postForObject(String url, Object request, Class<T> responseType, String apiKey) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
//        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, entity, responseType);
//        return responseEntity.getBody();
//    }
//}
//
