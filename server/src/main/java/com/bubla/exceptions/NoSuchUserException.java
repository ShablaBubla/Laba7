package com.bubla.exceptions;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(){super("Пользователь с таким именем не существует");}
}
