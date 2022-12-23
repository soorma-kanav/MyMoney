package org.mymoney.service;

import org.mymoney.entity.Investment;
import org.mymoney.entity.LedgerPassbook;
import org.mymoney.enums.FundType;
import org.mymoney.utils.Util;

import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class PortfolioManager {
    private final ConcurrentHashMap<FundType, Investment> portfolio;
    private TreeMap<Month, LedgerPassbook> passbook;

    public PortfolioManager() {
        this.portfolio = new ConcurrentHashMap<FundType, Investment>();
        initialisePassbook();
    }

    private void initialisePassbook() {
        this.passbook = new TreeMap<Month, LedgerPassbook>();
        passbook.put(Month.JANUARY, new LedgerPassbook());
    }

    public void addAllocationToPortFolio(FundType fundType, int investmentAmount) {
        this.portfolio.put(fundType, new Investment(investmentAmount));
        Optional<LedgerPassbook> ledgerPassbook = Optional.ofNullable(this.passbook.get(Month.JANUARY));
        if (ledgerPassbook.isPresent()) ledgerPassbook.get().addEntry(fundType, investmentAmount);
    }

    public void computeAllocationShares() {
        int totalCorpus = passbook.get(Month.JANUARY).getMonthlyBalance();
        float validator = 0;
        for (Map.Entry<FundType, Investment> entry : portfolio.entrySet()) {
            FundType fundType = entry.getKey();
            Investment investment = entry.getValue();
            float sharePercentage = Util.roundOffFloat((float) investment.getStartingAmount() / totalCorpus);
            validator += sharePercentage;
            investment.setSharePercentage(sharePercentage);
        }

    }

    public void setSip(FundType fundType, int sipAmmount) {
        Optional<Investment> investment = Optional.ofNullable(portfolio.get(fundType));
        if (investment.isPresent()) {
            investment.get().setSipAmount(sipAmmount);
        }
    }

    public void adjustChange(Month month, FundType fundType, float change) {
        int currentMonthBalance = 0;
        currentMonthBalance += getPreviousMonthCarryForward(month, fundType);
        LedgerPassbook currentMonthLedgerPassbook = Optional.ofNullable(this.passbook.get(month)).orElseGet(() -> {
            this.passbook.put(month, new LedgerPassbook());
            return this.passbook.get(month);
        });
        if (currentMonthLedgerPassbook != null) {
            currentMonthBalance += currentMonthLedgerPassbook.readValueForFund(fundType);
            currentMonthBalance += applySipIfApplicable(month, fundType);
            currentMonthBalance = (int) Math.floor(Util.roundOffFloat(currentMonthBalance * change));
            currentMonthLedgerPassbook.addEntry(fundType, currentMonthBalance);
        }
    }

    private int getPreviousMonthCarryForward(Month month, FundType fundType) {
        int previousMonthCarryForward = 0;
        if (month.compareTo(Month.JANUARY) > 0) {
            Month previousMonth = month.minus(1);
            Optional<LedgerPassbook> previousMonthLedgerPassbook = getNextMonthLedgerPassbook(previousMonth);
            if (previousMonthLedgerPassbook.isPresent())
                previousMonthCarryForward = previousMonthLedgerPassbook.get().readValueForFund(fundType);
        }
        return previousMonthCarryForward;
    }

    private int applySipIfApplicable(Month month, FundType fundType) {
        int sipAddition = 0;
        if (month.compareTo(Month.JANUARY) > 0) {
            sipAddition = this.portfolio.get(fundType).getSipAmount();
        }
        return sipAddition;
    }

    private Optional<LedgerPassbook> getNextMonthLedgerPassbook(Month currentMonth) {
        Optional<LedgerPassbook> currentMonthLedgerPassbook = Optional.ofNullable(this.passbook.get(currentMonth));
        if (currentMonthLedgerPassbook.isEmpty()) {
            this.passbook.put(currentMonth, new LedgerPassbook());
            currentMonthLedgerPassbook = Optional.ofNullable(this.passbook.get(currentMonth));
        }
        return currentMonthLedgerPassbook;
    }

    public int getBalance(Month month) {
        int balance = 0;
        Optional<LedgerPassbook> ledgerPassbook = Optional.ofNullable(this.passbook.get(month));
        if (ledgerPassbook.isPresent()) {
            balance = ledgerPassbook.get().getMonthlyBalance();
        }
        return balance;
    }

    public void printBalance(Month month) {
        Optional<LedgerPassbook> ledgerPassbook = Optional.ofNullable(this.passbook.get(month));
        if (ledgerPassbook.isPresent()) {
            for (Map.Entry entry : ledgerPassbook.get().getLedgerEntries()) {
                System.out.print(entry.getValue() + " ");
            }
            System.out.println();
        }
    }

    public Month getLatestMonth() {
        return passbook != null ? passbook.lastKey() : null;
    }

    public void rebalance() {
        Month latestMonth = passbook.lastKey();
        if (passbook.size() >= 6 && (latestMonth == Month.JUNE || latestMonth == Month.DECEMBER)) {
            int balance = getBalance(latestMonth);
            for (Map.Entry<FundType, Investment> fundTypeInvestmentEntry : portfolio.entrySet()) {
                FundType fundType = fundTypeInvestmentEntry.getKey();
                Investment investment = fundTypeInvestmentEntry.getValue();
                int changedValue = (int) Math.floor(Util.roundOffFloat(balance * investment.getSharePercentage()));
                this.passbook.get(latestMonth).updateEntry(fundType, changedValue);
            }
        }
        getBalance(latestMonth);
    }

    public ConcurrentHashMap<FundType, Investment> getPortfolio() {
        return portfolio;
    }

    public TreeMap<Month, LedgerPassbook> getPassbook() {
        return passbook;
    }

    @Override
    public String toString() {
        return "PortfolioManager{" +
                "portfolio=" + portfolio.toString() +
                ",\npassbook=" + passbook.toString() +
                '}';
    }
}
