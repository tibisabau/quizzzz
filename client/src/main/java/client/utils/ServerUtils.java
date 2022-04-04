/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import commons.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.core.GenericType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * The type Server utils.
 */
public class ServerUtils {

    private static String SERVER;

    private static ExecutorService exec =
            Executors.newSingleThreadExecutor();

    private static StompSession session;


    /**
     * set the websocket url
     * @param server
     */
    public void setSession(String server) {
        SERVER = "http://" + server + "/";
        this.session = connect("ws://" + server + "/websocket");
    }


    /**
     * Gets quotes the hard way.
     *
     * @throws IOException the io exception
     */
    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Gets quotes.
     *
     * @return the quotes
     */
    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {});
    }

    /**
     * Add quote quote.
     *
     * @param quote the quote
     * @return the quote
     */
    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }

    /**
     * Get score list.
     *
     * @return the list
     */
    public List<Score> getScore(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Score>>() {});
    }



    /**
     * Add score score.
     *
     * @param score the score
     * @return the score
     */
    public Score addScore(Score score){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score/post")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(score, APPLICATION_JSON), Score.class);
    }

    /**
     * Update scores on the data base
     *
     * @param score the score
     * @return the score
     */
    public Score updateScore(Score score){
        Long id = score.getUserId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score/put/" + id.toString())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(score, APPLICATION_JSON), Score.class);
    }

    /**
     * Adds an Activity to the database
     *
     * @param activity the activity
     * @return a new Activity
     */
    public Activity addEntry(Activity activity){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/post")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON),
                        Activity.class);
    }

    /**
     * updates an activity
     *
     * @param activity the activity
     * @return the updated activity
     */
    public Activity updateEntry(Activity activity){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/put/" + activity.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(activity, APPLICATION_JSON),
                        Activity.class);
    }

    /**
     * client gets all activities from the db
     *
     * @return a list of activities
     */
    public List<Activity> getActivities(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/get")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    /**
     * client accepts the list of activities from
     * the server
     *
     * @return a list of activities
     */
    public List<Activity> getJson(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/get/json")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    /**
     * client accepts the delete activity
     * request
     *
     * @param id the id
     * @return the deleted activity
     */
    public Activity deleteActivity(long id){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/delete/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(Activity.class);
    }

    /**
     * client accepts delete request
     * for the db of activities
     *
     * @return the remaining activities
     */
    public List<Activity> deleteAll(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/delete/all")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(new GenericType<List<Activity>>() {});
    }

    /**
     * generates a "Which activity takes more energy" question
     *
     * @return a "Which activity takes more energy" question
     */
    public MostEnergyQuestion getMEQuestion(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/me/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(MostEnergyQuestion.class);
    }

    /**
     * Get a sorted list of scores from the database.
     *
     * @return a sorted list of type Score
     */
    public List<Score> getTopScores(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score/get/top")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Score>>() {});
    }

    /**
     * generates a "How much energy does it take" question
     *
     * @return a "How much energy does it take" question
     */
    public HowMuchQuestion getHMQuestion(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/hm/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(HowMuchQuestion.class);
    }

    /**
     * generates a way to randomise between question types
     *
     * @return a random integer
     */
    public Integer getQuestionType(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/type/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Integer.class);
    }

    /**
     * gets the encoded image from the server
     *
     * @param path the path
     * @return an encoded image
     */
    public String getImage(String path) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/photo/get")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(path, APPLICATION_JSON), String.class);
    }

    /**
     * gets the encoded image for the emojis
     * @param path
     * @return an encoded image
     */
    public String getEmoji(String path) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/emoji/get")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(path, APPLICATION_JSON), String.class);
    }

    /**
     * generates a "Guess The Amount Of Energy" question
     *
     * @return a "Guess The Amount Of Energy" question
     */
    public GuessXQuestion getGXQuestion(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/gx/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(GuessXQuestion.class);
    }

    /**
     * Join an online waiting room via long polling
     * @param score
     */
    public void joinGame(List<Score> score){
                ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/multiplayer/join")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(score, APPLICATION_JSON),
                        new GenericType<List<Score>>() {});
    }

    /**
     * Quit the waiting room and inform the server
     * @param score
     */
    public void quitGame(List<Score> score){
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/multiplayer/quit")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(score, APPLICATION_JSON),
                        new GenericType<List<Score>>() {});
    }

    /**
     * Register for updates for long polling
     * @param consumer
     */
    public void registerForUpdates(Consumer<List<Score>> consumer){
        exec.submit(() -> {
            while(!Thread.interrupted()){
                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("/api/multiplayer/update")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);
                if(res.getStatus() == 204){
                    continue;
                }
                var s = res.readEntity(new GenericType<List<Score>>() {});
                consumer.accept(s);
            }
        });
    }

    /**
     * Connect to the server
     * @param url
     * @return A stompSession
     */
    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url,
                    new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException();
        }
        throw new IllegalStateException();
    }

    /**
     * Register for updates in a certain path
     * @param dest
     * @param type
     * @param consumer
     * @param <T>
     */
    public <T> void registerForMessages(String dest,
                                        Class<T> type, Consumer<T> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Send an object to the server
     * @param dest
     * @param s The object that will be sent
     */
    public void send(String dest, Object s){
        session.send(dest, s);
    }

    /**
     * sents a game object to the server
     * @param dest destination to send to
     * @param joker joker to send
     */
    public void sendGame(String dest, Joker joker) { session.send(dest, joker);}

    /**
     * shutdown the long polling
     */
    public void stop(){
        exec.shutdownNow();
        exec = null;
        exec = Executors.newSingleThreadExecutor();
    }

    /**
     * Get instead of question instead of question.
     *
     * @return the instead of question
     */
    public InsteadOfQuestion getInsteadOfQuestion(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/instead/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(InsteadOfQuestion.class);
    }

    /**
     * client sends image to server
     *
     * @param image the image
     * @param url   the url
     * @return the image path
     */
    public String updateImage(File image, String url) {
        try {
            HttpPost save = new HttpPost(SERVER + url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("File", new FileInputStream(image)
                    , ContentType.MULTIPART_FORM_DATA, image.getName());
            org.apache.http.HttpEntity multipart = builder.build();
            save.setEntity(multipart);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(save);
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            httpClient.close();
            response.close();
            if(response.getStatusLine().getStatusCode() == 200) {
                return "79/" + image.getName();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    /**
     * send image from client to server
     *
     * @param image the image
     * @return the image path
     */
    public String addImage(File image) {
        String url = "/api/entry/save";
        String response = updateImage(image, url);
        return response;
    }

    /**
     * Close the websocket connection
     */
    public void wsDisconnect(){
        session.disconnect();
        session = null;
        SERVER = null;
    }
}
