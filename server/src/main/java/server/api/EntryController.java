package server.api;


import java.util.List;
import java.util.Random;


import commons.Person;
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

        if (isNullOrEmpty(entry.description) || entry.image == null) {
            return ResponseEntity.badRequest().build();
        }
        Entry saved = repo.save(entry);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
