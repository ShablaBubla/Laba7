package com.bubla.executer;

import com.bubla.Client;
import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.exceptions.TimeOut;
import com.bubla.message.Request;
import com.bubla.message.Response;
import lombok.Data;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

@Data
public class Authentication {
    private Request request;
    private Response response;
    private Runner runner;
    private boolean isAvailable = true;

    public Authentication(Runner runner) throws IOException {
        this.response = new Response("");
        this.runner = new Runner();
    }

    public boolean start(){
        Scanner sc = new Scanner(System.in);
        System.out.println("login/reg");
        while (true) {
            String word = sc.nextLine();
            if (word.equals("reg")) {
                return true;
            }else if(word.equals("login")){
                break;
            }
            System.out.println("Введите login или reg");
        }
        String name = "";
        Client client;
        byte[] msg;
        while(this.response.isRunning()){
            System.out.println("Введите имя пользователя");
            name = sc.nextLine();
            System.out.println("Введите пароль");
            String password = sc.nextLine();
            request = new Request("auth", name + ":" + password, -1);

            client = runner.getClient();
            msg = SerializationUtils.serialize(request);
            try {
                msg = client.sendAndReceiveData(msg);
                this.response = SerializationUtils.deserialize(msg);
                System.out.println(response.getResponse().split(":")[0]);
            }catch (TimeOut e){
                System.out.println(e.getMessage());
                this.isAvailable = false;
            }
            while (!isAvailable){
                try{
                    client = runner.getClient();
                    msg = SerializationUtils.serialize(new Request(null, null, -1));
                    msg = client.sendAndReceiveData(msg);
                    this.response = SerializationUtils.deserialize(msg);
                    System.out.print(response.getResponse());
                    this.isAvailable = true;
                }catch (TimeOut ignored){
                }
            }
        }
        runner.setClientName(name);
        runner.setCliendID(Integer.valueOf(response.getResponse().split(":")[1].split("\n")[0]));
        return false;
    }
}
