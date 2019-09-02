package com.AdamGinna;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.Socket;

public class ServeLoged extends Serve {

    //SING IN MODE
    public ServeLoged(Socket soc, EntityManagerFactory emf) throws IOException {
        super(soc,emf);
    }

    public ServeLoged(Serve ser) throws IOException {
         super(ser.getSocket(),ser.getDatabase());
    }

    /*
    @Override
    public void run() {

        String clientSentence;
        try {
            ToClient.write("ready");

                while (true) {
                    clientSentence = FromCilent.readLine();
                    System.out.println(clientSentence);
                    getPlaces(1,2);
                    //komunikacja i zapytania do bazy danych
                }


        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    */
}
