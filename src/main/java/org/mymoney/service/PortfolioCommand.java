package org.mymoney.service;

import org.mymoney.enums.CommandName;
import org.mymoney.service.portfoliocommand.*;

import java.util.ArrayList;

public abstract class PortfolioCommand {
    protected PortfolioService portfolioService;

    public static PortfolioCommand getInputCommand(String command) {
        if (!command.isBlank()) {
            CommandName commandName = CommandName.getCommandName(command);
            switch (commandName) {
                case ALLOCATE:
                    return new AllocatePortfolioCommand();
                case SIP:
                    return new SipPortfolioCommand();
                case CHANGE:
                    return new ChangePortfolioCommand();
                case BALANCE:
                    return new BalancePortfolioCommand();
                case REBALANCE:
                    return new RebalancePortfolioCommand();
                default:
                    return null;
            }
        }
        return null;
    }

    public void setPortfolioService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    public abstract void execute(ArrayList<String> args);
}
