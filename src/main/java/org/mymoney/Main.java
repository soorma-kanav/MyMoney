package org.example;

import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        User user = new User("Kanav", "kanav0705@gmail.com");
        Fund fundEquity = new Fund("equity", FundType.EQUITY);
        Fund fundGold = new Fund("gold", FundType.GOLD);
        Fund fundDebt = new Fund("debt", FundType.DEBT);
        Portfolio portfolio = new Portfolio(user);
        int amountEquity = 6000;
        int amountGold = 3000;
        int amountDebt = 1000;

        int sum = amountEquity + amountGold + amountDebt;

        float sharePercentageEquity = Math.round(((float) amountEquity/sum));
        float sharePercentageGold = Math.round(((float) amountGold/sum));
        float sharePercentageDebt = Math.round(((float) amountDebt/sum));



/*
        Investment equity = new Investment("equity",
                sharePercentageEquity,
               Fund,
                6000,
                300,
                Month.JANUARY);
*/


    }

}