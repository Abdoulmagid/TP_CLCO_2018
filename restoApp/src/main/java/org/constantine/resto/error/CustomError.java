package org.constantine.resto.error;

import lombok.Data;

@Data
public class CustomError {

    private boolean errors = true;
    private String message;

    public CustomError(String message){
        this.message = message;
    }

}
