package dev.ifrs;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dev.ifrs.model.Channel;
import dev.ifrs.model.Message;
import dev.ifrs.model.User;

@Path("/user")
public class Example {
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User createUser(
        @FormParam("name") final String name
    ) {
        User user = new User();
        user.setName(name);
        
        user.persist();

        return user;
    }

    @PUT
    @Path("/add/channel")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User addUserChannel(
        @FormParam("name") final String name
    ) {
        User user = User.find("name", name).firstResult();
        
        Channel channel = new Channel();

        user.addChannel(channel);
        user.persist();

        return user;
    }

    @PUT
    @Path("/add/message")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User addUserMessage(
        @FormParam("name") final String name,
        @FormParam("hash") final String hash,
        @FormParam("text") final String text
    ) {
        
        User user = User.find("name", name).firstResult();

        Channel channel = user.getChannels().stream()
                            .filter(chn -> hash.equals(chn.getHash()))
                            .findAny()
                            .orElse(null);

        if (channel == null) {
            throw new IllegalArgumentException("Canal nao existe");
        }

        Message message = new Message();
        message.setText(text);
        user.addMessage(message);
        channel.addMessage(message);

        user.persist();
        return user;
    }

}
