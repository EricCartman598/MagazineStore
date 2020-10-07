package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.dao.MagazineDao;
import com.epam.lab.magazineStore.domain.Magazine;

import java.util.List;
import java.util.Map;

public class MagazineService {
    private MagazineDao magazineDao = new MagazineDao();

    public void add(Magazine magazine) {

        if (magazine != null) {
            magazineDao.addMagazine(magazine);
        }
    }

    public Magazine getMagazineById(Long id) {

        if (id != null) {
            return magazineDao.getMagazineById(id);
        }

        return null;
    }

    public Map<Magazine, Integer> getMagazinesByOrderId(Long orderId) {

        if (orderId != null) {
            return magazineDao.getMagazinesByOrderId(orderId);
        }

        return null;
    }

    public List<Magazine> getAll() {
        return magazineDao.getAll();
    }

    public void updateMagazine(Magazine magazine) {

        if (magazine != null) {
            magazineDao.update(magazine);
        }
    }

    public void deleteMagazineById(Long magazineId) {

        if (magazineId != null) {
            magazineDao.deleteMagazineById(magazineId);
        }
    }
}
