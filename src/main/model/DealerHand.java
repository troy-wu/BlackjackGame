package model;

import java.util.ArrayList;

public class DealerHand extends Hand {


    public DealerHand(ArrayList<Card> hand) {
        super(hand);

    }

    public int countDealer() {
        return countHand() - super.getHand().get(0).getValue();
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < super.getHand().size(); i++) {
            if (i == 0) {
                res =  "XX " + res;
            } else {
                res += super.getHand().get(i) + " ";
            }
        }
        return res += ": " + countDealer();
    }
}
