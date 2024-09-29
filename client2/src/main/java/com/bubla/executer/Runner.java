package com.bubla.executer;

import com.bubla.Client;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Stack;

@Data
public class Runner {
    private InputStream inputStream;
    private Scanner scanner;
    private Stack<String> cmdStack = new Stack<>();
    private Client client;
    private String clientName;
    private int cliendID;

    public Runner(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        this.client = new Client(InetAddress.getLocalHost(), 3412);
    }

    public Runner() throws IOException {
        this.client = new Client(InetAddress.getLocalHost(), 3412);
    }


    /** Метод добавления команды в стек
     *
     * @param cmd команда
     */
    public void pushCmd(String cmd){
        cmdStack.push(cmd);
    }

    /** Метод удаление команды из стека
     *
     * @return команда
     */
    public String popCmd(){
        return cmdStack.pop();
    }
}
