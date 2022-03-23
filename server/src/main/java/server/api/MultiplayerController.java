package server.api;

import commons.Score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/multiplayer")
public class MultiplayerController {

    Random random;
    Map<Long, String> players = new HashMap<>();
    Map<Integer, Map<Long, String>> games = new HashMap();
    private Map<Object, Consumer<Score>> listeners = new HashMap<>();

    int gameID = 0;

    public MultiplayerController(Random random){
        this.random = random;
    }

    @PostMapping(path = "join")
    public ResponseEntity<Score> joinGame(Score score){
        players.put(score.getUserId(), score.getUserName());
        listeners.forEach((k, l) -> l.accept(score));
        return ResponseEntity.ok(score);
    }

    @GetMapping(path = "update")
    public DeferredResult<ResponseEntity<Score>> getLobby(){
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Score>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, s -> {
            res.setResult(ResponseEntity.ok(s));
        });
        res.onCompletion(() -> {
            listeners.remove(key);
        });
        return res;
    }

}


