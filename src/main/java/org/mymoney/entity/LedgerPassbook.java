package org.mymoney;

import java.util.concurrent.ConcurrentHashMap;

public class LedgerPassbook {

    private int monthlyBalance;
    private ConcurrentHashMap<FundType,Integer> ledger;

    public LedgerPassbook() {
        this.ledger = new ConcurrentHashMap<>();
    }

    public void addEntry(FundType fundType, Integer value){
        this.ledger.put(fundType,value);
        monthlyBalance += value;
    }

    public int readValueForFund(FundType fundType){
        return this.ledger.get(fundType);
    }

    public int getMonthlyBalance(){
        return monthlyBalance;
    }

    public void replaceEntry(FundType fundType, Integer value){
        Integer replace = this.ledger.replace(fundType, value);
    }
}
