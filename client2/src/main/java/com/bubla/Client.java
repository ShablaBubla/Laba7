package com.bubla;


import com.bubla.exceptions.TimeOut;
import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.SerializationUtils;
import com.bubla.message.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Arrays;
import java.util.Iterator;

public class Client {
    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;

    private final DatagramChannel client;
    private final InetSocketAddress addr;


    public Client(InetAddress address, int port) throws IOException {
        this.addr = new InetSocketAddress(address, port);
        this.client = DatagramChannel.open();
        this.client.configureBlocking(false);
    }

    private void sendData(byte[] data) throws IOException {
        byte[][] ret = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }
        for(int i = 0; i < ret.length; i++) {
            byte[] msg = ret[i];
            if (i == ret.length - 1) {
                msg = Bytes.concat(msg, new byte[]{1});
                client.send(ByteBuffer.wrap(msg), addr);
            } else {
                byte[] answer = Bytes.concat(msg, new byte[]{0});
                client.send(ByteBuffer.wrap(answer), addr);
            }
        }
    }

    private byte[] receiveData() throws IOException {
        boolean received = false;
        byte[] result = new byte[0];

        while(!received) {
            byte[] data = receiveData(PACKET_SIZE);

            if (data[data.length - 1] == 1) {
                received = true;
            }
            result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
        }

        return result;
    }

    private byte[] receiveData(int bufferSize) throws IOException {
//        Selector selector = SelectorProvider.provider().openSelector();
//        client.register(selector, SelectionKey.OP_READ);

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress address = null;
//        int timeout = 5000;
//        if(selector.select(timeout) == 0){
//            throw new TimeOut();
//        }
        while(address == null) {
            address = client.receive(buffer);
        }
        return buffer.array();
    }

    public byte[] sendAndReceiveData(byte[] data) {
        byte[] received = new byte[0];
        try {
            sendData(data);
            try {
                received = receiveData();
            }catch (IOException e){
                System.out.println("Не удалось получить данные");
            }
        }catch (IOException e){
            System.out.println("Не удалось отпраивть данные");
        }
        return received;
    }
}
