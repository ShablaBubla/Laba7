package com.bubla;

import com.bubla.classes.LinkedHashMapOfProducts;
import com.bubla.classes.Product;
import com.bubla.exceptions.StopServerException;
import com.bubla.executer.Executer;
import com.bubla.executer.ServerApplication;
import com.bubla.executer.ServerExecuter;
import com.bubla.executer.Transmiter;
import com.bubla.message.Request;
import com.bubla.message.Response;
import com.google.common.primitives.Bytes;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.SerializationException;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

@Data
public class Server {
        private final int PACKET_SIZE = 1024;
        private final int DATA_SIZE = PACKET_SIZE - 1;
        private DatagramSocket ds;
        private InetAddress host;
        private int port = 3412;
        private SocketAddress addr;
        private boolean running = true;
        private ServerApplication application;
        private boolean oneMore = true;

        public Server(InetAddress address, LinkedHashMap<String, Product> products) throws SocketException {
            this.host = address;
            this.addr = new InetSocketAddress(this.host, this.port);
            this.ds = new DatagramSocket(this.addr);
            this.ds.setReuseAddress(true);
            this.application = new ServerApplication(new LinkedHashMapOfProducts(products));
        }

        public Pair<Byte[], SocketAddress> receiveData() throws IOException {
            boolean recieved = false;
            byte[]result = new byte[0];
            SocketAddress address = null;

            while(!recieved){
                byte[] data = new byte[PACKET_SIZE];
                DatagramPacket dp = new DatagramPacket(data, PACKET_SIZE);
                ds.receive(dp);
                oneMore = true;
                address = dp.getSocketAddress();
                if (data[data.length - 1] == 1){
                    recieved = true;
                }
                result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
            }
            return new ImmutablePair<>(ArrayUtils.toObject(result), address);
        }

        public void sendData(byte[] data, SocketAddress address) throws IOException {
            Transmiter transmiter = new Transmiter(data, true, ds, address);
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(transmiter);
        }

        public void connectToClient(SocketAddress address) throws SocketException {
            ds.connect(address);
        }

        public void disconnectFromClient() {
            ds.disconnect();
        }

        public void close() {
            ds.close();
        }

    public void run() {
            Executer executer = new Executer();
            new Thread(() -> {
                ServerExecuter serverExecuter = new ServerExecuter();
                Scanner sc = new Scanner(System.in);
                while(running){
                try{
                    System.out.println(serverExecuter.accomplish(sc.nextLine(), null, this.application));
                }catch (StopServerException e){
                    System.out.println(e.getMessage());
                    this.running = false;
                }
                }

            }).start();
            while (running) {
                if(oneMore) {
                    oneMore = false;
                    Pair<Byte[], SocketAddress> dataPair = null;
                    try {
                        dataPair = receiveData();
                    } catch (Exception e) {
                        disconnectFromClient();
                    }
                    Byte[] dataFromClient = dataPair.getKey();
                    SocketAddress clientAddress = dataPair.getValue();
                    try {
                        connectToClient(clientAddress);
                    } catch (Exception e) {
                    }
                    new Thread(() -> {
                        Request request = null;
                        try {
                            request = SerializationUtils.deserialize(ArrayUtils.toPrimitive(dataFromClient));
                        } catch (SerializationException e) {
                            disconnectFromClient();
                        }
                        Request finalRequest = request;
                        new Thread(() -> {
                            String cmd = finalRequest.getCmd();
                            String args = finalRequest.getArgs();
                            this.application.fillApplication(finalRequest.getNewProduct());
                            this.application.setUserID(finalRequest.getUserID());
                            String msg = executer.accomplish(cmd, args, this.application) + "\n";
                            Response response = new Response(msg);
                            response.setRunning(application.isRunnig());

                            byte[] data = SerializationUtils.serialize(response);
                            try {
                                sendData(data, clientAddress);
                            } catch (Exception ignored) {
                            }
                            this.application.setRunnig(true);
                        }).start();
                    }).start();
                    disconnectFromClient();
                }
            }
            close();
    }
}
