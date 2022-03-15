package server.api;

import commons.MostEnergyQuestion;
import commons.Activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me/question")
public class MEQuestionController {

    @Autowired
    private EntryController ctrl;

    @GetMapping(path = "")
    public MostEnergyQuestion getAll() {
        Activity firstOption = ctrl.getRandom().getBody();
        Activity secondOption = firstOption;
        Activity thirdOption = firstOption;

        while(secondOption.getId() == firstOption.getId()){
            secondOption = ctrl.getRandom().getBody();

        }
        while(thirdOption.getId() == firstOption.getId()
                || thirdOption.getId() == secondOption.getId()){
            thirdOption = ctrl.getRandom().getBody();
        }


        Activity answer = firstOption;
        if(answer.getConsumption_in_wh() < secondOption.getConsumption_in_wh()){
            answer = secondOption;
        }

        if(answer.getConsumption_in_wh() < thirdOption.getConsumption_in_wh()){
            answer = thirdOption;
        }


        return new MostEnergyQuestion(firstOption,
                secondOption, thirdOption, answer);
    }
}
