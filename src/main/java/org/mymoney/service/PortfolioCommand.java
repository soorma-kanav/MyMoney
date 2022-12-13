package org.mymoney;

import java.util.ArrayList;

public abstract class PortfolioCommand {
    private Portfolio portfolio;

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public abstract void execute(ArrayList<String> args);

    public static PortfolioCommand getInputCommand(String command){
        if (!command.isBlank()){
            CommandName commandName = CommandName.getCommandName(command);
            switch (commandName){
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
}
