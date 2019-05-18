package com.AdamGinna;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) {

        String clientSentence = null;
        ServerSocket welcome = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyUnit");
        try {
            welcome = new ServerSocket(6789);
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
            System.out.println("Received: " + clientSentence);
            Mguest = new Serve(connectionSocket,emf);
            } catch (IOException e) {
                break;
            }
            // Checking device and mode (SING IN or GEUST)
            if(clientSentence.equals("Mguest"))           // MOBILE GUEST
            {
                Mguest.run();
            }
            else if(clientSentence.equals("Mlogin"))       // MOBLIE SING IN
            {
                try {
                    Mguest = Mguest.login();
                    Mguest.run();
                } catch (IOException e) {
                    Mguest.run();
                }


            }
            else if(clientSentence.equals("Wguest"))       // WEB GUEST
            {

            }
            else if(clientSentence.equals("Wlogin"))       // WEB SING IN
            {

            }

        }



    }
}
