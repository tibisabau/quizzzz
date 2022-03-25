package server.api;


import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Random;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import commons.Activity;

import org.springframework.web.multipart.MultipartFile;
import server.database.EntryRepository;

import javax.imageio.ImageIO;


@RestController
@RequestMapping("/api/entry")
public class EntryController {

    private final Random random;

    private final EntryRepository repo;

    private List<Activity> list;

    /**
     * constructor for the activity controller
     * @param random
     * @param repo
     */
    public EntryController(Random random, EntryRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    /**
     * get request for all activities in the db
     * @return list of activities
     */
    @GetMapping(path = "get")
    public List<Activity> getAll() {
        return repo.findAll();
    }

    /**
     * get request for all activities in the json file
     * @return list of activities
     */
    @GetMapping(path = "get/json")
    public List<Activity> getJson() {
        return list;
    }

    /**
     * get request for an activity by id
     * @param id
     * @return an activity by id
     */
    @GetMapping(path = "get/{id}")
    public ResponseEntity<Activity> getById (@PathVariable("id") long id ){
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * get request for an activity by a random id
     * @return an activity by a random id
     */
    @GetMapping(path = "get/rnd")
    public ResponseEntity<Activity> getRandom() {
        var idx = random.nextInt((int) repo.count()) + 1;
        return ResponseEntity.ok(repo.findById((long) idx).get());

    }

    /**
     * saves the list of activities in a list on the server
     * @param list
     */
    public void setJsonList(List<Activity> list) {
        this.list = list;
    }


    /**
     * posts an activity in the db
     * @param activity
     * @return a response entity
     */
    @PostMapping(path = "post")
    public ResponseEntity<Activity> add(@RequestBody Activity activity) {
        if (isNullOrEmpty(activity.title) ||
                isNullOrEmpty(activity.imagePath)) {
            return ResponseEntity.badRequest().build();
        }
        Activity saved = repo.save(activity);
        return ResponseEntity.ok(saved);
    }

    /**
     * checks for empty fields
     * @param s
     * @return boolean
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * deletes an activity from the db
     * @param id
     * @return a response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Activity> deleteById(@PathVariable("id") long id ){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * deletes all activities from the db
     */
    @DeleteMapping("delete/all")
    public void deleteAll(){
        repo.deleteAll();
    }

    /**
     * put request for an activity
     * @param newActivity
     * @param id
     * @return a new activity
     */
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

    /**
     * encodes an image as param
     * @param path
     * @return a response entity
     */
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


    /**
     * creates the image on the server
     * @param multipartFile
     * @return the image path
     * @throws IOException
     */
    @PostMapping("/save")
    public String save(@RequestParam("File") MultipartFile multipartFile)
            throws IOException {

        System.out.println(multipartFile);
        String fileName = StringUtils.cleanPath(
                multipartFile.getOriginalFilename());

        String uploadDir = "./activity_bank/79";

        saveFile(uploadDir, fileName, multipartFile);

        return "79/" + fileName;
    }

    /**
     * creates the directory for all new images
     * @param uploadDir
     * @param fileName
     * @param multipartFile
     * @throws IOException
     */
    public void saveFile(String uploadDir, String fileName,
                         MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.
                getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.
                    REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " +
                    fileName, ioe);
        }
    }

}
