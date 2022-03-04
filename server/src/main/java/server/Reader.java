package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import commons.EntryRead;
import commons.Entry;
import server.api.EntryController;
import server.database.EntryRepository;


public class Reader {
    public static void main(String[] args){

        read("activities.json");

    }
    public static void read(String path){
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));

            // convert JSON array to list of users
            List<EntryRead> entries = new Gson().fromJson(reader, new TypeToken<List<EntryRead>>() {}.getType());

            // close reader
            reader.close();

            for(int i = 0; i < entries.size(); i++){
                Random random = new Random();
                Entry entry = convert(entries.get(i),(long) i);
                EntryController entryController = new EntryController(random, );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Entry convert(EntryRead entryRead, long id){
        String title = entryRead.title;
        String image_path = entryRead.image_path;
        String source = entryRead.source;
        int consumption_in_wh = entryRead.consumption_in_wh;
        Entry entry = new Entry(id, image_path,title,consumption_in_wh,source);
        return entry;
    }

}
