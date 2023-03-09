package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// This class represents the dealer's hand. It extends the Hand class and the key feature is that one of the cards is
// face down and hidden from the user.
public class DealerHand extends Hand {


    public DealerHand(ArrayList<Card> hand) {
        super(hand);

    }

    public String showHand() {
        return super.toString();
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
        return res + ": " + countDealer();
    }
}
