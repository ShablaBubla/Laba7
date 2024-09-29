package com.bubla.executer;

import com.bubla.Application;
import com.bubla.commands.*;
import com.bubla.message.Request;
import lombok.Data;

import java.util.HashMap;

/** Класс выолнения команд
 *
 */
@Data
public class Executer {
    private HashMap<String, PrimeCommand> commandsList;

    public Executer(){
        this.commandsList = new HashMap<String, PrimeCommand>();
        commandsList.put("execute_script", new ExecuteScript());
        commandsList.put("replace_if_lowe", new ReplaceIfLowe());
        commandsList.put("insert", new Insert());
        commandsList.put("update", new Update());
    }

    /** Выполнение команды
     *
     * @param cmd команда
     * @param args аргумент команды
     * @param application приложение
     */
    public void accomplish(Request request, Runner runner) {
        PrimeCommand command = this.commandsList.get(request.getCmd());
        try {
            command.execute(request, runner);
        } catch (Exception ignored){
        }
    }
}
