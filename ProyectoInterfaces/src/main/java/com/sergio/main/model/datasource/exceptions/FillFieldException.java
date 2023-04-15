package com.sergio.main.model.datasource.exceptions;

import java.io.IOException;


/**
 * Excepción en el relleno de campos.
 */
public class FillFieldException extends IOException {

    public FillFieldException() {
    }

    public FillFieldException(String message) {
        super(message);
    }
}
