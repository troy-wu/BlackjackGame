package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

// Class to test the Dealer Hand class.
public class DealerHandTest {
    private DealerHand h1;
    private DealerHand h2;
    private DealerHand h3;

    @BeforeEach
    public void setup() {
        Card c1 = new Card("9", "C");
        Card c2 = new Card("K", "D");
        Card c3 = new Card("A", "H");
        ArrayList<Card> a1 = new ArrayList<>();
        a1.add(c1);
        a1.add(c2);
        h1 = new DealerHand(a1);
        ArrayList<Card> a2 = new ArrayList<>();
        a2.add(c2);
        a2.add(c3);
        h2 = new DealerHand(a2);
        ArrayList<Card> a3 = new ArrayList<>();
        a3.add(c3);
        a3.add(c1);
        h3 = new DealerHand(a3);
    }

    @Test
    public void countDealerTest() {
        assertEquals(10, h1.countDealer());
        assertEquals(11, h2.countDealer());
    }

    @Test
    public void toStringTest() {
        String x = "XX KD : 10";
        assertEquals(x, h1.toString());
        x = "9C KD : 19";
        assertEquals(x, h1.showHand());
        String y = "XX AH : 11";
        assertEquals(y, h2.toString());
        String z = "XX 9C : 9";
        assertEquals(z, h3.toString());
    }
}
