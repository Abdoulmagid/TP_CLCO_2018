package org.constantine.resto.model;

public class RestaurantDetail {

    private final Long id;
    private final String name;
    private final String description;
    private final Address address;
    private final Double ratings;
    private final int totalRatings;

    public RestaurantDetail(Long id, String name, String description, Address address, Double ratings, Long totalRatings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.ratings = ratings == null ? 0 : ratings;
        this.totalRatings = totalRatings == null ? 0 : totalRatings.intValue();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    public Double getRatings() {
        return ratings;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

}
