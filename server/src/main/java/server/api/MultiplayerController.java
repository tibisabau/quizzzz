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
    Map<Long, String> players = new HashMap<>();
    Map<Integer, Map<Long, String>> games = new HashMap();
    private Map<Object, Consumer<List<Score>>> listeners = new HashMap<>();
    private long noPlayers = 0;
    private List<Score> lobby = new ArrayList<>();

    int gameID = 0;

    public MultiplayerController(Random random){
        this.random = random;
    }

    @PostMapping(path = "join")
    public ResponseEntity<List<Score>> joinGame(@RequestBody List<Score> scores){
        lobby.addAll(scores);
        System.out.println(lobby.toString());
        noPlayers++;
        System.out.println(players.toString());
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


