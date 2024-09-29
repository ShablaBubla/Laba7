package com.bubla.exceptions;

/** Класс исключения при несоблюдении порядка ввод из файла
 *
 */
public class WrongEnterOrder extends IllegalArgumentException{
    public WrongEnterOrder(String msg){
        super("Не соблюдён порядок ввода продукта из файла " + msg);
    }
}
