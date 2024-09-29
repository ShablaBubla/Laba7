package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

/** Класс команды update
 *
 */
public class Update extends PrimeCommand {
    /** Поле описания комнады*/
    public Update(){super("update id {element} : обновить значение элемента коллекции, id которого равен заданному");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    public String execute(String args, ServerApplication application){
        long id = Long.parseLong(args);
        LinkedHashMapOfProducts prods = application.getProducts();
        String oldKey = null;
        String outputString = "Объект успешно обновлён";
        for (String key: prods.getProducts().keySet()){
            Product product = prods.getProducts().get(key);
            if(id == product.getId()){
                oldKey = key;
                break;
            }
        }
        try{
            Product newProduct = application.getNewProd();
            newProduct.setId(id);
            if(prods.getProducts().get(oldKey).getCreatorID() != application.getUserID()){
                outputString = "Отказано в доступе не ваш объект!";
            }
            prods.update(oldKey, newProduct);
        }catch(NullPointerException e){
            outputString = "Объект с таким id не существует";
        }
        catch (IllegalArgumentException e){
            outputString = e.getMessage();
        }
        catch (ArrayIndexOutOfBoundsException e){
            outputString = "Неверный формат ввода данных из файла";
        }
        return outputString;
    }
}
