package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Class to test the Hand class.
public class HandTest {
    private Hand h1;
    private Hand h2;
    private Hand h3;

    @BeforeEach
    public void setup() {
        Card c1 = new Card("9", "C");
        Card c2 = new Card("K", "D");
        Card c3 = new Card("A", "H");
        ArrayList<Card> a1 = new ArrayList<>();
        a1.add(c1);
        a1.add(c2);
        h1 = new Hand(a1);
        ArrayList<Card> a2 = new ArrayList<>();
        a2.add(c2);
        a2.add(c3);
        h2 = new Hand(a2);
        ArrayList<Card> a3 = new ArrayList<>();
        a3.add(c3);
        a3.add(c1);
        h3 = new Hand(a3);
    }
    public void hasAceTest() {
        assertEquals(0, h1.getAceCount());
        assertEquals(1, h2.getAceCount());
        assertEquals(1, h3.getAceCount());
    }

    @Test
    public void addCardTest() {
        Card c3 = new Card("A", "D");
        assertEquals(0, h1.getAceCount());
        assertEquals(2, h1.getHand().size());
        h1.addCard(c3);
        assertEquals(1, h1.getAceCount());
        assertEquals(3, h1.getHand().size());
        assertEquals(c3, h1.getHand().get(2));
    }

    @Test
    public void clearHandTest() {
        assertEquals(2, h2.getHand().size());
        assertEquals(1, h2.getAceCount());
        h2.clearHand();
        assertEquals(0, h2.getHand().size());
        assertEquals(0, h2.getAceCount());
    }

    @Test
    public void countHandTest() {
        Card c1 = new Card("4", "C");
        Card c2 = new Card("A", "C");
        assertEquals(21, h2.countHand());
        assertEquals(19, h1.countHand());
        assertEquals(20, h3.countHand());
        h2.addCard(c1);
        assertEquals(15, h2.countHand());
        assertEquals(1, h2.getAceCount());
        h2.clearHand();
        h2.addCard(c2);
        h2.addCard(c2);
        assertEquals(2, h2.getAceCount());
        assertEquals(12, h2.countHand());
        h2.addCard(c1);
        h2.addCard(c1);
        assertEquals(20, h2.countHand());
    }

    @Test
    public void toStringTest() {
        String x = "9C KD : 19";
        assertEquals(x, h1.toString());
        String y = "KD AH : 21";
        assertEquals(y, h2.toString());
        String z = "AH 9C : 20";
        assertEquals(z, h3.toString());
    }

}
