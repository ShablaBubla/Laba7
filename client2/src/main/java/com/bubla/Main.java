package com.bubla;

import com.bubla.executer.CommandManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            CommandManager commandManager = new CommandManager();
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