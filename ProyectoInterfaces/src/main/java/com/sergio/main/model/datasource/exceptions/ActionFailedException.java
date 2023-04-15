package com.sergio.main.model.datasource.exceptions;

import java.io.IOException;


/**
 * Excepción al no poderse completar una acción realizada.
 */
public class ActionFailedException extends IOException {

    public ActionFailedException(String message){
        super(message);
    }

    public ActionFailedException() {

    }
}
