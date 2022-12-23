package org.mymoney.entity;

import org.mymoney.enums.FundType;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LedgerPassbook {

    private int monthlyBalance;
    private final TreeMap<FundType, Integer> ledger;

    public LedgerPassbook() {
        this.ledger = new TreeMap<>();
    }

    public void addEntry(FundType fundType, Integer value) {
        this.ledger.put(fundType, value);
        monthlyBalance += value;
    }

    public void updateEntry(FundType fundType, Integer value) {
        int diff = value - this.ledger.get(fundType);
        this.ledger.replace(fundType, value);
        monthlyBalance += diff;
    }

    public int readValueForFund(FundType fundType) {
        int value = 0;
        if (this.ledger.containsKey(fundType)) value = this.ledger.get(fundType);
        return value;
    }

    public int getMonthlyBalance() {
        return monthlyBalance;
    }

    public Set<Map.Entry<FundType, Integer>>

    getLedgerEntries() {
        return ledger.entrySet();
    }

    @Override
    public String toString() {
        return "LedgerPassbook{" +
                "monthlyBalance=" + monthlyBalance +
                ", ledger=" + ledger.toString() +
                '}';
    }
}
