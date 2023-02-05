package model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private boolean hasAce;

    public Hand(ArrayList<Card> hand) {
        this.hand = hand;
        hasAce = false;
        for (Card c : hand) {
            if (c.getVal().equals("A")) {
                hasAce = true;
            }
        }
    }

    public void clearHand() {
        hand = new ArrayList<Card>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }


    public int countHand() {
        int res = 0;
        for (Card c : hand) {
            res += c.getValue();
        }
        if (res > 21) {
            if (hasAce) {
                res -=  10;
            }
        }
        return res;
    }


    public boolean getHasAce() {
        return hasAce;
    }

    public boolean checkBust() {
        return countHand() > 21;
    }

    public void addCard(Card c) {
        hand.add(c);
        if ((c.getVal()).equals("A")) {
            hasAce = true;
        }
    }

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
