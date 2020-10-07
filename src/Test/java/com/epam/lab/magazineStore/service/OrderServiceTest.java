package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.dao.OrderDao;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    @Spy
    OrderService orderService;

    @Mock
    OrderDao orderDao;

    @Captor
    ArgumentCaptor<Long> longCaptor1;
    @Captor
    ArgumentCaptor<Long> longCaptor2;
    @Captor
    ArgumentCaptor<Integer> integerCaptor;

    private Order order;
    private List<Order> orders;
    private Magazine magazine1;
    private Magazine magazine2;
    private Map<Magazine, Integer> magazineIntegerMap;
    private Long orderId = 1L;
    private Long magazineId1 = 1L;
    private Long magazineId2 = 2L;
    private Integer magazineAmount = 2;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        order = new Order();
        orders = new ArrayList<>();
        magazine1 = new Magazine();
        magazine2 = new Magazine();
        magazineIntegerMap = new HashMap<>();
    }

    @Test
    public void add_notNullOrder() {
        order.setId(orderId);
        orderService.add(order);
        when(orderDao.addOrder(order)).thenReturn(orderId);
        Long retrievedOrderId = orderService.add(order);

        assertEquals(orderId, retrievedOrderId);
    }

    @Test
    public void add_nullOrder() {
        orderService.add(null);

        verify(orderDao, never()).addOrder(any());
    }

    @Test
    public void add_withMagazineList_notNull() {
        magazine1.setId(magazineId1);
        magazine2.setId(magazineId2);
        magazineIntegerMap.put(magazine1, magazineAmount);
        magazineIntegerMap.put(magazine2, magazineAmount);
        orderService.add(orderId, magazineIntegerMap);

        verify(orderDao, times(2)).addOrderMagazine(longCaptor1.capture(), longCaptor2.capture(), integerCaptor.capture());
        List<Long> capturedOrderIds = longCaptor1.getAllValues();
        List<Long> capturedMagazinesIds = longCaptor2.getAllValues();
        List<Integer> capturedValues = integerCaptor.getAllValues();

        assertEquals(orderId, capturedOrderIds.get(0));
        assertEquals(magazineId1, capturedMagazinesIds.get(0));
        assertEquals(magazineAmount, capturedValues.get(0));
    }

    @Test
    public void add_withMagazineList_nullOrderId() {
        magazine1.setId(magazineId1);
        magazineIntegerMap.put(magazine1, magazineAmount);
        orderService.add(null, magazineIntegerMap);

        verify(orderDao, never()).addOrderMagazine(anyLong(), anyLong(), anyInt());
    }

    @Test
    public void add_withMagazineList_nullMagazinesList() {
        orderService.add(orderId, null);

        verify(orderDao, never()).addOrderMagazine(anyLong(), anyLong(), anyInt());
    }

    @Test
    public void add_withMagazineList_emptyMagazinesList() {
        orderService.add(orderId, magazineIntegerMap);

        verify(orderDao, never()).addOrderMagazine(anyLong(), anyLong(), anyInt());
    }

    @Test
    public void getById_validOrderId() {
        when(orderDao.getOrderById(orderId)).thenReturn(order);
        Order retrievedOrder = orderService.getById(orderId);

        assertEquals(order, retrievedOrder);
    }

    @Test
    public void getById_nullOrderId() {
        Order retrievedOrder = orderService.getById(null);

        assertNull(retrievedOrder);
    }

    @Test
    public void getAll() {
        when(orderDao.getAllOrders()).thenReturn(orders);
        List<Order> retrievedList = orderService.getAll();

        assertEquals(orders, retrievedList);
    }
}
