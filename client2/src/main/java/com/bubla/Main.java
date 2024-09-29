package com.bubla;

import com.bubla.executer.Authentication;
import com.bubla.executer.CommandManager;
import com.bubla.executer.Registration;
import com.bubla.executer.Runner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Runner runner = new Runner();
            Authentication auth = new Authentication(runner);
            boolean isReg = auth.start();
            runner = auth.getRunner();
            if(isReg){
                Registration reg = new Registration(runner);
                reg.start();
                runner = reg.getRunner();
            }
            CommandManager commandManager = new CommandManager(runner);
            System.out.println("Для справки по командам введите help");
            commandManager.start(System.in);
        }
        catch (IOException e){
            System.out.println("Не удаётся подключиться к серверу");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}