package org.mymoney.service;

import org.mymoney.enums.ErrorCode;
import org.mymoney.exception.MyMoneyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PortfolioFileProcessor {

    private PortfolioService portfolioService;

    private final String REGEX_FILE_SPLIT_PATTERN = " ";

    public void setPortfolioService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /**
     * @param filepath
     * ALLOCATE 6000 3000 1000
     * SIP 2000 1000 500
     * CHANGE 4.00% 10.00% 2.00% JANUARY
     * CHANGE -10.00% 40.00% 0.00% FEBRUARY
     * CHANGE 12.50% 12.50% 12.50% MARCH
     * CHANGE 8.00% -3.00% 7.00% APRIL
     * CHANGE 13.00% 21.00% 10.50% MAY
     * CHANGE 10.00% 8.00% -5.00% JUNE
     * BALANCE MARCH
     * REBALANCE
     */
    public void processInputFile(String filepath) {
        try {
            Scanner scanner;
            scanner = new Scanner(new File(filepath));
            while (scanner.hasNext()) {
                String line = null;
                line = scanner.nextLine();
                ArrayList<String> inputLineList = new ArrayList<>(Arrays.asList(line.split(REGEX_FILE_SPLIT_PATTERN)));
                processInputLineCommand(inputLineList);
            }
        } catch (FileNotFoundException|NullPointerException e ) {
            throw new MyMoneyException(ErrorCode.FILE_ACCESS_ERROR, e);
        } catch (IllegalStateException illegalStateException){
            throw new MyMoneyException(ErrorCode.FILE_READ_ERROR, illegalStateException);
        }
    }

    private void processInputLineCommand(ArrayList<String> inputLineList) {
        if (!inputLineList.isEmpty()) {
            String commandName = inputLineList.remove(0);
            Optional<PortfolioCommand> portfolioCommand = Optional.ofNullable(PortfolioCommand.getInputCommand(commandName));
            if (portfolioCommand.isPresent()) {
                portfolioCommand.get().setPortfolioService(portfolioService);
                try {
                    portfolioCommand.get().execute(inputLineList);
                } catch (NumberFormatException nfe) {
                    throw new MyMoneyException(ErrorCode.COMMAND_ARGS_PARSING_ERROR, nfe);
                } catch (RuntimeException e) {
                    throw new MyMoneyException(ErrorCode.RUNTIME_ERROR, e);
                }
            } else throw new MyMoneyException(ErrorCode.COMMAND_PARSING_ERROR);

        }
    }
}
