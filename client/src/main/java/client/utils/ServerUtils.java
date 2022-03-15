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
import java.net.URL;
import java.util.List;

import commons.*;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

/**
 * The type Server utils.
 */
public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

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

    public Entry1 addEntry(Entry1 entry1){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/entry/post")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(entry1, APPLICATION_JSON), Entry1.class);
    }

    public MostEnergyQuestion getMEQuestion(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/me/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(MostEnergyQuestion.class);
    }

    /**
     * Get a sorted list of scores from the database.
     * @returns a sorted list of type Score
     */
    public List<Score> getTopScores(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score/get/top")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Score>>() {});
    }            

    public HowMuchQuestion getHMQuestion(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/hm/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(HowMuchQuestion.class);
    }

    public Integer getQuestionType(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/type/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Integer.class);
    }
}