package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/** Класс команды print_unique_owner
 *
 */
public class PrintUniqueOwner extends PrimeCommand {
    /** Поле описания комнады*/
    public PrintUniqueOwner(){super("print_unique_owner : вывести уникальные значения поля owner всех элементов в коллекции");}

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
        ArrayList<Product> val = new ArrayList<>(application.getProducts().getProducts().values());
        return val.stream()
                .distinct()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }
}
