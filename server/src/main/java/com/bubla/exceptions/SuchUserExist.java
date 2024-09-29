package com.bubla.exceptions;

public class SuchUserExist extends RuntimeException{
    public SuchUserExist(){super("Пользователь с таким именем уже есть");}
}
