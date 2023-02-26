package com.sergio.main.model.datasource.exceptions;

import java.io.IOException;

public class ActionFailedException extends IOException {

    public ActionFailedException(String message){
        super(message);
    }

    public ActionFailedException() {

    }
}
