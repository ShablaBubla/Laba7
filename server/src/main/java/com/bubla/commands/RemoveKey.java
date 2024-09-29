package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.database_managment.DataBaseManager;
import com.bubla.executer.ServerApplication;

import java.sql.SQLException;

/** Класс команды remove_key
 *
 */
public class RemoveKey extends PrimeCommand {
    /** Поле описания комнады*/
    public RemoveKey(){super("remove_key null : удалить элемент из коллекции по его ключу");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application){
        try {
            Product oldProduct = application.getProducts().getProducts().get(args);

            if(oldProduct.getCreatorID() != application.getUserID()){
                return "В доступе отказано";
            }
            DataBaseManager dataBaseManager = new DataBaseManager();
            dataBaseManager.remove(application.getProducts().getProducts().get(args));
            application.getProducts().remove(args);
            return "Объект был удалён";
        }catch (SQLException e){
            return e.getMessage();
        }
    }
}
