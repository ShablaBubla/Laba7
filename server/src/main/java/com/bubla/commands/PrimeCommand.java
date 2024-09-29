package com.bubla.commands;


import com.bubla.executer.Executable;
import lombok.Data;

/** Класс описывающий щаблон команды */
@Data
public abstract class PrimeCommand implements Executable {
    private String description;
    public PrimeCommand(String description){
        this.description = description;
    }
}
