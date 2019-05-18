package com.AdamGinna;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.Stream;

public class Serve extends Thread {                                     //GUEST MODE

    protected EntityManagerFactory database;
    protected Socket socket;
    protected BufferedReader FromCilent;
    protected DataOutputStream ToClient;
    protected boolean loged = false;

    public Serve(Socket soc, EntityManagerFactory emf) throws IOException {
        socket = soc;
        this.FromCilent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.ToClient = new DataOutputStream(socket.getOutputStream());
        database = emf;
    }


    public Serve(BufferedReader FromCilent, DataOutputStream ToClient) {
        this.FromCilent = FromCilent;
        this.ToClient = ToClient;
    }

    public void login(boolean loged)
    {
        this.loged = loged;
    }

    public Serve login()   // login mail + password
    {
        Serve log;
        try {
            log = new ServeLoged(socket,database);
        } catch (IOException e) {
            log = this;
        }
        return log;
    }

    @Override
    public void run() {

        String clientSentence;
        try {
            ToClient.writeBytes("ready");

            if(loged) {

                ServeLoged n = new ServeLoged(socket,database);
                n.run();
                this.interrupt();

            }
            else
            {
                while (true) {
                    clientSentence = FromCilent.readLine();
                    System.out.println(clientSentence);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected Stream getPLaces(double x,double y)
    {
        EntityManager em = database.createEntityManager();

        String x2 = "(P.latitude - " + x +") * (P.latitude - " + x+ ")";
        String y2 = "(P.longitude - " + y +") *  (P.longitude - " + y +")";
        Query q= em.createQuery("SELECT * FROM places  P WHERE " + x2 + " + " + y2 + "<  0.1" );
        return q.getResultList().stream();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public EntityManagerFactory getDatabase() {
        return database;
    }

    public void setDatabase(EntityManagerFactory database) {
        this.database = database;
    }
}
