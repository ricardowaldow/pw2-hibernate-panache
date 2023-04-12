package dev.ifrs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Channel extends PanacheEntity{
    
    private String hash;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "channels")
    @JsonIgnore
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id")
    private List<Message> messages;

    public Channel() {
        this.users = new ArrayList<>();
        this.hash = UUID.randomUUID().toString();
    }

    public void addUser(User obj) {
        this.users.add(obj);
    }  
    
    public void addMessage(Message obj) {
        this.messages.add(obj);
    }  
}
