package org.constantine.resto.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GraduateRequest {
    @NotNull
    private Long restoId;

    @NotNull
    private Long clientId;

    @NotNull
    private int foodQuality;

    @NotNull
    private int roomQuality;

    @NotNull
    private int serviceQuality;

}
