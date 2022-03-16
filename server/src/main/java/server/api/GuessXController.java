package server.api;

import commons.Activity;
import commons.GuessXQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/api/gx/question")
    public class GuessXController {

        @Autowired
        private EntryController ctrl;

        @GetMapping
        public GuessXQuestion getQuestion() {
            Activity correctOption = ctrl.getRandom().getBody();
            return new GuessXQuestion(correctOption);
        }
}
