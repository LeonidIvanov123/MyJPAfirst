package org.example.Entityes;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue
    private Long ID;

    @OneToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "message_id")
    protected List<Message> messages = new ArrayList<>();

    private String password;

    public List<Message> getMessages() {
        return messages;
    }

    public void addMsg(Message message){
        messages.add(message);
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column(unique = true)
    String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
