package server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/type/question")
public class QTypeController {

    @GetMapping(path = "")
    public Integer getRandomType(){
        return new Random().nextInt(3) + 1;
    }
}
