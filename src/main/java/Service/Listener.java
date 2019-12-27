package Service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Listener extends ListenerAdapter {

    private ArrayList<String> prefixes;
    private JDA jda;
    private String botID;
    private HashMap<String, Integer> counter;
    String command;

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    //receives input and determines the message type, if any,
    // before handing off to appropriate handler thread to process in background
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        //ignore PMs and messages from other bots
        if(event.getAuthor().isBot()) return;
        if(!msg.isFromGuild()) return;
        //create new event handler thread to process message and respond
        Handler handler = new Handler(event, jda);
        handler.start();
    }
}
