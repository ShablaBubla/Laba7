package com.bubla.executer;

import com.bubla.Application;

/** Интерфейс представляет метод исполнения команды
 *
 */
public interface Executable {
    String execute(String args, ServerApplication application);
}
