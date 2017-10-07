package com.ebberod.trader;

import java.util.Random;

/**
 * This class encapsulates the Bank of Ebberød's proprietary trading algorithm. It implements the
 * top secret un-weighted Monte Carlo-method which is, in essence just picking stocks at random.
 */
public class Trader {
    private Random r = new Random();

    public Trader(MarketLink market) {
        this.market = market;
    }

    private MarketLink market;

    private final String[] stockSymbols = new String[] {
            "NYSE:VMW", "NASDAQ:AMZN", "NASDAQ:MSFT", "NYSE:IBM", "NYSE:HPE", "NYSE:ORCL", "NASDAQ:CSCO"
    };

    /**
     * Place an order using our state-of-the art machine trading algorithm.
     */
    public String makeTrade() throws InterruptedException {
        return market.placeOrder(pickStock(), pickTxType(), pickAmount());
    }

    private String pickStock() throws InterruptedException {
        // All great thinkers need to take their time, so we wait a bit before we make a decision
        //
        Thread.sleep((long) Math.abs(r.nextGaussian()) * 10);

        // Recalculate decontribulators if needed
        //
        if((System.currentTimeMillis() / 60000) % 15 == 0) {
            recalculateDecontribulators();
        }

        // Now it's time to make our important decision using the finest math!
        //
        return stockSymbols[r.nextInt(stockSymbols.length)];
    }

    private int pickAmount() {
        // Another amazingly sophisticated algorithm!
        //
        return r.nextInt(100000);
    }

    private MarketLink.TXType pickTxType() {
        // Buy or sell? Only the random number generator knows!
        //
        return r.nextInt(10) > 5 ? MarketLink.TXType.BUY: MarketLink.TXType.SELL;
    }

    private void recalculateDecontribulators() throws InterruptedException {
        // Very complicated calculation going on here!!
        //
        Thread.sleep(500 + (long) Math.abs(r.nextGaussian()) * 10);
    }
}
