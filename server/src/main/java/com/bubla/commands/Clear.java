package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.database_managment.DataBaseManager;
import com.bubla.database_managment.Read;
import com.bubla.executer.ServerApplication;

import java.sql.SQLDataException;
import java.sql.SQLException;


/** Класс команды clear
 * @author ShablsBubla
 */
public class Clear extends PrimeCommand {
    /** Поле описания комнады*/
    public Clear(){super("clear : очистить коллекцию");}

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
         try {
             DataBaseManager dataBaseManager = new DataBaseManager();
             dataBaseManager.removeAll(application.getUserID());
             Read read = new Read();
             read.refil();
             LinkedHashMapOfProducts linkedHashMapOfProducts = new LinkedHashMapOfProducts(read.getProducts());
             linkedHashMapOfProducts.setCreationDate(application.getProducts().getCreationDate());
             application.setProducts(linkedHashMapOfProducts);
             return "Коллекция очищена";
         } catch (SQLException e ){
             return e.getMessage();
         }
    }
}
