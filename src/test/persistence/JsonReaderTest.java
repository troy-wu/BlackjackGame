package persistence;

import model.Card;
import org.junit.jupiter.api.Test;
import ui.Game;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderPostRound() {
        JsonReader reader = new JsonReader("./data/testReaderPostRound.json");
        try {
            Game g = reader.read();
            assertEquals(900, g.getBalance());
            assertEquals(100, g.getRound().getBetSize());
            checkCard("A", "C", g.getDeck().get(0));
            assertEquals(47, g.getDeck().size());
            assertEquals(0, g.getPlayerHand().length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMidRound() {
        JsonReader reader = new JsonReader("./data/testReaderMidRound.json");
        try {
            Game g = reader.read();
            assertEquals(1000, g.getBalance());
            assertEquals(300, g.getRound().getBetSize());
            checkCard("J", "S", g.getPlayerHand().getHand().get(0));
            checkCard("3", "H", g.getPlayerHand().getHand().get(1));
            assertEquals(2, g.getDealerHand().length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
