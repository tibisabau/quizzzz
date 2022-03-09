package server.api;
import commons.MostEnergyQuestion;
import commons.Entry1;

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
        Entry1 firstOption = ctrl.getRandom().getBody();
        Entry1 secondOption = firstOption;
        Entry1 thirdOption = firstOption;
        while(secondOption.getId() == firstOption.getId())
            secondOption = ctrl.getRandom().getBody();
        while(thirdOption.getId() == firstOption.getId() || thirdOption.getId() == secondOption.getId())
            thirdOption = ctrl.getRandom().getBody();

        return new MostEnergyQuestion(firstOption, secondOption, thirdOption);
    }
}
