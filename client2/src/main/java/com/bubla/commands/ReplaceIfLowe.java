package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.Product;
import com.bubla.executer.Runner;
import com.bubla.message.Request;

/** Класс команды replace_if_lowe
 *
 */
public class ReplaceIfLowe extends PrimeCommand {
    /** Поле описания комнады*/
    public ReplaceIfLowe(){super("replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого");}

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     */
    @Override
    public void execute(Request request, Runner runner) {
        Product prod = new Product();
        try{
            Insert insert = new Insert();
            if(runner.getInputStream().getClass().getCanonicalName().equals("java.io.FileInputStream")){
                prod = insert.enterProductFromFile(runner.getScanner());
                prod.setCreatorName(runner.getClientName());
                prod.setCreatorID(runner.getCliendID());
                request.setNewProduct(prod);
            }
            else {
                prod = insert.enterProduct();
                prod.setCreatorName(runner.getClientName());
                prod.setCreatorID(runner.getCliendID());
                request.setNewProduct(prod);
            }
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Неверный формат ввода данных из файла");
        }
    }
}
