package com.bubla.commands;

import com.bubla.Application;
import com.bubla.classes.*;
import com.bubla.exceptions.WrongCommandFormat;
import com.bubla.exceptions.WrongEnterOrder;
import com.bubla.executer.Runner;
import com.bubla.message.Request;

import java.util.Scanner;

/** Класс команды insert
 *
 */
public class Insert extends PrimeCommand {
    /** Поле описания комнады*/
    public Insert(){super("insert null {element} : добавить новый элемент с заданным ключом");}

    /** Метод исполнения команды
     * @param runner приложение
     */
    @Override
    public void execute(Request request, Runner runner){
        Product prod = new Product();
        try{
            if(runner.getInputStream().getClass().getCanonicalName().equals("java.io.FileInputStream")){
                prod = this.enterProductFromFile(runner.getScanner());
                request.setNewProduct(prod);
            }
            else {
                prod = this.enterProduct();
                request.setNewProduct(prod);
            }
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Неверный формат ввода данных из файла");
        }
    }

    /** Метод считывания продукта с консоли
     *
     * @return считанный продукт
     */
    public Product enterProduct(){
        Scanner sc = new Scanner(System.in);
        boolean correct = false;
        boolean hasOwner = false;
        Product product = new Product();
        Coordinates coordinates = new Coordinates();
        UnitOfMeasure unitOfMeasure;
        System.out.println("Введите название продукта");
        while(!correct){
            try{
                product.setName(sc.nextLine());
                correct = true;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Введите название продукта ещё раз");
            }
        }
        correct = false;
        System.out.println("Введите координату x продукта");
        while(!correct){
            try{
                coordinates.setX(Float.valueOf(sc.nextLine()));
                correct = true;
            }
            catch (NumberFormatException e){
                System.out.println("Координата x должна быть числом");
                System.out.println("Введите кординату x ещё раз");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Введите кординату x ещё раз");
            }
        }
        correct = false;
        System.out.println("Введите координату y продукта");
        while(!correct){
            try{
                coordinates.setY(Integer.valueOf(sc.nextLine()));
                correct = true;
            }
            catch (NumberFormatException e){
                System.out.println("Координата y должна быть числом");
                System.out.println("Введите кординату y ещё раз");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Введите кординату y ещё раз");
            }
        }
        product.setCoordinates(coordinates);
        correct = false;
        System.out.println("У продукта есть владелец?д/Н");
        while (!correct) {
            try {
                hasOwner = switch (sc.nextLine()) {
                    case "д" -> true;
                    case "Н" -> false;
                    default -> throw new WrongCommandFormat("строка должна содержать только д/Н");
                };
                correct =true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if(hasOwner){
            Person owner = new Person();
            correct = false;
            System.out.println("Введите имя владельца продукта");
            while(!correct){
                try{
                    owner.setName(sc.nextLine());
                    correct = true;
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Введите имя владельца ещё раз");
                }
            }
            correct = false;
            System.out.println("Введите дату рождения владельца продукта в формате \"гггг-мм-дд\"");
            while(!correct){
                try{
                    owner.setBirhday(sc.nextLine());
                    correct = true;
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Введите дату рождения владельца ещё раз");
                }
            }
            correct = false;
            System.out.println("Введите вес владельца продукта");
            while(!correct){
                try{
                    owner.setWeight(Long.parseLong(sc.nextLine()));
                    correct = true;
                }
                catch (NumberFormatException e){
                    System.out.println("Вес должен быть числом");
                    System.out.println("Введите вес владельца ещё раз");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Введите вес владельца ещё раз");
                }
            }
            product.setOwner(owner);
        }
        correct = false;
        System.out.println("Введите цену продукта");
        while(!correct){
            try{
                product.setPrice(Long.parseLong(sc.nextLine()));
                correct = true;
            }
            catch (NumberFormatException e){
                System.out.println("Цена должна быть числом");
                System.out.println("Введите цену ещё раз");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Введите цену ещё раз");
            }
        }
        correct = false;
        System.out.println("Введите меру измерения продукта\nСантиметр Грамм Милиграмм");
        while(!correct){
            try{
                unitOfMeasure = switch (sc.nextLine()){
                    case "Сантиметр" -> UnitOfMeasure.CENTIMETERS;
                    case "Грамм" -> UnitOfMeasure.GRAMS;
                    case "Милиграмм" -> UnitOfMeasure.MILLIGRAMS;

                    default -> throw new WrongCommandFormat("не та мера измерения");
                };
                product.setUnitOfMeasure(unitOfMeasure);
                correct = true;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Введите меру измерения ещё раз");
            }
        }
        return product;
    }

    /** Метод считывания продукта из файла
     *
     * @param sc Scanner читающий из файла
     * @return считанный продукт
     */
    public Product enterProductFromFile(Scanner sc){
        Product product = new Product();
        Coordinates coordinates = new Coordinates();
        UnitOfMeasure unitOfMeasure;

        String[] line = sc.nextLine().split(" ");
        if(!line[0].equals("Название:")){
            throw new WrongEnterOrder("(первым должно идти Название:)");
        }
        StringBuilder name = new StringBuilder();
        for(int i = 1; i<line.length; i++){
            name.append(line[i]);
        }
        product.setName(name.toString());

        line = sc.nextLine().split(" ");
        if(!line[0].equals("X:")){
            throw new WrongEnterOrder("(после названия идёт X:)");
        }
        coordinates.setX(Float.parseFloat(line[1]));

        line = sc.nextLine().split(" ");
        if(!line[0].equals("Y:")){
            throw new WrongEnterOrder("(после X: идёт Y:)");
        }
        coordinates.setY(Integer.valueOf(line[1]));
        product.setCoordinates(coordinates);

        line = sc.nextLine().split(" ");
        if(line[0].equals("Владелец:")){
            Person owner = new Person();

            line = sc.nextLine().split(" ");
            if(!line[4].equals("Имя:")){
                throw new WrongEnterOrder("(после Владелец: идёт Имя:)");
            }
            name = new StringBuilder();
            for(int i = 5; i<line.length; i++){
                name.append(line[i]);
            }
            owner.setName(name.toString());

            line = sc.nextLine().split(" ");
            if(line[4].equals("Дата") && line[5].equals("рождения:")){
                owner.setBirhday(line[6]);
                line = sc.nextLine().split(" ");
            }

            if(!line[4].equals("Вес:")){
                throw new WrongEnterOrder("(после Имя: идёт Вес:)");
            }
            owner.setWeight(Long.parseLong(line[5]));
            product.setOwner(owner);
            line = sc.nextLine().split(" ");
        }
        if(!line[0].equals("Цена:")){
            throw new WrongEnterOrder("(после идёт Цена:)");
        }
        product.setPrice(Long.parseLong(line[1]));

        line = sc.nextLine().split(" ");
        if(!line[0].equals("Мера") || !line[1].equals("измерения:")){
            throw new WrongEnterOrder("(после Цена: идёт Мера измерения:)");
        }
        unitOfMeasure = switch (line[2]){
            case "Сантиметр" -> UnitOfMeasure.CENTIMETERS;
            case "Грамм" -> UnitOfMeasure.GRAMS;
            case "Милиграмм" -> UnitOfMeasure.MILLIGRAMS;

            default -> throw new IllegalArgumentException("Не та мера измерения");
        };
        product.setUnitOfMeasure(unitOfMeasure);
        return product;
    }
}
