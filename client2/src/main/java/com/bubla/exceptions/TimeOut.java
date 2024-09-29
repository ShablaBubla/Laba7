package com.bubla.exceptions;

import java.util.zip.CheckedInputStream;

public class TimeOut extends RuntimeException {
    public TimeOut(){super("Сервер временно недоступен: время ожидания истекло");}
}
