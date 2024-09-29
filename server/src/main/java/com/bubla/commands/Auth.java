package com.bubla.commands;

import com.bubla.database_managment.DataBaseManager;
import com.bubla.executer.ServerApplication;

public class Auth extends PrimeCommand{
    public Auth(){super("");}
    @Override
    public String execute(String args, ServerApplication application) {
        String outputString;
        DataBaseManager dataBaseManager = new DataBaseManager();
        String[] tokens = args.split(":");
        String name = tokens[0];
        String passsword = tokens[1];
        try{
            int id = dataBaseManager.checkUser(name, passsword);
            outputString = "Вход успешно выполнен:" + id;
            application.setRunnig(false);
        } catch (Exception e){
            outputString = e.getMessage() + ":";
        }
        return outputString;
    }
}
