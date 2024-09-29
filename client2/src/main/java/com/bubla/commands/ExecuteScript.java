package com.bubla.commands;

import com.bubla.Application;
import com.bubla.executer.CommandManager;
import com.bubla.executer.Runner;
import com.bubla.message.Request;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/** Класс команды execute_script
 * @author ShablsBubla
 */
public class ExecuteScript extends PrimeCommand {
    /** Поле описания комнады*/
    public ExecuteScript() {
        super("execute_script file_name : считать и исполнить скрипт из указанного файла");
    }


    @Override
    public void execute(Request request, Runner runner) {
        String args = request.getArgs();
        request.setArgs("Скрипт выолнен");
        if(runner.getCmdStack().contains(args)){
            System.out.println("Рекурсия запрещена!");
        }
        else {
            try{
                runner.pushCmd(args);
                runner.setInputStream(new FileInputStream(args));
                CommandManager commandManager = new CommandManager(runner, request);
                commandManager.start(runner.getInputStream());
            } catch (FileNotFoundException e) {
                request.setArgs(e.getMessage());
            } finally {
                runner.popCmd();
            }
        }
    }
}