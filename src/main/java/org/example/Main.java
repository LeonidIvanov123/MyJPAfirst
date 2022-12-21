package org.example;

import org.example.Entityes.Message;
import org.example.Entityes.Rules;
import org.example.Entityes.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        Message msg;

        //admin уникальный
        try {
            User admin = session.createQuery("FROM User where username='admin'", User.class).getSingleResult();
            msg = new Message();
            msg.setUser(admin);
            msg.setText("Start program in " + new Date().toString());
            storMSG(msg);
            msg = null;
        }catch (NoResultException e){}

        System.out.println("Введите Имя: ");

        data = scanner.nextLine();
        User user = null;
        List<User> users = new ArrayList<>();
        users = session.createCriteria(User.class).list();
        for (User u:users) {
            if(u.getUsername().equals(data)) {
                user = u;
                System.out.println("\n\nHi again, " + user.getUsername());
                System.out.println("\nYou wrote last message: "+ user.getMessages().get(user.getMessages().size()-1).getText() +
                        " num of messages = " + user.getMessages().size());
                break;
            }
        }
        if(user == null) {
            user = new User();
            user.setUsername(data);
            user.setPassword("123");
            session.save(user);
        }


        System.out.println("Введите сообщение для сохранения в БД (или 'Exit' для выхода из программы): ");

        while (true) {
            switch (data = scanner.nextLine()) {
                case "Exit" -> System.exit(0);
                case "Admin" -> {
                    Rules rules = new Rules();
                    rules.setAdmin(true);
                    user.setRules(rules);
                    rules.setUser(user);
                    Transaction tx = session.beginTransaction();
                    session.save(rules);
                    tx.commit();
                    System.out.println("At now " + user.getUsername() + " admin.");
                }
                default -> {
                    msg = new Message();
                    msg.setUser(user);
                    msg.setText(data);
                    //user.addMsg(msg); //появляется лишний update к БД - нужно ли?!?
                    storMSG(msg);
                }
            }
        }
            //switch-case
  /*      while(!(data = scanner.nextLine()).equals("Exit")){
            msg = new Message();
            msg.setUser(user);
            msg.setText(data);
            //user.addMsg(msg); //появляется лишний update к БД - нужно ли?!?
            storMSG(msg);
        }
*/
    }

    public static boolean storMSG(Message msg){
        Transaction tx = session.beginTransaction();
        session.save(msg);
        tx.commit();
        return true;
    }

    private static Session getSession() throws ServiceException{
        Session session = null;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
        }catch (ServiceException ie){
            System.out.println("Не удается соединиться с БД.");
            System.exit(3);
        }
        return session;
    }
}
