package com.AdamGinna;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Serve extends Thread {

    private Socket socket;
    private BufferedReader FromCilent;
    private DataOutputStream ToClient;
    private boolean loged = false;

    public Serve(Socket soc) throws IOException {
        socket = soc;
        this.FromCilent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.ToClient = new DataOutputStream(socket.getOutputStream());
    }

    public Serve(BufferedReader FromCilent, DataOutputStream ToClient) {
        this.FromCilent = FromCilent;
        this.ToClient = ToClient;
    }

    public void login(boolean loged)
    {
        this.loged = loged;
    }

    public boolean login()   // login mail + password
    {
        this.loged = true;

        return loged;
    }

    @Override
    public void run() {

        String clientSentence;
        try {
            ToClient.writeBytes("ready");

            if(loged) {

                while (true) {                                              //GUEST MODE
                    clientSentence = FromCilent.readLine();
                    System.out.println(clientSentence);
                    //komunikacja i zapytania do bazydanych
                }
            }
            else
            {
                while (true) {                                              //SING IN MODE
                    clientSentence = FromCilent.readLine();
                    System.out.println(clientSentence);
                    //komunikacja i zapytania do bazydanych
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
