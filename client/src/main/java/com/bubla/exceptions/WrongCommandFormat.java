package com.bubla.exceptions;

/** Класс исключения неправильного аргумента для команды
 *
 */
public class WrongCommandFormat extends Exception{
    public WrongCommandFormat(String msg){
        super("Неверный формат аргумента: " + msg);
    }
}
