package com.bubla.commands;

import com.bubla.Application;
import com.bubla.executer.ServerApplication;

/** Класс команды history*/
public class History extends PrimeCommand {
    /** Поле описания комнады*/
    public History(){super("history : вывести последние 5 команд");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application) {
        if(!args.isBlank()){
            return "Недопустимый аргумент: " + args;
        }
        String[] history = application.getHistory();
        int counter = application.getCounter();
        int count = ((counter - 1) % 6 + 6) % 6;
        StringBuilder outputString = new StringBuilder();
        for(int i = 1; i < 6; i++){
            if(history[count] == null){
                continue;
            }
            outputString.append(i).append(". ").append(history[count]).append("\n");
            count = ((count - 1) + 6) % 6;
        }
        return outputString.toString();
    }
}
