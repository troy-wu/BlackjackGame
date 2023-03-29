package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Card;
import model.DealerHand;
import model.Hand;
import org.json.*;
import ui.Game;
import ui.Round;

// Represents a reader that reads game from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public double read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private double parseWorkRoom(JSONObject jsonObject) {
        // Game g = new Game();
        // addHands(g, jsonObject);
        // addDeck(g, jsonObject);
        // addDeck(g, jsonObject);
//        g.setBalance(jsonObject.getDouble("balance"));
//        g.setRound(new Round(jsonObject.getDouble("round")));
        return jsonObject.getDouble("balance");
    }

    // MODIFIES: g
    // EFFECTS: adds deck to JSON file
    private void addDeck(Game g, JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("deck");
        ArrayList<Card> res = new ArrayList<>();
        for (Object j : jsonArray) {
            res.add(jsonToCard((JSONObject) j));
        }
        g.setDeck(res);
    }

    // MODIFIES: g
    // EFFECTS: adds dealer and player hand to game
    private void addHands(Game g, JSONObject json) {
        JSONObject p = json.getJSONObject("playerHand");
        JSONArray jsonArray1 = p.getJSONArray("hand");
        ArrayList<Card> res1 = new ArrayList<>();
        for (Object j : jsonArray1) {
            res1.add(jsonToCard((JSONObject) j));
        }
        g.setPlayerHand(new Hand(res1));

        JSONObject d = json.getJSONObject("dealerHand");
        JSONArray jsonArray2 = d.getJSONArray("hand");
        ArrayList<Card> res2 = new ArrayList<>();
        for (Object j : jsonArray2) {
            res2.add(jsonToCard((JSONObject) j));
        }
        g.setDealerHand(new DealerHand(res2));
    }

    // EFFECTS: converts json representation of a card to a Card object
    private Card jsonToCard(JSONObject c) {
        Card card = new Card(c.getString("val"), c.getString("suit"));
        return card;
    }

}
