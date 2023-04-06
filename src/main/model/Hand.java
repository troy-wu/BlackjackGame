package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// This class represents a blackjack hand. It consists of Cards from the card class. It has a fields hand, an arraylist
// representing the hand and aceCount, an integer representing the amount of aces in the hand.
public class Hand {
    protected ArrayList<Card> hand;
    protected int aceCount;

    // REQUIRES: hand is not an empty list
    // MODIFIES: this
    // EFFECTS: Creates a new hand, also checks if hand has ace
    public Hand(ArrayList<Card> hand) {
        this.hand = hand;
        aceCount = 0;
        for (Card c : hand) {
            if (c.getVal().equals("A")) {
                aceCount += 1;
                EventLog.getInstance().logEvent(new Event("Ace detected, updated aceCount to " + aceCount));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes hand an empty list
    public void clearHand() {
        hand = new ArrayList<>();
        aceCount = 0;
        EventLog.getInstance().logEvent(new Event("Hand Cleared"));
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    // EFFECTS: returns length of hand
    public int length() {
        return hand.size();
    }


    // EFFECTS: returns the value of the hand.
    public int countHand() {
        int temp = aceCount;
        int res = 0;
        for (Card c : hand) {
            res += c.getValue();
        }
        while (temp > 0 && res > 21) {
            res -= 10;
            temp--;
        }
        EventLog.getInstance().logEvent(new Event("Hand Counted: " + res));
        return res;
    }


    public int getAceCount() {
        return aceCount;
    }

    // MODIFIES: this
    // EFFECTS: adds given card to hand.
    public void addCard(Card c) {
        hand.add(c);
        if ((c.getVal()).equals("A")) {
            aceCount++;
            EventLog.getInstance().logEvent(new Event("Ace detected, updated aceCount to " + aceCount));
        }
        EventLog.getInstance().logEvent(new Event("Card added to hand: " + c));
    }

    // EFFECTS: Constructs a hand in JSON format
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray cards = new JSONArray(hand);
        json.put("hand", cards);
        json.put("aceCount", aceCount);
        return json;
    }

    // EFFECTS: returns a version of a blackjack hand that is more readable to users.
    @Override
    public String toString() {
        String res = "";
        for (Card c : hand) {
            res += c + " ";
        }
        res += ": " + countHand();
        return res;
    }
}
