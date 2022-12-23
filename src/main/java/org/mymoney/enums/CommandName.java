package org.mymoney.enums;

import java.util.HashMap;

public enum CommandName {
    ALLOCATE("ALLOCATE"), SIP("SIP"), CHANGE("CHANGE"), BALANCE("BALANCE"), REBALANCE("REBALANCE");

    private static final HashMap<String, CommandName> commandNameHashMap = new HashMap<>();

    static {
        for (CommandName commmand : CommandName.values()) {
            commandNameHashMap.put(commmand.name, commmand);
        }
    }

    private final String name;

    CommandName(String name) {
        this.name = name;
    }

    public static CommandName getCommandName(String command) {
        return commandNameHashMap.get(command);
    }

    public boolean compareString(String value) {
        return name.equalsIgnoreCase(value);
    }


}
