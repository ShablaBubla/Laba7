package com.bubla.executer;

import com.bubla.commands.*;
import com.bubla.exceptions.NoSuchCommandException;
import com.bubla.exceptions.StopServerException;
import lombok.Data;

import java.util.HashMap;
import java.util.NoSuchElementException;

/** Класс выолнения команд
 *
 */
@Data
public class Executer {
    private HashMap<String, PrimeCommand> commandsList;

    public Executer(){
        this.commandsList = new HashMap<String, PrimeCommand>();
        commandsList.put("help", new Help());
        commandsList.put("info", new Info());
        commandsList.put("show", new Show());
        commandsList.put("insert", new Insert());
        commandsList.put("update", new Update());
        commandsList.put("remove_key", new RemoveKey());
        commandsList.put("clear", new Clear());
        commandsList.put("execute_script", new ExecuteScript());
        commandsList.put("exit", new Exit());
        commandsList.put("replace_if_lowe", new ReplaceIfLowe());
        commandsList.put("remove_lower_key", new RemoveLowerKey());
        commandsList.put("print_ascending", new PrintAscending());
        commandsList.put("print_unique_owner", new PrintUniqueOwner());
        commandsList.put("print_field_descending_unit_of_measure", new PrintFieldDescendingUnitOfMeasure());
        commandsList.put("history", new History());
        commandsList.put(null, new Ready());
        commandsList.put("auth", new Auth());
        commandsList.put("reg", new Reg());
    }

    /** Выполнение команды
     *
     * @param cmd команда
     * @param args аргумент команды
     * @param application приложение
     */
    public String accomplish(String cmd, String args, ServerApplication application){
        PrimeCommand command = this.commandsList.get(cmd);
        String outputString = "";
        try {
            outputString = command.execute(args, application);
            application.updateHistory(cmd);
        }
        catch (NoSuchElementException e){
        }
        catch(StopServerException e){
            throw e;
        }
        catch (Exception e){
            outputString = e.getMessage();
        }
        return outputString;
    }
}
