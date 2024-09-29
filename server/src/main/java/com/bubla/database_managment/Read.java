package com.bubla.database_managment;

import com.bubla.classes.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;

/** Класс чтения из файла
 *
 */
@Data
public class Read {
    private String file;
    private LinkedHashMap<String, Product> products;

    public Read(){
        this.products = new LinkedHashMap<>();
    }

    /** Метод наполнения коллекции из файла
     *
     */
    public void refil() {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            this.products = dataBaseManager.refill();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
