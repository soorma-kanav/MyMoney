package org.mymoney.service;

import org.mymoney.entity.User;
import org.mymoney.pojo.FundChangeRequest;
import org.mymoney.pojo.FundValueRequest;
import org.mymoney.utils.Constant;

import java.time.Month;
import java.util.ArrayList;

public class PortfolioService {
    private final User user;
    private int netWorth;
    private final PortfolioManager portfolioManager;

    public PortfolioService(User user) {
        this.user = user;
        this.portfolioManager = new PortfolioManager();
    }

    public void allocate(ArrayList<FundValueRequest> fundValueRequests) {
        for (FundValueRequest fundValueRequest : fundValueRequests) {
            portfolioManager.addAllocationToPortFolio(fundValueRequest.getFundType(), fundValueRequest.getValue());
        }
        portfolioManager.computeAllocationShares();
    }

    public void sip(ArrayList<FundValueRequest> fundValueRequests) {
        for (FundValueRequest fundValueRequest : fundValueRequests) {
            portfolioManager.setSip(fundValueRequest.getFundType(), fundValueRequest.getValue());
        }
    }

    public void change(FundChangeRequest fundChangeRequest) {
        Month month = fundChangeRequest.getMonth();
        for (FundChangeRequest.FundChange fundChange : fundChangeRequest.getFundChangeList()) {
            portfolioManager.adjustChange(month, fundChange.getFundType(), fundChange.getValue());
        }
        rebalanceIfApplicable(month);
    }

    private void rebalanceIfApplicable(Month month) {
        if (month.equals(Month.JUNE) || month.equals(Month.DECEMBER))
            portfolioManager.rebalance();
    }

    public void getBalance(Month month) {
        portfolioManager.printBalance(month);
    }

    public void rebalance() {
        Month month = portfolioManager.getLatestMonth();
        if (!month.equals(Month.JUNE) && !month.equals(Month.DECEMBER)) {
            System.out.println(Constant.CANNOT_REBALANCE_STR);
        }
    }

    @Override
    public String toString() {
        return "PortfolioService{" +
                "portfolioManager=" + portfolioManager +
                '}';
    }
}
