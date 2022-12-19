package org.example.Entityes;

//import com.sun.jdi.PrimitiveValue;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
public class Message {
    @Id
    @GeneratedValue
    private Long ID;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;
    private String text;

    private final String dataOfSend;

    public Message() {
        dataOfSend = new Date().toString();
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
        user.addMsg(this);
    }
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }

}
