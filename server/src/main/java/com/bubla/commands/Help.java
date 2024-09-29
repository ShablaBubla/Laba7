package com.bubla.commands;

import com.bubla.Application;
import com.bubla.executer.Executer;
import com.bubla.executer.ServerApplication;

import java.util.stream.Collectors;

/** Класс команды help*/
public class Help extends PrimeCommand {
    /** Поле описания комнады*/
    public Help(){super("help : вывести справку по доступным командам");}

    /** Метод выполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application){
        if (!args.isBlank()){
            return "Недопусттимый аргумент: " + args;
        }
        Executer ex = new Executer();
        return ex.getCommandsList().values().stream()
                .map(PrimeCommand::getDescription)
                .filter(i -> !i.isBlank())
                .collect(Collectors.joining("\n"));
    }
}
