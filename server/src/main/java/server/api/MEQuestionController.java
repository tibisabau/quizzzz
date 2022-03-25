package server.api;

import commons.MostEnergyQuestion;
import commons.Activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/me/question")
public class MEQuestionController {

    @Autowired
    private EntryController ctrl;

    /**
     * server gets a "Which activity takes the most energy" question
     * @return MostEnergyQuestion
     */
    @GetMapping(path = "")
    public MostEnergyQuestion getAll() {
        List<Activity> activityList = ctrl.getAll();
        Collections.shuffle(activityList);
        Activity firstOption = activityList.get(0);
        Activity secondOption = activityList.get(1);
        Activity thirdOption = activityList.get(2);

        Activity answer = firstOption;
        if(answer.getConsumptionInWh() < secondOption.getConsumptionInWh()){
            answer = secondOption;
        }

        if(answer.getConsumptionInWh() < thirdOption.getConsumptionInWh()){
            answer = thirdOption;
        }

        return new MostEnergyQuestion(firstOption,
                secondOption, thirdOption, answer);
    }
}
