package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card c1 = new Card("K", "C");
    private Card c2 = new Card("A", "D");
    private Card c3 = new Card("9", "H");

    @Test
    public void getValTest() {
        assertEquals(10, c1.getValue());
        assertEquals(11, c2.getValue());
        assertEquals(9, c3.getValue());
    }

}