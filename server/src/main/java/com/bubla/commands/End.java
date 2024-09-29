package com.bubla.commands;

import com.bubla.exceptions.StopServerException;
import com.bubla.executer.ServerApplication;

public class End extends PrimeCommand{
    public End() {
        super("Серверная комманда для завершения работы");
    }

    @Override
    public String execute(String args, ServerApplication application) {
        throw new StopServerException();
    }
}
