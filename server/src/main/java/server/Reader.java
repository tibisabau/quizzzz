package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import commons.ActivityParse;
import commons.Activity;
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
     * Reads the provided JSON file and puts the
     * entries in the database on application startup.
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
                    reader = Files.newBufferedReader(Paths.
                            get("repository-template/server/activities.json"));
                }
            }

            // convert JSON array to list of users
            List<ActivityParse> entries = new Gson().fromJson(reader,
                    new TypeToken<List<ActivityParse>>() {}.getType());

            // close reader
            reader.close();
            entryController.deleteAll();

            for(int i = 0; i < entries.size(); i++){
                Activity activity = convert(entries.get(i));
                entryController.add(activity);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Converts activityParse to activity
     * @param activityParse Takes an activityParse object as an input
     * @return an activity object converted from the activityParse object
     */
    public static Activity convert(ActivityParse activityParse){
        String title = activityParse.title;
        String image_path = activityParse.image_path;
        long consumption_in_wh = activityParse.consumption_in_wh;
        Activity activity = new Activity(image_path,title,consumption_in_wh);
        return activity;
    }

}