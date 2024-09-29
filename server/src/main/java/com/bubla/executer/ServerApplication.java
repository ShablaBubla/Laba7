package com.bubla.executer;

import com.bubla.Application;
import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.classes.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServerApplication extends com.bubla.Application {
    private LinkedHashMapOfProducts products;
    private String[] history = new String[6];
    private int counter = 0;
    private int userID;

    public ServerApplication(LinkedHashMapOfProducts products){
        this.products = products;
    }


    /** Метод добавления новой команды в историю команд
     *
     * @param cmd команды
     */
    public void updateHistory(String cmd){
        this.counter = (this.counter + 1) % 6;
        this.history[counter] = cmd;
    }

    public void fillApplication(Product product){
        this.setNewProd(product);
    }

}
