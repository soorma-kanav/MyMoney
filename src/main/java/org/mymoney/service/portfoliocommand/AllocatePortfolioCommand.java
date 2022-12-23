package org.mymoney.service.portfoliocommand;

import org.mymoney.enums.FundType;
import org.mymoney.pojo.FundValueRequest;
import org.mymoney.service.PortfolioCommand;

import java.util.ArrayList;

public class AllocatePortfolioCommand extends PortfolioCommand {
    @Override
    public void execute(ArrayList<String> args) {
        ArrayList<FundValueRequest> fundValueRequests = new ArrayList<>();
        int fundIndex = 0;
        for (String str : args) {
            int value = Integer.parseInt(str);
            fundValueRequests.add(new FundValueRequest(FundType.values()[fundIndex++], value));
        }
        portfolioService.allocate(fundValueRequests);
    }
}
