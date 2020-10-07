package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.dao.LibraryDao;
import com.epam.lab.magazineStore.dao.UserDao;
import com.epam.lab.magazineStore.domain.Library;
import com.epam.lab.magazineStore.domain.Magazine;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class LibraryService {
    private LibraryDao libraryDao;
    private UserDao userDao;

    public LibraryService() {
        libraryDao = new LibraryDao();
        userDao = new UserDao();
    }

    public void add(Library library) {
        if (library != null) {
            libraryDao.addMagazineToLibrary(library);
        }
    }

    public void addWithChecking(Long userId, Map<Magazine, Integer> magazineCart) {
        if (userId != null && magazineCart != null) {
            for (Magazine magazine : magazineCart.keySet()) {
                Library library = new Library();
                library.setUserId(userId);
                library.setMagazineId(magazine.getId());

                if (isMagazineNew(userId, magazine)) {
                    library.setExpiration(calculateNewExpiration(magazine.getPeriodDays(), magazineCart.get(magazine)));
                    add(library);
                } else {
                    Map<Magazine, Timestamp> userLibrary = userDao.getUserLibrary(userId);
                    Timestamp expirationDate = userLibrary.get(magazine);
                    if (magazineExpired(expirationDate)) {
                        library.setExpiration(calculateNewExpiration(magazine.getPeriodDays(), magazineCart.get(magazine)));
                    } else {
                        library.setExpiration(calculateFromOldExpiration(magazine.getPeriodDays(), magazineCart.get(magazine), expirationDate));
                    }
                    updateLibrary(library);
                }
            }
        }
    }

    public List<Library> getAll() {
        return libraryDao.getAll();
    }

    public void updateLibrary(Library library) {
        if (library != null) {
            libraryDao.updateLibrary(library);
        }
    }

    private LocalDateTime calculateFromOldExpiration(Integer periodDays, Integer amount, Timestamp currentDate) {
        long daysToAdd = (long) periodDays * amount;
        LocalDateTime currentLocalDateTime = currentDate.toLocalDateTime();
        return currentLocalDateTime.plusDays(daysToAdd);
    }

    private LocalDateTime calculateNewExpiration(Integer periodDays, Integer amount) {
        long daysToAdd = (long) periodDays * amount;
        return LocalDateTime.now().plusDays(daysToAdd);
    }

    private Boolean magazineExpired(Timestamp currentDate) {
        LocalDateTime currentLocalDateTime = currentDate.toLocalDateTime();
        return currentLocalDateTime.isBefore(LocalDateTime.now());
    }

    private Boolean isMagazineNew(Long userId, Magazine magazine) {
        Map<Magazine, Timestamp> userLibrary = userDao.getUserLibrary(userId);
        return !userLibrary.containsKey(magazine);
    }
}