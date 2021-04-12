package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MONS
 */
public class Server implements Runnable {

    ServerSocket srvSocket;
    BufferedReader in;
    DataOutputStream out;
    Thread server; //thread for exploring connections from staffs
    Vector<User> clients;
    Vector<String> nameClients;
    manageThread manage;
    DataOutputStream outClient;
    boolean state = false;

    public Server() {
        int serverPort = 1;
        try {
            srvSocket = new ServerSocket(serverPort);

        } catch (Exception e) {
            System.out.println("Error");
        }
        clients = new Vector<User>();
        nameClients = new Vector<String>();
        server = new Thread(this);
        server.start();

        Thread chk = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (nameClients.size() > 1) {
                            for (User client : clients) {

                                outClient = client.getOut();
                                for (int i = 0; i < nameClients.size(); i++) {
                                    outClient.writeBytes("@@" + nameClients.get(i) + "\n");

                                }
                                outClient.writeByte(13);
                                outClient.flush();
                                client.setClients(nameClients);
                            }

                        }

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        chk.start();

    }

    @Override
    public void run() {
        while (true) {
            try { //wait for client
                System.out.println("wait");
                Socket UserSocket = srvSocket.accept();
                if (UserSocket != null) {
                    //get staff
                    in = new BufferedReader(new InputStreamReader(UserSocket.getInputStream()));
                    out = new DataOutputStream(UserSocket.getOutputStream());
                    String S = in.readLine();
                    int pos = S.indexOf(":");
                    String userName = S.substring(pos + 1);
                    if (!nameClients.contains(userName)) {
                        nameClients.add(userName);
                        User client = new User(userName, in, out, nameClients);
                        clients.add(client);
                    }
                    manage = new manageThread(in, userName, clients);
                    manage.start();

                }

                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        Server obj = new Server();
    }
}
