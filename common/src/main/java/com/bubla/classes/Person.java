package com.bubla.classes;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Класс описывает человека
 *
 */
@Data
public class Person implements Comparable<Person>, Serializable {
    private static final long serialVersionUID = 1L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0

    public Person(String name, String birthday, Long weight){
        this.name = name;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthday = LocalDate.parse(birthday.split(" ")[0], formatter).atStartOfDay();
        this.weight = weight;
    }
    public Person(String name, LocalDateTime birthday, Long weight){
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }
    public Person(String name, Long weight){
        this(name, LocalDateTime.now(), weight);
    }
    public Person(){}
    public void setName(String newName){
        if(newName == null){
            throw new IllegalArgumentException("Имя не может быть null");
        }
        if(newName.isBlank()){
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        this.name = newName;
    }
    public void setBirhday(String birthday){
        if(!birthday.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.birthday = LocalDate.parse(birthday, formatter).atStartOfDay();
        }
    }
    public void setWeight(Long newWeight){
        if(newWeight == null){
            throw new IllegalArgumentException("Вес не может быть null");
        }
        if(newWeight <= 0){
            throw new IllegalArgumentException("Вес не может быть отрицательным");
        }
        this.weight = newWeight;
    }
    @Override
    public String toString(){
        if (birthday == null){
            return name + ", весит: " + weight;
        }
        return name + ", родился: " + birthday.toLocalDate() + ", весит: " + weight;
    }
    @Override
    public int compareTo(Person pers) {
        return this.name.compareTo(pers.getName());
    }
}
