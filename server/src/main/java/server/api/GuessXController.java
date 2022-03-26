package server.api;

import commons.Activity;
import commons.GuessXQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
    @RequestMapping("/api/gx/question")
    public class GuessXController {

        @Autowired
        private EntryController ctrl;

    /**
     * server gets a "Guess how much energy this activity takes" question
     * @return GuessXQuestion
     */
    @GetMapping
        public GuessXQuestion getQuestion() {

            List<Activity> activities = ctrl.getAll();
            Collections.shuffle(activities);
            Activity correctOption = activities.get(0);
            return new GuessXQuestion(correctOption);
        }
}
