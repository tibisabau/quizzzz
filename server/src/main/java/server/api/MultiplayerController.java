package server.api;

import commons.Score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/multiplayer")
public class MultiplayerController {


    Random random;

    //when starting a game the lobby should be added here, so ids of all players in each game are here, we'll figure sth out from there
    List<List<Score>> games = new ArrayList<>();

    private Map<Object, Consumer<List<Score>>> listeners = new HashMap<>();

    //all scores containing usernames and ids are stored here
    private List<Score> lobby = new ArrayList<>();


    public MultiplayerController(Random random){
        this.random = random;
    }

    @PostMapping(path = "join")
    public ResponseEntity<List<Score>> joinGame(@RequestBody List<Score> scores){
        lobby.addAll(scores);
        System.out.println(lobby.toString());
        listeners.forEach((k, l) -> l.accept(lobby));
        return ResponseEntity.ok(lobby);
    }

    @GetMapping(path = "update")
    public DeferredResult<ResponseEntity<List<Score>>> getLobby(){
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<List<Score>>>(5000L, noContent);

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


