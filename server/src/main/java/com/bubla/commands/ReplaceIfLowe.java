package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.ServerApplication;

/** Класс команды replace_if_lowe
 *
 */
public class ReplaceIfLowe extends PrimeCommand {
    /** Поле описания комнады*/
    public ReplaceIfLowe(){super("replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application) {
        String outputStr = "Новое занчение больше";
        try{
            Product prod = application.getNewProd();
        if(prod.compareTo(application.getProducts().getProducts().get(args)) > 0){
            application.getProducts().update(args, prod);
            outputStr = "Объект успешно поменян";
        }
        }
        catch (IllegalArgumentException e){
            outputStr = e.getMessage();
        }
        catch (ArrayIndexOutOfBoundsException e){
            outputStr = "Неверный формат ввода данных из файла";
        }
        catch (Exception e){
            outputStr = "Нет продукта с таким ключом";
        }
        return outputStr;
    }
}
