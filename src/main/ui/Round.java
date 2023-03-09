package ui;

import org.json.JSONObject;

// This class represents a blackjack round. It contains data on the users bet
public class Round {
    private double betSize;

    // MODIFIES: this
    // EFFECTS: sets the bet size to the given bet
    public Round(double bet) {
        betSize = bet;
    }

    public void setBetSize(double betSize) {
        this.betSize = betSize;
    }

    public double getBetSize() {
        return betSize;
    }

    // EFFECTS: returns winnings on a regular win.
    public double getWinReg() {
        return betSize;
    }

    // EFFECTS: returns winnings on a blackjack win.
    public double getWinBJ() {
        return betSize * 1.5;
    }

}
