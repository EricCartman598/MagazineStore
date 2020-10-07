package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import com.epam.lab.magazineStore.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @InjectMocks
    @Spy
    CartService cartService;

    @Mock
    UserService userService;
    @Mock
    OrderService orderService;

    @Captor
    ArgumentCaptor<User> userCaptor;
    @Captor
    ArgumentCaptor<Order> orderCaptor;
    @Captor
    ArgumentCaptor<Map<Magazine, Integer>> cartCaptor;
    @Captor
    ArgumentCaptor<Long> longCaptor;

    private User user;
    private Map<Magazine, Integer> magazinesCart;
    private Long userId = 1L;
    private Long orderId = 1L;
    private BigDecimal userBalance = new BigDecimal(1.18);
    private BigDecimal cartPrice = new BigDecimal(1.0);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User();
        magazinesCart = new HashMap<>();
    }

    @Test
    public void add() {
        doReturn(cartPrice).when(cartService).calculateOrderPrice(magazinesCart);
        doReturn(orderId).when(orderService).add(any());

        cartService.add(magazinesCart, userId);

        verify(orderService).add(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        verify(orderService).add(longCaptor.capture(), cartCaptor.capture());
        Long capturedId = longCaptor.getValue();
        Map<Magazine, Integer> capturedCart = cartCaptor.getValue();

        assertEquals(userId, capturedOrder.getUserId());
        assertEquals(cartPrice, capturedOrder.getPrice());
        assertEquals(orderId, capturedId);
        assertEquals(magazinesCart, capturedCart);
    }

    @Test
    public void calculateAndSetUserBalance() {
        user.setBalance(userBalance);
        when(userService.findById(userId)).thenReturn(user);
        doReturn(cartPrice).when(cartService).calculateOrderPrice(magazinesCart);
        BigDecimal retrievedBalance = cartService.calculateAndSetUserBalance(magazinesCart, userId);

        verify(userService).update(userCaptor.capture());
        User updatedUser = userCaptor.getValue();

        assertEquals(userBalance.subtract(cartPrice), retrievedBalance);
        assertEquals(userBalance.subtract(cartPrice), updatedUser.getBalance());
    }
}
