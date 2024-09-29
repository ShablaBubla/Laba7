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
public class Registration {
    private Request request;
    private Response response;
    private Runner runner;
    private boolean isAvailable = true;

    public Registration(Runner runner) throws IOException {
        this.response = new Response("");
        this.runner = runner;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        Client client;
        String name = "";
        byte[] msg;
        while (this.response.isRunning()) {
            System.out.println("Введите имя пользователя");
            name = sc.nextLine();
            System.out.println("Ввведите пароль");
            String password = sc.nextLine();
            request = new Request("reg", name + ":" + password, -1);

            client = runner.getClient();
            msg = SerializationUtils.serialize(request);
            try {
                msg = client.sendAndReceiveData(msg);
                this.response = SerializationUtils.deserialize(msg);
                System.out.println(response.getResponse().split(":")[0]);
            } catch (TimeOut e) {
                System.out.println(e.getMessage());
                this.isAvailable = false;
            }
            while (!isAvailable) {
                try {
                    client = runner.getClient();
                    msg = SerializationUtils.serialize(new Request(null, null, -1));
                    msg = client.sendAndReceiveData(msg);
                    this.response = SerializationUtils.deserialize(msg);
                    System.out.print(response.getResponse());
                    this.isAvailable = true;
                } catch (TimeOut ignored) {
                }
            }
        }
        runner.setClientName(name);
        runner.setCliendID(Integer.valueOf(response.getResponse().split(":")[1].split("\n")[0]));
    }
}