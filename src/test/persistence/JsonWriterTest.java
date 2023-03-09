package persistence;

import model.Card;
import model.DealerHand;
import model.Hand;
import org.junit.jupiter.api.Test;
import ui.Game;
import ui.Round;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Game g = new Game();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNewGame() {
        try {
            Game g = new Game();
            g.setBalance(250);
            ArrayList<Card> h = new ArrayList<>();
            h.add(new Card("A", "C"));
            h.add(new Card("6", "H"));
            g.setPlayerHand(new Hand(h));
            ArrayList<Card> d = new ArrayList<>();
            d.add(new Card("4", "S"));
            d.add(new Card("J", "D"));
            g.setDealerHand(new DealerHand(d));
            g.setRound(new Round(100));
            JsonWriter writer = new JsonWriter("./data/testWriterNewGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNewGame.json");
            g = reader.read();
            assertEquals(250, g.getBalance());
            checkCard("A", "C", g.getPlayerHand().getHand().get(0));
            checkCard("6", "H", g.getPlayerHand().getHand().get(1));
            assertEquals(2, g.getDealerHand().length());
            assertEquals(100, g.getRound().getBetSize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
