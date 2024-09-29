package com.bubla.executer;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class Transmiter extends RecursiveAction {
    private final int DATA_SIZE = 1023;
    private final int PACKET_SIZE = 1024;
    private boolean isLast;
    private byte[] data;
    private DatagramSocket ds;
    private SocketAddress address;

    public Transmiter(byte[] data, boolean isLast, DatagramSocket ds, SocketAddress address){
        this.data = data;
        this.isLast = isLast;
        this.ds = ds;
        this.address = address;
    }
    @Override
    protected void compute() {
        byte[][] ret = new byte[(int) Math.ceil(data.length / (double) DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }

        for (int i = 0; i < ret.length; i++) {
            byte[] msg = ret[i];
            if (i == ret.length - 1) {
                byte[] lastMsg = Bytes.concat(msg, new byte[]{1});
                DatagramPacket dp = new DatagramPacket(lastMsg, PACKET_SIZE, address);
                try {
                    ds.send(dp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                DatagramPacket dp = new DatagramPacket(ByteBuffer.allocate(PACKET_SIZE).put(msg).array(), PACKET_SIZE, address);
                try {
                    ds.send(dp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
