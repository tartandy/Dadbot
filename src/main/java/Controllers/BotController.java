package Controllers;

import DAO.FileHandler;
import Service.Listener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.net.http.WebSocket;


public class BotController {

    private JDABuilder builder;
    private Listener listener;

    public BotController() {
        //creates the bot and throws a login exception if it cannot connect
        this.builder = new JDABuilder(AccountType.BOT);
        builder.setToken(FileHandler.getToken());
        builder.setActivity(Activity.watching("for !dadbot help"));
        //set the JDA listener
        listener = new Listener();
        builder.addEventListeners(listener);
    }

    public void start(){
        try{
            //build JDA and pass it to listener
            listener.setJda(builder.build());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
