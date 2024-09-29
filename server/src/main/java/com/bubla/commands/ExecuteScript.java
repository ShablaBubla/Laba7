package com.bubla.commands;


import com.bubla.Application;
import com.bubla.executer.ServerApplication;

/** Класс команды execute_script
 * @author ShablsBubla
 */
public class ExecuteScript extends PrimeCommand {
    /** Поле описания комнады*/
    public ExecuteScript() {
        super("execute_script file_name : считать и исполнить скрипт из указанного файла");
    }

    /** Метод исполнения команды
     *
     * @param args аргумент команды
     * @param application приложение
     */
    @Override
    public String execute(String args, ServerApplication application) {
        return args;
    }
}