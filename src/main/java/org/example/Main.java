package org.example;

import org.example.Entityes.Message;
import org.example.Entityes.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Session session;

    public static void main(String[] args) {
        //проверка доступности БД из кода напрямую без hibernate
        //Connection dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/myhibernate", "hiberuser", "991_Roter");

        String data = null;
        Scanner scanner = new Scanner(System.in);
        session = getSession();

        System.out.println("Введите Имя: ");

        data = scanner.nextLine();
        User user = null;
        List<User> users = new ArrayList<>();
        users = session.createCriteria(User.class).list();
        for (User u:users) {
            if(u.getUsername().equals(data)) {
                user = u;
                System.out.println("Hi again, " + user.getUsername());
                break;
            }
        }
        if(user == null) {
            user = new User();
            user.setUsername(data);
            user.setPassword("123");
            session.save(user);
        }



        //Transaction tx = session.beginTransaction();

        //tx.commit();
        //tx = null;

        //Transaction tx = session.beginTransaction();
        Message msg = new Message();
        user.addMsg(msg);
        msg.setText("hi hibernate!, start program at " + new Date().toString());
        storMSG(msg);


        System.out.println("Введите сообщение для сохранения в БД (или 'Exit' для выхода из программы): ");


            //switch-case
        do {
            data = scanner.nextLine();
            msg = new Message();
            msg.setUser(user);
            msg.setText(data);
            storMSG(msg);
        }while(!data.equals("Exit"));

    }

    public static boolean storMSG(Message msg){
        Transaction tx = session.beginTransaction();
        session.save(msg);
        tx.commit();
        return true;
    }

    private static Session getSession(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory.openSession();
    }
}
