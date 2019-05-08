package com.AdamGinna;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String clientSentence;
        ServerSocket welcome = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcome.accept();
            BufferedReader FromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream ToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = FromClient.readLine();
            System.out.println("Received: " + clientSentence);

            // Checking device and mode (SING IN or GEUST)
            if(clientSentence.equals("Mguest"))           // MOBILE GUEST
            {
                Serve Mguest = new Serve(connectionSocket);
                Mguest.run();
            }
            else if(clientSentence.equals("Mlogin"))       // MOBLIE SING IN
            {
                Serve Mlogin = new Serve(connectionSocket);
                Mlogin.login();
                Mlogin.run();
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
