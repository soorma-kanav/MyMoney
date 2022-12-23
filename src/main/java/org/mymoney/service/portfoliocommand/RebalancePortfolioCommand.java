package org.mymoney.service.portfoliocommand;

import org.mymoney.service.PortfolioCommand;

import java.util.ArrayList;

public class RebalancePortfolioCommand extends PortfolioCommand {
    @Override
    public void execute(ArrayList<String> args) {
        portfolioService.rebalance();
    }
}
