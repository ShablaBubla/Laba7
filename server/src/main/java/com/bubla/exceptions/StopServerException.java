package com.bubla.exceptions;

public class StopServerException extends RuntimeException{
    public StopServerException(){super("Сервер закончит свою работу после выполнения последней команды");}
}
