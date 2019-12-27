package Helpers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.*;

public class Helper {

    // function to sort hashmap by values
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    //checks if a user is in a guild
    public static boolean isUserInGuild(Guild guild, String key) {
        for(Member member : guild.getMembers()){
            if(member.getUser().getId().equals(key)){
                return true;
            }
        }
        return false;
    }

    //gets a random number within a range
    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
