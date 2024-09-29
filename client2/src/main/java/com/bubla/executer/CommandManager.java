package com.bubla.executer;

import com.bubla.Client;
import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.exceptions.TimeOut;
import com.bubla.message.Request;
import com.bubla.message.Response;
import lombok.Data;
import org.apache.commons.lang3.SerializationUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/** Класс управления командами
 *
 */
@Data
public class CommandManager {
    private Response response;
    private Request request;
    private Executer executer;
    private Runner runner;
    private LinkedHashMapOfProducts linkedHashMapOfProducts;
    private Scanner sc;
    private String cmd;
    private String arg;
    private boolean isCmd;
    private boolean isAvailable = true;

    public CommandManager() throws IOException {
        this.linkedHashMapOfProducts = new LinkedHashMapOfProducts();
        this.response = new Response("");
        this.runner = new Runner(System.in);
        this.executer = new Executer();
    }

    public CommandManager(Runner runner, Request request){
        this.runner = runner;
        this.request = request;
        this.response = new Response("");
        this.executer = new Executer();
    }

    /** Считывание команды из потока-ввода
     *
     * @param inputStream поток-ввода
     */
    public void start(InputStream inputStream){
        sc = new Scanner(inputStream);
        runner.setScanner(sc);
        Client client;
        byte[] msg;
        while(this.response.isRunning()){
            runner.setInputStream(inputStream);
            String[] tokens = sc.nextLine().split(" ");
            this.isCmd = true;
            try{
                this.cmd = tokens[0];
                this.arg = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
            }catch (IndexOutOfBoundsException e){
                this.cmd = "";
                this.arg = "";
            }
            request = new Request(this.cmd, this.arg);
            this.executer.accomplish(this.request, this.runner);

            client = runner.getClient();
            msg = SerializationUtils.serialize(request);
            try {
                msg = client.sendAndReceiveData(msg);
                this.response = SerializationUtils.deserialize(msg);
                System.out.print(response.getResponse());
            }catch (TimeOut e){
                System.out.println(e.getMessage());
                this.isAvailable = false;
            }
            while (!isAvailable){
                try{
                    client = runner.getClient();
                    msg = SerializationUtils.serialize(new Request(null, null));
                    msg = client.sendAndReceiveData(msg);
                    this.response = SerializationUtils.deserialize(msg);
                    System.out.print(response.getResponse());
                    this.isAvailable = true;
                    msg = SerializationUtils.serialize(this.request);
                    msg = client.sendAndReceiveData(msg);
                    this.response = SerializationUtils.deserialize(msg);
                    System.out.print(response.getResponse());
                }catch (TimeOut ignored){
                }
            }
        }
    }
}
