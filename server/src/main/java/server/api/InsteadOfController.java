package server.api;

import commons.Activity;
import commons.InsteadOfQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/instead/question")
public class InsteadOfController {

    @Autowired
    private EntryController ctrl;


    /**
     * Gets a new "Instead of" question.
     *
     * @return InsteadOfQuestion.
     */
    @GetMapping(path = "")
    public InsteadOfQuestion getAll() {
        List<Activity> activityList = ctrl.getAll();
        Collections.shuffle(activityList);
        List<Activity> listOfAnswers = activityList.subList(0,4);
        Activity minimum = listOfAnswers.get(0);
        for (Activity listOfAnswer : listOfAnswers) {
            if (listOfAnswer.getConsumptionInWh()
                    < minimum.getConsumptionInWh()) {
                minimum = listOfAnswer;
            }
        }
        Activity correctOption = minimum;
        listOfAnswers.remove(correctOption);
        minimum = listOfAnswers.get(0);
        for (Activity listOfAnswer : listOfAnswers) {
            if (listOfAnswer.getConsumptionInWh()
                    < minimum.getConsumptionInWh()) {
                minimum = listOfAnswer;
            }
        }
        Activity promptedOption= minimum;
        listOfAnswers.remove(promptedOption);
        Activity firstOption = listOfAnswers.get(0);
        Activity secondOption = listOfAnswers.get(1);
        return new InsteadOfQuestion(promptedOption,
                correctOption, firstOption, secondOption);

    }


}
