package org.mymoney;

import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PortfolioManager {
    private ConcurrentHashMap<FundType,Investment> portfolio;
    private ConcurrentHashMap<Month, LedgerPassbook> passbook;

    public PortfolioManager() {
        this.portfolio = new ConcurrentHashMap<FundType, Investment>();
        initialisePassbook();
    }

    private void initialisePassbook(){
        this.passbook = new ConcurrentHashMap<Month, LedgerPassbook>();
        passbook.put(Month.JANUARY,new LedgerPassbook());
    }
    public void addAllocationToPortFolio(FundType fundType, int investmentAmount){
        this.portfolio.put(fundType,new Investment(investmentAmount));
        Optional<LedgerPassbook> ledgerPassbook = Optional.ofNullable(this.passbook.get(Month.JANUARY));
        if (ledgerPassbook.isPresent()) ledgerPassbook.get().addEntry(fundType, investmentAmount);
    }

    public void computeAllocationShares() {
        int totalCorpus = passbook.get(Month.JANUARY).getMonthlyBalance();
        float validator = 0;
        for(Investment investment: portfolio.values()) {
            float sharePercentage = Util.roundOffFloat(investment.getStartingAmount() / totalCorpus);
            validator+=sharePercentage;
            investment.setSharePercentage(sharePercentage);
        }

    }

    public void setSip(FundType fundType, int sipAmmount) {
        Optional<Investment> investment = Optional.ofNullable(portfolio.get(fundType));
        if (investment.isPresent()){
            investment.get().setSipAmount(sipAmmount);
        }
    }

    public void adjustChange(Month month, FundType fundType, float change) {
        Month currentMonth = month.plus(1);
        Optional<LedgerPassbook> currentMonthLedgerPassbook = getCurrentMonthLedgerPassbook(currentMonth);
        Optional<LedgerPassbook> previousMonthLedgerPassbook = Optional.ofNullable(this.passbook.get(month));
        if (currentMonthLedgerPassbook.isPresent() && previousMonthLedgerPassbook.isPresent()){
            int previousMonthBalanace = previousMonthLedgerPassbook.get().readValueForFund(fundType);
            int sipAmount = this.portfolio.get(fundType).getSipAmount();
            int currentMonthBalance = Math.round(Util.roundOffFloat(previousMonthBalanace * change) + sipAmount);
            currentMonthLedgerPassbook.get().addEntry(fundType,currentMonthBalance);
        }
        if (currentMonth == Month.JUNE || currentMonth == Month.DECEMBER) rebalance();
    }

    private Optional<LedgerPassbook> getCurrentMonthLedgerPassbook(Month currentMonth) {
        Optional<LedgerPassbook> currentMonthLedgerPassbook = Optional.ofNullable(this.passbook.get(currentMonth));
        if (currentMonthLedgerPassbook.isEmpty()) {
            this.passbook.put(currentMonth,new LedgerPassbook());
            currentMonthLedgerPassbook = Optional.ofNullable(this.passbook.get(currentMonth));
        }
        return currentMonthLedgerPassbook;
    }

    public int getBalance(Month month) {
        int balance = 0;
        Optional<LedgerPassbook> ledgerPassbook = Optional.ofNullable(this.passbook.get(month));
        if (ledgerPassbook.isPresent()){
            balance = ledgerPassbook.get().getMonthlyBalance();
        }
        return balance;
    }

    public void rebalance() {
        ArrayList<Month> months = Collections.list(passbook.keys());
        Collections.sort(months);
        int monthsCount = months.size();
        if (monthsCount >= 6){
            Month latestMonth = months.get(monthsCount - 1);
            int balance = getBalance(latestMonth);
            for (Map.Entry<FundType, Investment> fundTypeInvestmentEntry : portfolio.entrySet()){
                FundType fundType = fundTypeInvestmentEntry.getKey();
                Investment investment = fundTypeInvestmentEntry.getValue();
                int changedValue = (int) Math.round( balance * investment.getSharePercentage() / 100.0);
                this.passbook.get(latestMonth).replaceEntry(fundType,changedValue);
            }
        }
    }
}
