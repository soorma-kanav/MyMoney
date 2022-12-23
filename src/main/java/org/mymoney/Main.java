package org.mymoney;

import org.mymoney.entity.User;
import org.mymoney.enums.ErrorCode;
import org.mymoney.exception.MyMoneyException;
import org.mymoney.service.PortfolioFileProcessor;
import org.mymoney.service.PortfolioService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user = new User("Kanav", "kanav0705@gmail.com");
        PortfolioService portfolioService = new PortfolioService(user);

        PortfolioFileProcessor portfolioFileProcessor = new PortfolioFileProcessor();
        portfolioFileProcessor.setPortfolioService(portfolioService);

        try {
            if (args.length < 1) throw new MyMoneyException(ErrorCode.FILE_ACCESS_ERROR);
            String filePath = args[0];
            portfolioFileProcessor.processInputFile(filePath);
        } catch (MyMoneyException mmex) {
            System.out.println(mmex.getMessage());
        }
    }

}