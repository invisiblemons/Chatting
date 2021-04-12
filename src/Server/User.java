package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
public class User {

    String name;
    BufferedReader in;
    DataOutputStream out;
    Vector<String> clients;

    public User(String name, BufferedReader in, DataOutputStream out, Vector<String> clients) {
        this.name = name;
        this.in = in;
        this.out = out;
        this.clients = clients;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<String> getClients() {
        return clients;
    }

    public void setClients(Vector<String> clients) {
        this.clients = clients;
    }

}
