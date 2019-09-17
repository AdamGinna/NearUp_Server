package com.AdamGinna;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.*;
import java.net.Socket;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
            ToClient.newLine();
            ToClient.flush();


            if(loged) {

                ServeLoged n = new ServeLoged(socket,database);
                n.run();
                this.interrupt();
            }
            else
            {
                while (true) {
                    if((clientSentence = FromCilent.readLine()) != null) {
                        System.out.println(clientSentence);
                        serve(clientSentence);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void serve(String mode) throws IOException {

        if(mode.equals("places"))
        {
            String clientSentence;
            clientSentence = FromCilent.readLine();
            Scanner scanner = new Scanner(clientSentence);
            ObjectMapper mapper = new ObjectMapper();

            List<Place> places = getPlaces(scanner.nextDouble(),scanner.nextDouble());

            int i = 0;
            for(Place o: places) {
                String jsonS = mapper.writeValueAsString(o);
                ToClient.write(jsonS);
                ToClient.newLine();
            }
            ToClient.write("*End*");
            ToClient.newLine();
            ToClient.flush();

        }
    }

    protected List<Place> getPlaces(double x, double y)
    {
        EntityManager em = database.createEntityManager();

        String x2 = "(P.latitude - " + x +") * (P.latitude - " + x+ ")";
        String y2 = "(P.longitude - " + y +") *  (P.longitude - " + y +")";
        //Query q= em.createQuery("SELECT P FROM Place P WHERE " + x2 + " + " + y2 + "<  0.1" );
        Query q= em.createQuery("SELECT P FROM Place P");
        List<Place> list = q.getResultList();

        return list.stream().sorted((p1,p2)->Place.compare(p1,p2,x,y)).collect(Collectors.toList());
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
