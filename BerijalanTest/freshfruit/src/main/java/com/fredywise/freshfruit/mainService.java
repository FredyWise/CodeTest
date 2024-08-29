package com.fredywise.freshfruit;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class mainService implements IMainService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_BASE_URL = "http://localhost:8081/api/products";
    private final String API_KEY = "7def4ec4deab71e2c5911ee718db181c8bf077582e9cc397af95c76fb0d459f0";

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.set("x-content-type-options", "nosniff");
        headers.set("x-xss-protection", "1; mode=block");
        headers.set("strict-transport-security", "max-age=31536000; includeSubDomains");
        headers.set("x-frame-options", "DENY");
        return headers;
    }

    private <T> Buah[] processApiResponse(ResponseEntity<ApiResponse> response) {
        ApiResponse apiResponse = response.getBody();
        if (apiResponse != null && "T".equals(apiResponse.getOutStat())) {
            return apiResponse.getOutData();
        } else {
            throw new RuntimeException(
                    "API error: " + (apiResponse != null ? apiResponse.getOutMess() : "Unknown error"));
        }
    }

    @Override
    public List<Buah> fetchAllBuah() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, entity,
                ApiResponse.class);
        return Arrays.asList(processApiResponse(response));
    }

    @Override
    public Buah fetchBuahById(int id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(API_BASE_URL + "/" + id, HttpMethod.GET, entity,
                ApiResponse.class);
        return Arrays.asList(processApiResponse(response)).get(0);

    }

    @Override
    public void saveBuah(Buah buah) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Buah> entity = new HttpEntity<>(buah, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(API_BASE_URL, HttpMethod.POST, entity,
                ApiResponse.class);
        processApiResponse(response);
    }

    @Override
    public void updateBuah(int id, Buah buah) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Buah> entity = new HttpEntity<>(buah, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(API_BASE_URL + "/" + id, HttpMethod.PUT,
                entity, ApiResponse.class);
        processApiResponse(response);
    }

    @Override
    public void deleteBuah(int id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(API_BASE_URL + "/softDelete/" + id, HttpMethod.PUT,
                entity, ApiResponse.class);
        processApiResponse(response);
    }

    @Override
    public List<Buah> searchBuah(String searchTerm) {
        System.out.println("Start: " + searchTerm);
        HttpHeaders headers = createHeaders();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("searchTerm", searchTerm);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_BASE_URL + "/search")
                .queryParam("searchTerm", searchTerm);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
                entity, ApiResponse.class);

        System.out.println(Arrays.asList(processApiResponse(response)).toString());

        return Arrays.asList(processApiResponse(response));
    }
}
