package org.mymoney.service.portfoliocommand;

import org.mymoney.service.PortfolioCommand;

import java.time.Month;
import java.util.ArrayList;

public class BalancePortfolioCommand extends PortfolioCommand {
    @Override
    public void execute(ArrayList<String> args) {
        String monthStr = args.remove(args.size() - 1);
        Month month = Month.valueOf(monthStr.trim().toUpperCase());
        portfolioService.getBalance(month);
    }
}
