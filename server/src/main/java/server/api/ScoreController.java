package server.api;


import commons.Score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ScoreRepository;

import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api/score")

public class ScoreController {
    private final Random random;
    private final ScoreRepository repo;

    public ScoreController(Random random, ScoreRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = "get")
    public List<Score> getAll() {
        return repo.findAll();
    }


    @PostMapping(path = "post")
    public ResponseEntity<Score> add(@RequestBody Score score) {

        if (isNullOrEmpty(score.getUserName() )) {
            return ResponseEntity.badRequest().build();
        }

        Score saved = repo.save(score);
        return ResponseEntity.ok(saved);
    }



    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @GetMapping(path = "get/rnd")
    public ResponseEntity<Score> getRandom() {
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(repo.getById((long) idx));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Score> deleteById(@PathVariable("id") long id ){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("put/{id}")
    public Score updateById(@RequestBody Score newScore, @PathVariable("id") long id) {

        return repo.findById(id)
                .map(score -> {
                    score.setScore (newScore.getScore());
                    score.setUserName(newScore.getUserName());
                    score.setUserId(newScore.getUserId());
                    return repo.save(score);
                })
                .orElseGet(() -> {
                    newScore.setUserId(id);
                    return repo.save(newScore);
                });
    }



}
