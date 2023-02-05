package ui;

import model.Card;
import model.DealerHand;
import model.Hand;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Hand playerHand;
    private DealerHand dealerHand;
    private ArrayList<Card> deck;

    public Game() {
        playerHand = new Hand(new ArrayList<Card>());
        dealerHand = new DealerHand(new ArrayList<Card>());
    }

    // REQUIRES: deck is not null and must contain `1 element
    public Card dealCard(ArrayList<Card> deck) {
        return deck.remove(0);
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public DealerHand getDealerHand() {
        return dealerHand;
    }

    public void setup() {
        deck = makeDeck();
        for (int i = 0; i < 2; i++) {
            playerHand.addCard(dealCard(deck));
            dealerHand.addCard(dealCard(deck));
        }
    }



    public void newGame() {
        playerHand.clearHand();
        dealerHand.clearHand();
    }


    public static ArrayList<Card> makeDeck() {
        ArrayList<Card> res = new ArrayList<Card>();
        for (String v : Card.values) {
            for (String s : Card.suits) {
                res.add(new Card(v, s));
            }
        }
        Collections.shuffle(res);
        return res;
    }


}
