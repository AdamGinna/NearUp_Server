package com.AdamGinna;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Serve extends Thread {                                     //GUEST MODE

    protected EntityManagerFactory database;
    protected Socket socket;
    protected BufferedReader FromCilent;
    protected BufferedWriter ToClient;
    protected boolean loged = false;




    public Serve(Socket soc, EntityManagerFactory emf) throws IOException {
        socket = soc;
        this.FromCilent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.ToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        database = emf;
    }


    public Serve(BufferedReader FromCilent, BufferedWriter ToClient) {
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
            ToClient.write("ready");

            if(loged) {

                ServeLoged n = new ServeLoged(socket,database);
                n.run();
                this.interrupt();

            }
            else
            {
                while (true) {
                    clientSentence = FromCilent.readLine();
                    Scanner scanner = new Scanner(clientSentence);
                    ObjectMapper mapper = new ObjectMapper();

                    Object[] places = getPlaces(scanner.nextDouble(),scanner.nextDouble());
                    String[] Jsons = new String[places.length];
                    int i = 0;
                    for(Object o: places) {
                        String jsonS = mapper.writeValueAsString(o);
                        Jsons[i] = jsonS;
                        i++;
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("places",places);
                    ToClient.write(jsonObject.toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected Object[] getPlaces(double x, double y)
    {
        EntityManager em = database.createEntityManager();

        String x2 = "(P.latitude - " + x +") * (P.latitude - " + x+ ")";
        String y2 = "(P.longitude - " + y +") *  (P.longitude - " + y +")";
        Query q= em.createQuery("SELECT P FROM places  P WHERE " + x2 + " + " + y2 + "<  0.1" );
        return q.getResultList().stream().sorted().toArray();
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
