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


import commons.Entry;
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
    public List<Entry> getAll() {
        return repo.findAll();
    }

    @PostMapping(path = "post")
    public ResponseEntity<Entry> add(@RequestBody Entry entry) {

        if (isNullOrEmpty(entry.title) /**|| entry.image == null**/) {
            return ResponseEntity.badRequest().build();
        }
        Entry saved = repo.save(entry);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Entry> deleteById(@PathVariable("id") long id ){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("put/{id}")
    public Entry updateById(@RequestBody Entry newEntry, @PathVariable("id") long id) {

        return repo.findById(id)
                .map(entry -> {
                    entry.title = newEntry.title;
                    entry.consumption_in_wh = newEntry.consumption_in_wh;
                   // entry.image = newEntry.image;
                    entry.id = newEntry.id;
                    return repo.save(entry);
                })
                .orElseGet(() -> {
                    newEntry.id = id;
                    return repo.save(newEntry);
                });
    }

}
