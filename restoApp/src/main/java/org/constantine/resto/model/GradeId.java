package org.constantine.resto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeId implements Serializable {

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "visitor_id")
    private Long visitorId;

}
