package server.api;
import commons.MostEnergyQuestion;
import commons.Entry1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.EntryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/me/question")
public class MEQuestionController {

    private final Random random;
    private final EntryRepository repo;

    public MEQuestionController(Random random, EntryRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = "")
    public MostEnergyQuestion getAll() {
        List<Entry1> activities = repo.findAll();
        Collections.shuffle(activities);
        return new MostEnergyQuestion(activities.get(0), activities.get(1), activities.get(2));
    }
}
