package server.api;

import commons.Activity;
import commons.Game;
import commons.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/multiplayer")
public class MultiplayerController {

    private int counter;

    private Random random;

    @Autowired
    private SimpMessagingTemplate simpMess;

    @Autowired
    private QTypeController qTypeController;

    @Autowired
    private HMQuestionController hmQuestionController;

    @Autowired
    private MEQuestionController meQuestionController;

    @Autowired
    private GuessXController guessXController;

    //when starting a game the lobby should be added here,
    // so ids of all players in each game are here,
    // we'll figure sth out from there

    private Map<Object, Consumer<List<Score>>> listeners = new HashMap<>();

    //all scores containing usernames and ids are stored here
    private List<Score> lobby = new ArrayList<>();

    private List<Game> currentGames = new ArrayList<>();

    public MultiplayerController(Random random){
        this.random = random;
    }

    @MessageMapping("/game")
    @SendTo("/topic/game")
    public Game createGame(@Payload String s){
        System.out.println("_____\n"+s+"\n_____");

        List<Object> questions = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            int type = qTypeController.getRandomType();
            switch (type){
                case 1:
                    questions.add(hmQuestionController.getAll());
                    break;
                case 2:
                    questions.add(guessXController.getQuestion());
                    break;
                case 3:
                    questions.add(meQuestionController.getAll());
                    break;
            }
        }
        Game game = new Game(counter++, questions);
        System.out.println(game);
        currentGames.add(game);
        this.listeners = new HashMap<>();
        this.lobby = new ArrayList<>();
        //game control timer init here
        
        return game;
    }


    @SendTo("/topic/nextQuestion")
    public Integer sendString(Integer gameID){
        return gameID;
    }

    @MessageMapping("/emoji")
    @SendTo("/topic/emoji")
    public Activity sendPath(Activity activity){
        return new Activity(activity.getImagePath(), activity.getTitle(), 1);
    }

    @PostMapping(path = "join")
    public ResponseEntity<List<Score>>
    joinGame(@RequestBody List<Score> scores){
        lobby.addAll(scores);
        System.out.println(lobby.toString());
        listeners.forEach((k, l) -> l.accept(lobby));
        return ResponseEntity.ok(lobby);
    }

    @GetMapping(path = "update")
    public DeferredResult<ResponseEntity<List<Score>>> getLobby(){
        var noContent =
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new
                DeferredResult<ResponseEntity<List<Score>>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, s -> {
            res.setResult(ResponseEntity.ok(lobby));
        });
        res.onCompletion(() -> {
            listeners.remove(key);
        });
        return res;
    }

}


