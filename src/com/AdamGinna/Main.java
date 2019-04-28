package com.AdamGinna;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcome = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcome.accept();
            BufferedReader FromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream ToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = FromClient.readLine();
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + 'n';
            ToClient.writeBytes(capitalizedSentence);
        }


    }
}
