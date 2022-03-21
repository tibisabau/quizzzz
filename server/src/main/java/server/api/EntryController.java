package server.api;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.List;
import java.util.Random;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import commons.Activity;
import server.database.EntryRepository;

import javax.imageio.ImageIO;


@RestController
@RequestMapping("/api/entry")
public class EntryController {

    private final Random random;

    private final EntryRepository repo;

    public EntryController(Random random, EntryRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = "get")
    public List<Activity> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = "get/{id}")
    public ResponseEntity<Activity> getById (@PathVariable("id") long id ){
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @GetMapping(path = "get/rnd")
    public ResponseEntity<Activity> getRandom() {
        var idx = random.nextInt((int) repo.count()) + 1;
        return ResponseEntity.ok(repo.findById((long) idx).get());

    }


    @PostMapping(path = "post")
    public ResponseEntity<Activity> add(@RequestBody Activity activity) {
        if (isNullOrEmpty(activity.title) ||
                isNullOrEmpty(activity.imagePath)) {
            return ResponseEntity.badRequest().build();
        }
        Activity saved = repo.save(activity);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Activity> deleteById(@PathVariable("id") long id ){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete/all")
    public void deleteAll(){
        repo.deleteAll();
    }

    @PutMapping("put/{id}")
    public Activity updateById(@RequestBody Activity newActivity,
                               @PathVariable("id") long id) {

        return repo.findById(id)
                .map(entry1 -> {
                    entry1.title = newActivity.title;
                    entry1.consumptionInWh = newActivity.consumptionInWh;
                    entry1.imagePath = newActivity.imagePath;
                    entry1.id = newActivity.id;
                    return repo.save(entry1);
                })
                .orElseGet(() -> {
                    newActivity.id = id;
                    return repo.save(newActivity);
                });
    }

    @PostMapping("photo/get")
    public ResponseEntity<String> getImage(@RequestBody String path) {
        try {
            BufferedImage img = ImageIO.read(
                    new File("./activity_bank/" + path));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(img, path.split("\\.")[1], outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedImage = encoder.
                    encodeToString(outputStream.toByteArray());
            return ResponseEntity.ok(encodedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

}
