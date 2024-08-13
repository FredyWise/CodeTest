package com.fredywise.freshfruitapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> welcome() {
        return ResponseEntity.ok(ResponseMapper.toSuccessResponse("Welcome fredy wise api!"));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBuahs() {
        List<Buah> products = apiService.getAllBuahs();
        return ResponseEntity.ok(ResponseMapper.toResponse(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBuahById(@PathVariable int id) {
        Buah product = apiService.getBuahById(id);
        if (product == null) {
            return ResponseEntity.status(404).body(ResponseMapper.toErrorResponse("Buah not found"));
        }
        return ResponseEntity.ok(ResponseMapper.toResponse(product));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addBuah(@RequestBody Buah product) {
        product.setSoftDelete(false);
        int productId = apiService.addBuah(product);
        return ResponseEntity.ok(ResponseMapper.toSuccessResponse("Buah with ID " + productId + " added."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBuah(@PathVariable int id, @RequestBody Buah product) {
        product.setSoftDelete(false);
        boolean isUpdated = apiService.updateBuah(id, product);
        if (!isUpdated) {
            return ResponseEntity.status(404).body(ResponseMapper.toErrorResponse("Buah not found"));
        }
        return ResponseEntity.ok(ResponseMapper.toSuccessResponse("Buah with ID " + id + " updated."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBuah(@PathVariable int id) {
        boolean isDeleted = apiService.deleteBuah(id); // Perform actual deletion
        if (!isDeleted) {
            return ResponseEntity.status(404).body(ResponseMapper.toErrorResponse("Buah not found"));
        }
        return ResponseEntity.ok(ResponseMapper.toSuccessResponse("Buah with ID " + id + " deleted."));
    }

    @PutMapping("softDelete/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteBuah(@PathVariable int id) {
        boolean isDeleted = apiService.softDeleteBuah(id); // Implement soft delete logic in ApiService
        if (!isDeleted) {
            return ResponseEntity.status(404).body(ResponseMapper.toErrorResponse("Buah not found"));
        }
        return ResponseEntity.ok(ResponseMapper.toSuccessResponse("Buah with ID " + id + " soft deleted."));
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBuah(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("searchTerm");
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseMapper.toErrorResponse("Request body is missing"));
        }

        if (searchTerm.matches(".*[^a-zA-Z0-9 ].*")) {
            return ResponseEntity.badRequest().body(ResponseMapper.toErrorResponse("Invalid input"));
        }

        List<Buah> results = apiService.searchBuah(searchTerm);
        return ResponseEntity.ok(ResponseMapper.toResponse(results));
    }
}
