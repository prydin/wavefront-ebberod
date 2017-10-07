package com.ebberod.trader_annotated;

import com.codahale.metrics.annotation.Timed;

import java.util.Random;
import java.util.UUID;

/**
 * The Ebberød Bank is, of course, a member of all the exchanges and we're connected directly
 * through a dedicate fiber, so latency should be in the low microseconds. Still, sometimes things
 * take a while and we don't know why...
 */
public class MarketLink {
    private Random r = new Random();

    public enum TXType { BUY, SELL };

    /**
     * Places an order and waits for a response.
     */
    @Timed(absolute = true)
    public String placeOrder(String symbol, TXType txType, int amount) throws InterruptedException {
        String token = sendRequest(symbol, txType, amount);
        return waitReply(token);
    }

    /**
     * Sends an order and returns a token we can use to wait for the result
     */
    @Timed(absolute = true)
    private String sendRequest(String symbol, TXType txType, int amount) throws InterruptedException {

        // Simulate a problem with NASDAQ being 20 times slower than NYSE
        //
        if(symbol.startsWith("NASDAQ:")) {
            Thread.sleep((long) Math.abs(r.nextGaussian()) * 200);
        } else {
            Thread.sleep((long) Math.abs(r.nextGaussian()) * 10);
        }
        return UUID.randomUUID().toString();
    }

    /**
     * Waits for completion and returns outcome
     */
    @Timed(absolute = true)
    private String waitReply(String token) throws InterruptedException {
        Thread.sleep((long) Math.abs(r.nextGaussian()) * 10 +
                50 * ( 1 + (long) Math.sin(2 * Math.PI * System.currentTimeMillis() / 86400000)));
        return r.nextInt(10) > 8 ? "FAILURE" : "OK";
    }
}
