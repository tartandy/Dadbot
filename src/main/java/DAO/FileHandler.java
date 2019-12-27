package DAO;

import java.io.*;
import java.util.HashMap;

public class FileHandler {

    private static HashMap<String,Integer> getCounter() {
        String fn = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "dadbotScores.txt";
        HashMap<String, Integer> counter = new HashMap<>();
        String line;
        //load file into BR
        try {
            File file = new File(fn);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                String[] temp;
                temp = line.split(";");
                counter.put(temp[0], Integer.parseInt(temp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static HashMap<String,Integer> loadCounter() {
        synchronized (FileHandler.class){
            String fn = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "dadbotScores.txt";
            HashMap<String, Integer> counter = new HashMap<>();
            String line;
            //load file into BR
            try {
                File file = new File(fn);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    String[] temp;
                    temp = line.split(";");
                    counter.put(temp[0], Integer.parseInt(temp[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return counter;
        }
    }

     private static void storeCounter(HashMap<String,Integer> counter) {
        //creates copy to avoid future changes in referenced object
        //format input into string so it can be stored
        StringBuilder output = new StringBuilder();
        for(String k : counter.keySet()){
            output.append(k).append(";").append(counter.get(k)).append("\n");
        }
        String fn = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "dadbotScores.txt";
        File file = new File(fn);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //write to file, trims final \n
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(output.toString().trim());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addCount(String id){
        synchronized (FileHandler.class){
            //get scoreboard and increment score for that user
            HashMap<String, Integer> counter = getCounter();
            int newValue = counter.getOrDefault(id, 0);
            counter.put(id, newValue);
            //store scoreboard
            storeCounter(counter);
        }
    }

    private static HashMap<String,String> loadDefaultNames() {
        synchronized (FileHandler.class) {
            String fn = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "dadbotNames.txt";
            HashMap<String, String> defaultNames = new HashMap<>();
            String line;
            //load file into BR
            try {
                File file = new File(fn);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    String[] temp;
                    temp = line.split(";");
                    defaultNames.put(temp[0], temp[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defaultNames;
        }
    }

    private static void storeDefaultNames(HashMap<String,String> defaultNames) {
        synchronized (FileHandler.class){
            //creates copy to avoid future changes in referenced object
            //format input into string so it can be stored
            StringBuilder output = new StringBuilder();
            for(String k : defaultNames.keySet()){
                output.append(k).append(";").append(defaultNames.get(k)).append("\n");
            }
            String fn = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "dadbotNames.txt";
            File file = new File(fn);
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //write to file, trims final \n
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(output.toString().trim());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean setDefaultName(String id, String name) {
        try{
            //get default name list
            HashMap<String, String> defaultNames = loadDefaultNames();
            //put name into data structure
            defaultNames.put(id, name);
            //store back in file
            storeDefaultNames(defaultNames);
        } catch(Exception e){
            return false;
        }
        return true;
    }

    public static String getDefaultName(String id) {
        return loadDefaultNames().getOrDefault(id, null);
    }

    public static String getToken() {
            String fn = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "token.txt";
            String line;
            //load file into BR
            try {
                File file = new File(fn);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }
}
