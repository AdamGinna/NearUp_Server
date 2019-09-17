package com.AdamGinna;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.net.*;



public class Main {
     public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyUnit");

    public static void main(String[] args) {


        String clientSentence = null;
        ServerSocket welcome = null;



        try {
            welcome = new ServerSocket(6789);
            System.out.println("IP Address:- " + welcome.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket connectionSocket = null;
            Serve Mguest = null;
            try {

                connectionSocket = welcome.accept();


            BufferedReader FromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            clientSentence = FromClient.readLine();

            Mguest = new Serve(connectionSocket,emf);

            } catch (IOException e) {
                break;
            }
            // Checking device and mode (SING IN or GEUST)
            if(clientSentence.equals("Mguest"))           // MOBILE GUEST
            {

            }
            else if(clientSentence.equals("Mlogin"))       // MOBLIE SING IN
            {
                Mguest = Mguest.login();
            }
            else if(clientSentence.equals("Wguest"))       // WEB GUEST
            {

            }
            else if(clientSentence.equals("awd&&3"))       // WEB SING IN
            {
                try {
                    Mguest = new ServeAdmin(Mguest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {

            }
            Mguest.start();
        }



    }
}
