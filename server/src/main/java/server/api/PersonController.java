package server.api;


import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


import commons.Person;
import server.database.PersonRepository;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final Random random;
    private final PersonRepository repo;

    public PersonController(Random random, PersonRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = "get")
    public List<Person> getAll() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    @PostMapping(path = "post")
    public ResponseEntity<Person> add(@RequestBody Person person) {

        if (isNullOrEmpty(person.firstName) || isNullOrEmpty(person.lastName)) {
            return ResponseEntity.badRequest().build();
        }

        Person saved = repo.save(person);
        return ResponseEntity.ok(saved);
    }



    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @GetMapping(path = "get/rnd")
    public ResponseEntity<Person> getRandom() {
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(repo.getById((long) idx));
    }


   /** @DeleteMapping("delete/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable("id") long id ){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }**/

    @PutMapping("put/{id}")
    public Person updateById(@RequestBody Person newPerson, @PathVariable("id") long id) {

        return repo.findById(id)
                .map(person -> {
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    return repo.save(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return repo.save(newPerson);
                });
    }




}
