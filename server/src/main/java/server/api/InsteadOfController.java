package server.api;

import commons.Activity;
import commons.InsteadOfQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


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
        Activity promptedOption = activityList.get(
                new Random().nextInt(activityList.size()));
        long min = Long.MAX_VALUE;
        activityList.remove(promptedOption);
        Activity correctOption = promptedOption;

        for (int i = 0; i < activityList.size(); i++) {
            if(Math.abs(promptedOption.getConsumptionInWh() -
                    activityList.get(i).getConsumptionInWh()) < min &&
                    activityList.get(i).getConsumptionInWh() <=
                            promptedOption.getConsumptionInWh()) {
                min = Math.abs(promptedOption.getConsumptionInWh() -
                        activityList.get(i).getConsumptionInWh());
                correctOption = activityList.get(i);
            }
        }

        activityList.remove(correctOption);
        Activity activity1 = correctOption;
        Activity activity2 = correctOption;
        Activity activity3 = correctOption;
        while(promptedOption.getConsumptionInWh() -
                activity1.getConsumptionInWh() == min
                || promptedOption.getConsumptionInWh() -
                activity2.getConsumptionInWh() == min
                || activity1.equals(activity2)) {
            activity1 = activityList.get(new Random().nextInt
                    (activityList.size()));
            activity2 = activityList.get(new Random().nextInt
                    (activityList.size()));
        }
        List<Activity> list = new ArrayList<>();
        System.out.println(activity1);
        System.out.println(activity2);
        System.out.println(activity3);
        list.add(activity1);
        list.add(activity2);
        list.add(activity3);
        Collections.shuffle(list);
        return new InsteadOfQuestion(promptedOption,
                correctOption, list.get(0), list.get(1), list.get(2));
    }

}
