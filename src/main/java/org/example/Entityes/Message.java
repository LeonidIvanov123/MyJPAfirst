package org.example.Entityes;

//import com.sun.jdi.PrimitiveValue;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
