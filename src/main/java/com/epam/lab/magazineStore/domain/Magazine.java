package com.epam.lab.magazineStore.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents magazine domain model for {@code Magazine}
 *
 * @author Aleksandr_Samsonov
 */
public class Magazine {
    private Long id;
    private String name;
    private String description;
    private Integer periodDays;
    private BigDecimal price;

    public Magazine() {
    }

    public Magazine(String name, String description, Integer periodDays, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.periodDays = periodDays;
        this.price = price;
    }

    public Magazine(Long id, String name, String description, Integer periodDays, BigDecimal price) {
        this(name, description, periodDays, price);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(Integer periodDays) {
        this.periodDays = periodDays;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Magazine:{ " +
                "id:" + id + "\n" +
                "name:" + name + "\n" +
                "description:" + description + "\n" +
                "periodDays:" + periodDays + "\n" +
                "price:" + price + "}" + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Magazine magazine = (Magazine) o;
        return id.equals(magazine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
