package com.bubla.commands;

import com.bubla.executer.ServerApplication;

public class Ready extends PrimeCommand{
    public Ready(){super("");}

    @Override
    public String execute(String args, ServerApplication application) {
        return "Сервер снова доступен";
    }
}
