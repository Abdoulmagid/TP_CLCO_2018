package org.constantine.resto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @NotNull
    @Size(max = 100)
    private String street;

    @NotNull
    @Size(max = 6)
    private String zip;

    @NotNull
    @Size(max = 100)
    private String city;

}
