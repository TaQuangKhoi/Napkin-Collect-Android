package com.taquangkhoi.napkin.utils;

import static com.taquangkhoi.napkin.utils.testInternetConnection.testHttpURLConnection;

public class SendThoughtThread extends Thread {
    @Override
    public void run() {
        super.run();
        testHttpURLConnection();
    }
}
