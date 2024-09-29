package com.bubla.exceptions;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(){super("Неверный пароль"); }
}
