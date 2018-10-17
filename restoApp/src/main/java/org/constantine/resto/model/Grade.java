package org.constantine.resto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "grades")
public class Grade {

    @EmbeddedId
    private GradeId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("id.restaurantId")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("id.visitorId")
    @JsonIgnore
    private Visitor visitor;

    @NotNull
    private int foodQuality;

    @NotNull
    private int roomQuality;

    @NotNull
    private int serviceQuality;

    @NotNull
    @JsonIgnore
    private double quality;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "graduate_at")
    private Date graduateAt = new Date();

    public Grade() {
    }

    public Grade(Restaurant restaurant, Visitor visitor, @NotNull int foodQuality, @NotNull int roomQuality, @NotNull int serviceQuality) {
        this.restaurant = restaurant;
        this.visitor = visitor;
        this.foodQuality = foodQuality;
        this.roomQuality = roomQuality;
        this.serviceQuality = serviceQuality;
        this.quality = Math.ceil((foodQuality + roomQuality + serviceQuality) / 3);
        this.id = new GradeId(restaurant.getId(), visitor.getId());
    }

    public double getQuality() {
        return Math.ceil((foodQuality + roomQuality + serviceQuality) / 3);
    }

}
