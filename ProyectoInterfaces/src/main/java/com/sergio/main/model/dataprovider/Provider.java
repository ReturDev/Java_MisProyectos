package com.sergio.main.model.dataprovider;

public class Provider {

    private static final int REQUESTS_PER_MINUTE = 60;
    private static final int REQUEST_PER_SECOND = 3;
    private long lastRequest;
    private int requestsLastSecond;
    private int requestsLastMinute;

    private void newRequest(){

        requestsLastSecond++;
        requestsLastMinute++;
        lastRequest = System.currentTimeMillis();

    }

    private void reset(){

        requestsLastSecond = 0;
        requestsLastMinute = 0;

    }

    private void checkRequestAvailable(){





    }


}
