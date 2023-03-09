package persistence;

import model.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCard(String value, String suit, Card c) {
        assertEquals(value, c.getVal());
        assertEquals(suit, c.getSuit());
    }
}
