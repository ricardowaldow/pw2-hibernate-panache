package dev.ifrs.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class User extends PanacheEntity {

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)

    private List<Channel> channels;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Message> messages;

    public User() {
        this.channels = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addChannel(Channel obj) {
        this.channels.add(obj);
    }

    public void addMessage(Message obj) {
        this.messages.add(obj);
    }    

}
