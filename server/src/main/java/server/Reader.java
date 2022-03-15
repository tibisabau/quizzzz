package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import commons.EntryRead;
import commons.Entry1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import server.api.EntryController;
import server.database.EntryRepository;

@Component
public class Reader implements ApplicationRunner {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private EntryController entryController;

    public Reader() {
    }

    /**
     * Reads the provided JSON file and puts the entries in the database on application startup.
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception{
        if(entryController.getAll().size() != 0){
            return;
        }
        try {

            BufferedReader reader = null;
            boolean assign = false;
            try {
                reader = Files.newBufferedReader(Paths.get("activities.json"));
            } catch (Exception e){
                assign = true;
            } finally {
                if(assign == true){
                    reader = Files.newBufferedReader(Paths.get("repository-template/server/activities.json"));
                }
            }

            // convert JSON array to list of users
            List<EntryRead> entries = new Gson().fromJson(reader, new TypeToken<List<EntryRead>>() {}.getType());

            // close reader
            reader.close();
            entryController.deleteAll();

            for(int i = 0; i < entries.size(); i++){
                Entry1 entry1 = convert(entries.get(i));
                entryController.add(entry1);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Converts entryRead to entry1
     * @param entryRead Takes an entryREad as input
     * @returns an entry1 object converted from the entryRead object
     */
    public static Entry1 convert(EntryRead entryRead){
        String title = entryRead.title;
        String image_path = entryRead.image_path;
        String source = entryRead.source;
        long consumption_in_wh = entryRead.consumption_in_wh;
        Entry1 entry1 = new Entry1(image_path,title,consumption_in_wh,source);
        return entry1;
    }

}