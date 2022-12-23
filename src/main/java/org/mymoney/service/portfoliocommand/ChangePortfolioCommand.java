package org.mymoney.service.portfoliocommand;

import org.mymoney.enums.FundType;
import org.mymoney.pojo.FundChangeRequest;
import org.mymoney.service.PortfolioCommand;
import org.mymoney.utils.Util;

import java.time.Month;
import java.util.ArrayList;

public class ChangePortfolioCommand extends PortfolioCommand {
    private static float getChangeValue(String str) {
        float value = 0;
        value = Float.parseFloat(Util.removeLastCharacter(str.trim()));
        value = (float) (1.00 + (value / 100.00));
        return value;
    }

    @Override
    public void execute(ArrayList<String> args) {
        int fundIndex = 0;
        String monthStr = args.remove(args.size() - 1);
        Month month = Month.valueOf(monthStr.trim().toUpperCase());
        FundChangeRequest fundChangeRequest = new FundChangeRequest(month);
        for (String str : args) {
            float value = getChangeValue(str);
            fundChangeRequest.addFundChange(FundType.values()[fundIndex++], value);
        }
        portfolioService.change(fundChangeRequest);
    }
}
