package com.fredywise.freshfruitapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseMapper {

    public static Map<String, Object> toResponse(Buah product) {
        return toResponse(List.of(product));
    }

    public static Map<String, Object> toResponse(List<Buah> products) {
        Map<String, Object> response = new HashMap<>();
        response.put("OUT_STAT", "T");
        response.put("OUT_MESS", "Success");
        response.put("OUT_DATA", products.stream().map(product -> {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.getId());
            productMap.put("name", product.getName());
            productMap.put("price", product.getPrice());
            return productMap;
        }).collect(Collectors.toList()));
        return response;
    }

    public static Map<String, Object> toSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("OUT_STAT", "T");
        response.put("OUT_MESS", message);
        response.put("OUT_DATA", List.of());
        return response;
    }

    public static Map<String, Object> toErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("OUT_STAT", "F");
        response.put("OUT_MESS", message);
        response.put("OUT_DATA", List.of());
        return response;
    }
}