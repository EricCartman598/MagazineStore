package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import com.epam.lab.magazineStore.domain.User;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents functionality, that can be made with user cart.
 *
 * @author Daniil_Gorbach on 5/27/2019
 */
public class CartService {
    private OrderService orderService;
    private UserService userService;
    private LibraryService libraryService;

    public CartService() {
        orderService = new OrderService();
        userService = new UserService();
        libraryService = new LibraryService();
    }

    /**
     * @param magazinesCart cart with magazines and amounts
     * @param userId        user identification
     */
    public void add(Map<Magazine, Integer> magazinesCart, Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setPrice(calculateOrderPrice(magazinesCart));
        Long generatedId = orderService.add(order);
        orderService.add(generatedId, magazinesCart);
        libraryService.addWithChecking(userId,magazinesCart);
    }

    /**
     * Calculate user balance after order creation.
     *
     * @param magazinesCart cart with magazines and amounts
     * @param userId        user identification
     * @return new user balance
     */
    public BigDecimal calculateAndSetUserBalance(Map<Magazine, Integer> magazinesCart, Long userId) {
        User user = userService.findById(userId);
        BigDecimal newBalance = user.getBalance().subtract(calculateOrderPrice(magazinesCart));
        user.setBalance(newBalance);
        userService.update(user);

        return newBalance;
    }

    /**
     * Returns the total price of cart.
     *
     * @param magazinesCart cart to calculate price
     * @return total cart price
     */
    protected BigDecimal calculateOrderPrice(Map<Magazine, Integer> magazinesCart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Magazine magazine : magazinesCart.keySet()) {
            BigDecimal magazinePrice = magazine.getPrice();
            BigDecimal itemCost = magazinePrice.multiply(new BigDecimal(magazinesCart.get(magazine)));
            totalPrice = totalPrice.add(itemCost);
        }
        return totalPrice;
    }
}
