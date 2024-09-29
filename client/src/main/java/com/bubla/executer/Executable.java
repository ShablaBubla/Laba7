package com.bubla.executer;

import com.bubla.Application;
import com.bubla.message.Request;

/** Интерфейс представляет метод исполнения команды
 *
 */
public interface Executable {
    void execute(Request request, Runner runner);
}
