package com.AdamGinna;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class Serve extends Thread {

    private BufferedReader FromCilent;
    private DataOutputStream ToClient;
    private boolean loged;

    public Serve(BufferedReader FromCilent, DataOutputStream ToClient,boolean loged) {
        this.FromCilent = FromCilent;
        this.ToClient = ToClient;
        this.loged = loged;
    }

    public void login(boolean loged)
    {
        this.loged = loged;
    }

    public void login()   // login mail + password
    {
        this.loged = true;
    }

    @Override
    public void run() {

        String clientSentence;
        try {
            ToClient.writeBytes("ready");

            if(loged) {

                while (true) {                                              //GUEST MODE
                    clientSentence = FromCilent.readLine();
                    //komunikacja i zapytania do bazydanych
                }
            }
            else
            {
                while (true) {                                              //SING IN MODE
                    clientSentence = FromCilent.readLine();
                    //komunikacja i zapytania do bazydanych
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
