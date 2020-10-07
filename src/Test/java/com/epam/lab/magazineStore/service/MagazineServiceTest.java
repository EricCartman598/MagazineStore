package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.dao.MagazineDao;
import com.epam.lab.magazineStore.domain.Magazine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class MagazineServiceTest {
    @InjectMocks
    @Spy
    MagazineService magazineService;

    @Mock
    MagazineDao magazineDao;

    private Magazine magazine;
    private List<Magazine> magazines;
    private Long magazineId = 1L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        magazine = new Magazine();
        magazines = new ArrayList<>();
    }

    @Test
    public void add_nullMagazine() {
        magazineService.add(null);
        verify(magazineDao, never()).addMagazine(any());
    }


    @Test
    public void getById_validMagazineId() {
        when(magazineDao.getMagazineById(magazineId)).thenReturn(magazine);
        Magazine retrievedMagazine = magazineService.getMagazineById(magazineId);
        assertEquals(magazine, retrievedMagazine);
    }

    @Test
    public void getById_nullMagazineId() {
        Magazine recievedMagazine = magazineService.getMagazineById(null);
        assertNull(recievedMagazine);
    }

    @Test
    public void getAll() {
        when(magazineDao.getAll()).thenReturn(magazines);
        List<Magazine> retrievedList = magazineService.getAll();
        assertEquals(magazines, retrievedList);
    }


}
