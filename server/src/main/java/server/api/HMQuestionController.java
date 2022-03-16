package server.api;

import commons.Activity;
import commons.HowMuchQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
    @RequestMapping("/api/hm/question")
    public class HMQuestionController {

        @Autowired
        private EntryController ctrl;

        @GetMapping(path = "")
        public HowMuchQuestion getAll() {
            Activity correctOption = ctrl.getRandom().getBody();
            Activity firstOption = correctOption;
            double random = Math.random() * (2) + 0.1;
            double random2 = Math.random() * (2) + 0.1;
            Activity secondOption = new Activity(correctOption.getImagePath(),
                    correctOption.getTitle(),
                    (int)(correctOption.getConsumptionInWh() * random) / 10 * 10);
            Activity thirdOption = new Activity(correctOption.getImagePath(),
                    correctOption.getTitle(),
                    (int)(correctOption.getConsumptionInWh() * random2) / 10 * 10);
            List<Activity> options = new ArrayList<>();

            options.add(firstOption);
            options.add(secondOption);
            options.add(thirdOption);

            Collections.shuffle(options);
            return new HowMuchQuestion(options.get(0), options.get(1),
                    options.get(2), correctOption);
        }
}
