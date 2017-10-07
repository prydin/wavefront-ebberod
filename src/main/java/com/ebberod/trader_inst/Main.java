package com.ebberod.trader_inst;

public class Main {

    public static void main(String[] args) {
        // Our algorithm is a money-making machine, so all we have to do is to run it
        // forever and the cash should roll in!
        //
        MarketLink market = new MarketLink();
        Trader trader = new Trader(market);
        try {
            for (; ; ) {
                String result = trader.makeTrade();
                System.err.println("Traded result was: " + result);
            }
        } catch (InterruptedException e) {
            System.err.println("The fun is over");
        }
    }
}
