package server.api;

import commons.Entry1;
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
            Entry1 correctOption = ctrl.getRandom().getBody();
            Entry1 firstOption = correctOption;
            double random = Math.random() * (2) + 0.1;
            double random2 = Math.random() * (2) + 0.1;
            Entry1 secondOption = new Entry1(correctOption.getImage_path(), correctOption.getTitle()
            , (int)(correctOption.getConsumption_in_wh() * random) / 10 * 10, correctOption.getSource());
            Entry1 thirdOption = new Entry1(correctOption.getImage_path(), correctOption.getTitle()
                    , (int)(correctOption.getConsumption_in_wh() * random2) / 10 * 10, correctOption.getSource());
            List<Entry1> options = new ArrayList<>();

            options.add(firstOption);
            options.add(secondOption);
            options.add(thirdOption);

            Collections.shuffle(options);
            return new HowMuchQuestion(options.get(0), options.get(1), options.get(2), correctOption);
        }
}
