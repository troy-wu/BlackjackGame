package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Class to test the Card class.
class CardTest {
    private Card c1;
    private Card c2;
    private Card c3;
    @BeforeEach
    public void setup() {
        c1 = new Card("9", "C");
        c2 = new Card("K", "D");
        c3 = new Card("A", "H");
    }


    @Test
    public void getValTest() {
        assertEquals(9, c1.getValue());
        assertEquals(10, c2.getValue());
        assertEquals(11, c3.getValue());
    }

    @Test
    public void getSuitTest() {
        assertEquals("C", c1.getSuit());
        assertEquals("D", c2.getSuit());
        assertEquals("H", c3.getSuit());
    }

    @Test
    public void toStringTest() {
        assertEquals("9C", c1.toString());
        assertEquals("KD", c2.toString());
        assertEquals("AH", c3.toString());
    }

}