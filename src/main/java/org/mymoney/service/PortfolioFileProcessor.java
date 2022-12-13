package org.mymoney;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PortfolioFileProcessor{

    /**
     * @param filepath
     *
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
     *
     */
    public void parseInputFile(String filepath) {
        Scanner scanner;
        try {
             scanner = new Scanner(new File(filepath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            ArrayList<String> inputLineList = (ArrayList<String>) Arrays.asList(line.split(" "));
            processInputLineCommand(inputLineList);
        }
    }

    private void processInputLineCommand(ArrayList<String> inputLineList) {
        if (!inputLineList.isEmpty()){
            String commandName = inputLineList.get(0);
            Optional<PortfolioCommand> portfolioCommand = Optional.ofNullable(PortfolioCommand.getInputCommand(commandName));
            if (portfolioCommand.isPresent()){
                portfolioCommand.get().execute(inputLineList.addAll());
            }

        }
    }
}
