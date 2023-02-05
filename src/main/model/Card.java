package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Card {

    private String val;
    private String suit;
    public static final ArrayList<> faces = new ArrayList<String>(Arrays.asList("J", "Q", "K"));
    public static final ArrayList<> values = new ArrayList<String>(Arrays.asList(
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"));
    public static final ArrayList<String> suits = new ArrayList<String>(Arrays.asList("C", "S", "D", "H"));

    // REQUIRES: val must be 0 - 10, or "J", "Q", "K" or "A"
    //           suit must be either "C", "S", "H", "D"
    // MODIFIES: this
    // EFFECTS: Constructs card object, assigning a value and suit to the card
    public Card(String val, String suit) {
        this.val = val;
        this.suit = suit;
    }

    public String getVal() {
        return val;
    }

    // EFFECTS: returns the corresponding numerical value of the card
    public int getValue() {
        if (faces.contains(val)) {
            return 10;
        } else if (val.equals("A")) {
            return 11;
        } else {
            return Integer.parseInt(val);
        }
    }

    // EFFECTS: returns the suit
    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return val + suit;
    }
}