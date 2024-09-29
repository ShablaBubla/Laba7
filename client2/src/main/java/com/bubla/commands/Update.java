package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.Runner;
import com.bubla.message.Request;

/** Класс команды update
 *
 */
public class Update extends PrimeCommand {
    /** Поле описания комнады*/
    public Update(){super("update id {element} : обновить значение элемента коллекции, id которого равен заданному");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     */
    public void execute(Request request, Runner runner){
        Product newProduct = new Product();
        try{
            Insert insert = new Insert();
            if(runner.getInputStream().getClass().getCanonicalName().equals("java.io.FileInputStream")){
                newProduct = insert.enterProductFromFile(runner.getScanner());
                request.setNewProduct(newProduct);
            }
            else {
                newProduct = insert.enterProduct();
                request.setNewProduct(newProduct);
            }
        }catch(NullPointerException e){
            System.out.println("Объект с таким id не существует");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Неверный формат ввода данных из файла");
        }
    }
}
