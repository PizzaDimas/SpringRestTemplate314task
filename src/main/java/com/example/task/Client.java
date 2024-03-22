package com.example.task;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    private static String url = "http://94.198.50.185:7081/api/users";

    private static String sessionId;
    private static final RestTemplate restTemplate = new RestTemplate();
    private static StringBuilder result = new StringBuilder();



    public static void main(String[] args) {
        get();
        postUser();
        putUser();
        delete();
    }

    private static void postUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", "JSESSIONID=" + sessionId);

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 18);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status code POST: " + statusCode);
        System.out.println("Response body POST: " + responseBody);
        result.append(responseBody);
        System.out.println(sessionId);
    }



    private static void putUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", "JSESSIONID=" + sessionId);

        User user = new User();
        user.setId(3L);
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte)19);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status code PUT: " + statusCode);
        System.out.println("Response body PUT: " + responseBody);
        result.append(responseBody);
        System.out.println(sessionId);

    }

    private static void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        User user = new User();
        user.setId(3L);
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte)19);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url + "/3", HttpMethod.DELETE, requestEntity, String.class);
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status code PUT: " + statusCode);
        System.out.println("Response body PUT: " + responseBody);
        result.append(responseBody);
        System.out.println(sessionId);

        System.out.println(result);
    }

    private static void get(){

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status code GET: " + statusCode);
        System.out.println("Response body GET: " + responseBody);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.startsWith("JSESSIONID=")) {
                    sessionId = cookie.substring("JSESSIONID=".length(), cookie.indexOf(';'));
                    break;
                }
            }
        }
        System.out.println(sessionId);
    }
}
