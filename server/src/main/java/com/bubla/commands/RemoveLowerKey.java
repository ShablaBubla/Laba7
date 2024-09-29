package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/** Класс команды remove_lower_key
 *
 */
public class RemoveLowerKey extends PrimeCommand {
    /** Поле описания комнады*/
    public RemoveLowerKey(){super("remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application) {
        LinkedHashMap<String, Product> val = application.getProducts().getProducts();
        val = val.entrySet()
                .stream()
                .filter(entry -> entry.getKey().compareTo(args) >= 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        LinkedHashMapOfProducts newLinkedMap = new LinkedHashMapOfProducts(application.getProducts().getCreationDate());
        newLinkedMap.setProducts(val);
        application.setProducts(newLinkedMap);
        return "Объекто успешно удалены";
    }
}
