package com.bubla.exceptions;

/** Класс исключения при вводе неверной команды
 *
 */
public class NoSuchCommandException extends Exception {
    public NoSuchCommandException(String msg){super("Команда " + msg + " не существует");}
}
