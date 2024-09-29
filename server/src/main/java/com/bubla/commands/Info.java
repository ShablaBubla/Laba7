package com.bubla.commands;

import com.bubla.Application;
import com.bubla.executer.ServerApplication;

/** Класс команды info*/
public class Info extends PrimeCommand {
    /** Поле описания комнады*/
    public Info(){super("info : вывести в стандартный поток вывода информацию о коллекции");}

    /** Метод исполения команды
     *
     * @param args аргумент команды
     * @param application прилодение
     */
    @Override
    public String execute(String args, ServerApplication application){
        if(!args.isBlank()){
            return "Недопустимый аргумент: " + args;
        }
        return "Тип коллекции: LinkedHashMap\n" +
                "Дата инициализации: " + application.getProducts().getCreationDate() + "\n" +
                "Дата модификации: " + application.getProducts().getModDate() + "\n" +
                "Количество элементов:" + application.getProducts().getSize();
    }
}
