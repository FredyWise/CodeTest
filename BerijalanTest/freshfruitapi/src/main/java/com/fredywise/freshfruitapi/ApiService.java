package com.fredywise.freshfruitapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    private BuahRepository productRepository;

    public List<Buah> getAllBuahs() {
        return productRepository.findAll().stream()
                .filter(buah -> !buah.getSoftDelete())
                .toList();
    }

    public Buah getBuahById(int id) {
        Buah buah = productRepository.findById(id).orElse(null);
        return (buah != null && !buah.getSoftDelete()) ? buah : null;
    }

    public int addBuah(Buah product) {
        Buah savedBuah = productRepository.save(product);
        return savedBuah.getId();
    }

    public boolean updateBuah(int id, Buah product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public boolean deleteBuah(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean softDeleteBuah(int id) {
        Buah buah = productRepository.findById(id).orElse(null);
        if (buah != null) {
            buah.setSoftDelete(true);
            productRepository.save(buah);
            return true;
        }
        return false;
    }

    public List<Buah> searchBuah(String searchTerm) {
        // Fetch only non-soft-deleted Buah objects matching the search term
        return productRepository.findByNameContainingIgnoreCase(searchTerm)
                .stream()
                .filter(buah -> !buah.getSoftDelete())
                .toList();
    }
}