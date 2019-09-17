package com.AdamGinna;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ServeAdmin extends ServeLoged {


    public ServeAdmin(Socket soc, EntityManagerFactory emf) throws IOException {
        super(soc, emf);
    }

    public ServeAdmin(Serve ser) throws IOException {
        super(ser);
    }
    @Override
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
        else if(mode.equals("editPlace"))
        {
            String clientSentence;
            clientSentence = FromCilent.readLine();
            ObjectMapper mapper = new ObjectMapper();
            Place place = mapper.readValue(clientSentence,Place.class);



            EntityManager entitymanager= database.createEntityManager();
            EntityTransaction transaction= entitymanager.getTransaction();
            Place orginalPlace= entitymanager.find(Place.class, place.getId());
            transaction.begin();

            orginalPlace.setDescription(place.getDescription());
            orginalPlace.setLatitude(place.getLatitude());
            orginalPlace.setLongitude(place.getLongitude());
            orginalPlace.setName(place.getName());

            transaction.commit();
            entitymanager.close();

        }
        else if(mode.equals("deletePlace"))
        {
            String clientSentence;
            clientSentence = FromCilent.readLine();
            JSONObject json = new JSONObject(clientSentence);
            EntityManager entitymanager= database.createEntityManager();
            EntityTransaction transaction= entitymanager.getTransaction();
            Place orginalPlace= entitymanager.find(Place.class, json.get("id"));
            transaction.begin();
            entitymanager.remove(orginalPlace);
            transaction.commit();
            entitymanager.close();
        }
        else if(mode.equals("users"))
        {
            ObjectMapper mapper = new ObjectMapper();

            List<User> users = getUsers();

            int i = 0;
            for(User o: users) {
                String jsonS = mapper.writeValueAsString(o);
                ToClient.write(jsonS);
                ToClient.newLine();
            }
            ToClient.write("*End*");
            ToClient.newLine();
            ToClient.flush();

        }
        else if(mode.equals("deleteUser"))
        {
            String clientSentence;
            clientSentence = FromCilent.readLine();
            JSONObject json = new JSONObject(clientSentence);
            EntityManager entitymanager= database.createEntityManager();
            EntityTransaction transaction= entitymanager.getTransaction();
            User orginalUser = entitymanager.find(User.class, json.get("id"));
            transaction.begin();
            entitymanager.remove(orginalUser);
            transaction.commit();
            entitymanager.close();
        }


    }

    protected List<User> getUsers()
    {
        EntityManager em = database.createEntityManager();


        Query q= em.createQuery("SELECT U FROM User U");
        List<User> list = q.getResultList();

        return list.stream().sorted((p1,p2)->(p1.getId()-p2.getId())).collect(Collectors.toList());
    }
}
