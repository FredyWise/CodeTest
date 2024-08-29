package com.fredywise.freshfruit;

import java.util.List;

public interface IMainService {
    List<Buah> fetchAllBuah();
    Buah fetchBuahById(int id);
    void saveBuah(Buah buah);
    void updateBuah(int id, Buah buah);
    void deleteBuah(int id);
    List<Buah> searchBuah(String searchTerm);
}
