package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

import java.util.ArrayList;
import java.util.stream.Collectors;

/** Класс команды print_ascending
 *
 */
public class PrintAscending extends PrimeCommand {
    /** Поле описания комнады*/
    public PrintAscending(){super("print_ascending : вывести элементы коллекции в порядке возрастания");}

    /** Метод исполнения комнады
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
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }
}
