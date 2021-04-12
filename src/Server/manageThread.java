package Server;

import Server.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MONS
 */
public class manageThread extends Thread {

    String name;
    Vector<User> clients;
    BufferedReader in;
    DataOutputStream out;
    DataOutputStream outReceiver;

    public manageThread(BufferedReader in, String name, Vector<User> clients) {

        this.in = in;
        this.name = name;
        this.clients = clients;

    }

    @Override
    public void run() {

        while (true) {
            try {

                //output to receiversocket
                while (in.ready()) {
                    String text = in.readLine();
                    if (text.matches(".+==>.+")) {
                        String[] S = text.split("==>");
                        String sender = S[0];
                        String receiver = S[1];

                        if (S[1].matches(".+-.*")) {
                            receiver = S[1].split("-")[0];
                            String msg = S[1].split("-")[1];
                            for (User client : clients) {
                                if (client.getName().equals(receiver)) {

                                    outReceiver = client.getOut();
                                    outReceiver.writeBytes(sender);
                                    outReceiver.writeBytes("@!@");
                                    outReceiver.writeBytes(msg);
                                    outReceiver.writeByte(13);
                                    outReceiver.writeByte(10);
                                    outReceiver.flush();
                                }
                            }
                        } else {
                            try {
                                receiver = S[1].split("!")[0];
                                String msg = S[1].split("!")[1];
                                for (User client : clients) {
                                    if (client.getName().equals(receiver)) {

                                        outReceiver = client.getOut();
                                        outReceiver.writeBytes(sender);
                                        outReceiver.writeBytes("@##@");
                                        outReceiver.writeBytes(msg + "\n");
                                        while(in.ready())
                                        {
                                            outReceiver.writeBytes(in.readLine());
                                            outReceiver.writeBytes("\n");
                                        }
                                        outReceiver.writeByte(13);
                                        outReceiver.writeByte(10);
                                        outReceiver.flush();
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }

    }

}
