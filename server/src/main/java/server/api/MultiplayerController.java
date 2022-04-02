package server.api;

import commons.Activity;
import commons.Game;
import commons.Joker;
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
import java.util.Timer;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/multiplayer")
public class MultiplayerController {

    private int counter;

    private Random random;

    private SimpMessagingTemplate msg;

    @Autowired
    private SimpMessagingTemplate simpMess;

    @Autowired
    private QTypeController qTypeController;

    @Autowired
    private HMQuestionController hmQuestionController;

    @Autowired
    private InsteadOfController insteadOfController;

    @Autowired
    private MEQuestionController meQuestionController;

    @Autowired
    private GuessXController guessXController;

    private Map<Object, Consumer<List<Score>>> listeners = new HashMap<>();

    private List<Score> lobby = new ArrayList<>();


    private List<Game> currentGames = new ArrayList<>();

    public MultiplayerController(Random random, SimpMessagingTemplate msg){
        this.random = random;
        this.msg = msg;
    }

    //@MessageMapping("/nextQuestion")
    @SendTo("/topic/nextQuestion")
    public Integer sendString(Integer gameID){
        System.out.println(gameID);
        return gameID;
    }

    /**
     * Create a new game and return it to everyone in the lobby
     * @param s
     * @return game
     */
    @MessageMapping("/game")
    @SendTo("/topic/game")
    public Game createGame(@Payload String s){
        System.out.println("_____\n"+s+"\n_____");

        Game game = new Game(counter++);
        game.setCurrentQuestion(getQuestion());
        System.out.println(game);
        currentGames.add(game);
        this.listeners = new HashMap<>();
        this.lobby = new ArrayList<>();
        //game control timer init here
        gameSync(game);
        return game;
    }


    public Object getQuestion() {
        Object question = new Object();
        int type = qTypeController.getRandomType();
        switch (type){
            case 1:
                question = hmQuestionController.getAll();
                break;
            case 2:
                question = guessXController.getQuestion();
                break;
            case 3:
                question = meQuestionController.getAll();
                break;
            case 4:
                question = insteadOfController.getAll();
                break;
        }
        return question;
    }

    @MessageMapping("/joker")
    @SendTo("/topic/joker")
    public Joker timeJoker(Joker joker){
        System.out.println("joker gebruikt");
        return joker;
    }

    /**
     * Send the emoji to the client
     * @param activity
     * @return emoji
     */
    @MessageMapping("/emoji")
    @SendTo("/topic/emoji")
    public Activity sendPath(Activity activity){
        return new Activity(activity.getImagePath(), activity.getTitle(), 1);
    }

    /**
     * Join the waiting room and return the list of scores
     * @param scores
     * @return List of scores
     */
    @PostMapping(path = "join")
    public ResponseEntity<List<Score>>
    joinGame(@RequestBody List<Score> scores){
        for(int i = 0; i < scores.size(); i++){
            if(!lobby.contains(scores.get(i))){
                lobby.add(scores.get(i));
            }
        }
//        lobby.addAll(scores);
        System.out.println(lobby.toString());
        listeners.forEach((k, l) -> l.accept(lobby));
        return ResponseEntity.ok(lobby);
    }

    @PostMapping(path = "quit")
    public ResponseEntity<List<Score>>
    quitGame(@RequestBody List<Score> scores){
        lobby = scores;
        System.out.println(lobby.toString());
        listeners.forEach((k, l) -> l.accept(lobby));
        return ResponseEntity.ok(lobby);
    }

    /**
     * Accepts a listener for the long polling
     * @return DeferredResult
     */
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

    /**
     * Gets a multiplayer game in sync.
     * @param game
     */
    public void gameSync(Game game){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                counter++;
                if (counter > 19){
                    timer.cancel();
                    timer.purge();
                    return;
                }
                game.setCurrentQuestion(getQuestion());
                msg.convertAndSend("/topic/nextQuestion", game);

            }
        }, 15000, 15000);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                msg.convertAndSend("/topic/betweenScreen", game.getID());
            }
        },12000, 15000);



    }

}


