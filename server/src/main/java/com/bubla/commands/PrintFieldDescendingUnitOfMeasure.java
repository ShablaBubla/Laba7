package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/** Класс команды print_field_descending_unit_of_measure
 *
 */
public class PrintFieldDescendingUnitOfMeasure extends PrimeCommand {
    /** Поле описания комнады*/
    public PrintFieldDescendingUnitOfMeasure(){super("print_field_descending_unit_of_measure : вывести значения поля unitOfMeasure всех элементов в порядке убывания");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application) {
            if (!args.isBlank()) {
                return "Недопустимый аргумент: " + args;
            }
            ArrayList<Product> val = new ArrayList<>(application.getProducts().getProducts().values());
            return val.stream()
                    .sorted(Comparator.reverseOrder())
                    .map(i -> i.getUnitOfMeasure())
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n"));
        }
}
