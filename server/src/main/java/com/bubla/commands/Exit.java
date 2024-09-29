package com.bubla.commands;


import com.bubla.Application;
import com.bubla.executer.ServerApplication;

/** Класс команды exit */
public class Exit extends PrimeCommand {
    /** Поле описания комнады*/
    public Exit(){super("exit : завершить программу");}

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
        application.setRunnig(false);
        return "Завершение работы";
    }
}
