package com.epam.lab.magazineStore.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents {@code Order} entity
 *
 * @author Daniil_Gorbach on 5/17/2019
 */
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal price;
    private LocalDateTime created;

    public Order() {
    }

    public Order(Long id, Long userId, BigDecimal price, LocalDateTime dateTime) {
        this.id = id;
        this.userId = userId;
        this.price = price;
        this.created = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime dateTime) {
        this.created = dateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", price=" + price +
                ", dateTime=" + created +
                '}';
    }
}
