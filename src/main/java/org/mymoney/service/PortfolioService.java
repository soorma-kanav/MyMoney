package org.mymoney;

import java.time.Month;

public class Portfolio {
    private User user;
    private int netWorth;
    private PortfolioManager portfolioManager;

    public Portfolio(User user) {
        this.user = user;
    }

    public void allocate(int equityFund,
                         int goldFund,
                         int debtFund){
        portfolioManager.addAllocationToPortFolio(FundType.EQUITY,equityFund);
        portfolioManager.addAllocationToPortFolio(FundType.GOLD,goldFund);
        portfolioManager.addAllocationToPortFolio(FundType.DEBT,debtFund);
        portfolioManager.computeAllocationShares();
    }


    public void sip(int equitySip,
                    int goldSip,
                    int debtSip){
        portfolioManager.setSip(FundType.EQUITY, equitySip);
        portfolioManager.setSip(FundType.GOLD, goldSip);
        portfolioManager.setSip(FundType.DEBT, debtSip);
    }

    public void change(float equityChange,
                       float goldChange,
                       float debtChange,
                       Month month){
        portfolioManager.adjustChange(month, FundType.EQUITY, equityChange);
        portfolioManager.adjustChange(month, FundType.GOLD, goldChange);
        portfolioManager.adjustChange(month, FundType.DEBT, debtChange);
    }

    public void getBalance(Month month){
        portfolioManager.getBalance(month);
    }

    public void rebalance(){
        portfolioManager.rebalance();
    }
}
