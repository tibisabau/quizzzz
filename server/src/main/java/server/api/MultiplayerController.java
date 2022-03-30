package server.api;

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

    //when starting a game the lobby should be added here,
    // so ids of all players in each game are here,
    // we'll figure sth out from there

    private Map<Object, Consumer<List<Score>>> listeners = new HashMap<>();

    //all scores containing usernames and ids are stored here
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

    @MessageMapping("/game")
    @SendTo("/topic/game")
    public Game createGame(@Payload Integer s){
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
                case 4:
                    questions.add(insteadOfController.getAll());
                    break;
            }
        }
        Game game = new Game(counter++, questions);
        System.out.println(game);
        currentGames.add(game);
        this.listeners = new HashMap<>();
        this.lobby = new ArrayList<>();
        //game control timer init here
        gameSync(game);
        return game;
    }


    @MessageMapping("/joker")
    @SendTo("/topic/joker")
    public Game timeJoker(Game game){
        return game;
    }

    @PostMapping(path = "join")
    public ResponseEntity<List<Score>>
    joinGame(@RequestBody List<Score> scores){
        lobby.addAll(scores);
        System.out.println(lobby.toString());
        listeners.forEach((k, l) -> l.accept(lobby));
        return ResponseEntity.ok(lobby);
    }


    @GetMapping(path = "getPlayers")
    public List<Score> getScores(){
        List<Score> players = lobby;
        return players;
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
                if (counter>9){
                    timer.cancel();
                    timer.purge();
                    return;
                }
                msg.convertAndSend("/topic/nextQuestion", game.getID());
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


