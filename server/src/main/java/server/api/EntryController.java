package server.api;


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


import commons.Entry1;
import server.database.EntryRepository;




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
    public List<Entry1> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = "get/{id}")
    public ResponseEntity<Entry1> getById (@PathVariable("id") long id ){
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }
    @GetMapping(path = "get/rnd")
    public ResponseEntity<Entry1> getRandom() {
        var idx = random.nextInt((int) repo.count()) + 1;
        return ResponseEntity.ok(repo.findById((long) idx).get());

    }


    @PostMapping(path = "post")
    public ResponseEntity<Entry1> add(@RequestBody Entry1 entry1) {

        if (isNullOrEmpty(entry1.title) || isNullOrEmpty(entry1.source) || isNullOrEmpty(entry1.image_path)) {
            return ResponseEntity.badRequest().build();
        }
        Entry1 saved = repo.save(entry1);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Entry1> deleteById(@PathVariable("id") long id ){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("delete/all")
    public void deleteAll(){
        repo.deleteAll();
    }
    @PutMapping("put/{id}")
    public Entry1 updateById(@RequestBody Entry1 newEntry1, @PathVariable("id") long id) {

        return repo.findById(id)
                .map(entry1 -> {
                    entry1.title = newEntry1.title;
                    entry1.consumption_in_wh = newEntry1.consumption_in_wh;
                    entry1.source = newEntry1.source;
                    entry1.image_path = newEntry1.image_path;
                    entry1.id = newEntry1.id;
                    return repo.save(entry1);
                })
                .orElseGet(() -> {
                    newEntry1.id = id;
                    return repo.save(newEntry1);
                });
    }

}
