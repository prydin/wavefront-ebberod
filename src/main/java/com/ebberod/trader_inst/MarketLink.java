package com.ebberod.trader_inst;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;

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

    private final Timer t_trades = MetricHelper.getRegistry().timer("MarketLink.placeOrder.time");

    private final Timer t_sendReqeust = MetricHelper.getRegistry().timer("MarketLink.placeOrder.sendRequest");

    private final Timer t_waitReply = MetricHelper.getRegistry().timer("MarketLink.placeOrder.waitReply");

    /**
     * Places an order and waits for a response.
     */
    public String placeOrder(String symbol, TXType txType, int amount) throws InterruptedException {
        Timer.Context ctx = t_trades.time();
        try {
            String token = sendRequest(symbol, txType, amount);
            return waitReply(token) + " " + symbol + " " + txType + " " + amount;
        } finally {
            ctx.stop();
        }
    }

    /**
     * Sends an order and returns a token we can use to wait for the result
     */
    private String sendRequest(String symbol, TXType txType, int amount) throws InterruptedException {

        Timer.Context ctx = t_sendReqeust.time();

        try {
            // Simulate a problem with NASDAQ being 20 times slower than NYSE
            //
            if (symbol.startsWith("NASDAQ:")) {
                Thread.sleep((long) Math.abs(r.nextGaussian()) * 200);
            } else {
                Thread.sleep((long) Math.abs(r.nextGaussian()) * 10);
            }
            return symbol + "." + UUID.randomUUID().toString();
        } finally {
            ctx.stop();
        }
    }

    /**
     * Waits for completion and returns outcome
     */
    private String waitReply(String token) throws InterruptedException {
        Timer.Context ctx = t_waitReply.time();
        try {
            Thread.sleep((long) (Math.abs(r.nextGaussian()) * 10 +
                    (50 * ( 1 + Math.sin(2 * Math.PI * System.currentTimeMillis() / 86400000)))));
            int successProb = token.startsWith("NASDAQ") ? 75 : 95;
            return r.nextInt(100) > successProb ? "FAILURE" : "OK";
        } finally {
            ctx.stop();
        }
    }
}
