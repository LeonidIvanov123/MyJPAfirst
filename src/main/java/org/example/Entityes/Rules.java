package org.example.Entityes;

import com.mysql.cj.Constants;

import javax.persistence.*;

@Entity
public class Rules {
    @Id
    @GeneratedValue
    protected long id;

    @JoinColumn(name="admin")
    protected boolean admin;
    @OneToOne
    protected User user;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
