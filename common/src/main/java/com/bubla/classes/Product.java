package com.bubla.classes;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/** Класс описывает продукт
 *
 */
public class Product implements Comparable<Product>, Serializable {
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private long id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Getter
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Getter
    private Coordinates coordinates; //Поле не может быть null
    @Getter
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Getter
    private long price; //Значение поля должно быть больше 0
    @Getter
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    @Getter
    @Setter
    private Person owner; //Поле может быть null
    @Getter
    @Setter
    private String creatorName;
    @Setter
    @Getter
    private int creatorID;

    public Product(long id, String name, Coordinates coordinates, Date creationDate, long price, UnitOfMeasure unitOfMeasure, Person owner, String creatorName, int creatorID){
        this.creationDate = creationDate;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        this.creatorName = creatorName;
        this.creatorID = creatorID;
    }
    public Product(String name, Coordinates coordinates, long price, UnitOfMeasure unitOfMeasure, Person owner){
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }
    public Product(String name, Coordinates coordinates, long price, UnitOfMeasure unitOfMeasure){
        this(name, coordinates, price, unitOfMeasure, null);
    }
    public Product(){
        this.creationDate = new Date();
    }

    public void setName(String newName) {
        if(newName == null){
            throw new IllegalArgumentException("Название не может быть null");
        }
        if(newName.isBlank()){
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        this.name = newName;
    }
    public void setCoordinates(Coordinates newCoordinates){
        if(newCoordinates == null){
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        this.coordinates = newCoordinates;
    }
    public void setPrice(long newPrice){
        if(newPrice <= 0){
            throw new IllegalArgumentException("Цена может быть только положительной");
        }
        this.price = newPrice;
    }
    public void setUnitOfMeasure(UnitOfMeasure newUnit){
        if(newUnit == null){
            throw new IllegalArgumentException("Мера измерения не может быть null");
        }
        this.unitOfMeasure = newUnit;
    }



    @Override
    public String toString(){
        if(owner != null) {
            return "id " + this.id + ", " + this.name + " " + this.price + " за " + this.unitOfMeasure + ", лежит в " + this.getCoordinates() + ", владелец: (" + this.owner + ")";
        }else {
            return "id " + this.id + ", " + this.name + " " + this.price + " за " + this.unitOfMeasure + ", лежит в " + this.getCoordinates();
        }
    }


    @Override
    public int compareTo(Product prod) {
        return this.name.compareTo(prod.getName());
    }
}

