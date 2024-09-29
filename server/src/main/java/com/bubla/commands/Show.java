package com.bubla.commands;


import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/** Класс команды show
 *
 */
public class Show extends PrimeCommand {
    /** Поле описания комнады*/
    public Show(){super("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application){
        if(!args.isBlank()){
            return "Недопустимый аргумент: " + args;
        }
        LinkedHashMap<String, Product> val = application.getProducts().getProducts();
        return val.entrySet()
                .stream()
                .filter(entry -> entry.getKey().compareTo(args) >= 0)
                .map(i -> i.getKey() + " " + i.getValue().toString())
                .collect(Collectors.joining("\n"));
    }
}
