package Service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Handler extends Thread {

    private MessageReceivedEvent event;
    private JDA jda;
    private String prefix;

    public Handler(MessageReceivedEvent event, JDA jda){
        this.event = event;
        this.jda = jda;
        prefix = "!dadbot";
    }

    public void run(){
        //find out which type of message it is and pass off to correct sub-handler
        //check first for the dad bot prefix and hand off if possible
        if(event.getMessage().getContentRaw().toLowerCase().startsWith(prefix)){
            CommandHandler commandHandler = new CommandHandler(event, jda);
            commandHandler.run();
        }
        //secondly hand off to dadjoke checker
    }

}
