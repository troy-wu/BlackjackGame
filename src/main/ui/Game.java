package ui;

import exceptions.InputException;
import model.Card;
import model.DealerHand;
import model.Hand;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// this class represents the UI and logic for blackjack game
public class Game extends JFrame {
    private Hand playerHand;
    private DealerHand dealerHand;
    private ArrayList<Card> deck;
    private Round round;
    private double balance;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/game.json";
    private PlayArea panel;

    // EFFECTS: initializes player hand and dealer hand and creates deck
    public Game() {
        super("BlackJack");
        setVisible(true);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(1200,600);
        panel = new PlayArea();
        add(panel, BorderLayout.NORTH);
        panel.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        balance = 1000;
        deck = makeDeck();
        for (int i = 0; i < 5; i++) {
            deck.addAll(makeDeck());
        }
        deck.addAll(makeDeck());
        playerHand = new Hand(new ArrayList<>());
        dealerHand = new DealerHand(new ArrayList<>());
        revalidate();
        setVisible(true);


    }


    // MODIFIES; this
    // EFFECTS: main function that runs blackjack game and logic.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void main() {
        waitForLoad();
        if (panel.isLoadGame()) {
            panel.setLoadGame(false);
            loadGame();
            round = new Round(askBet());
            setup();
            panel.updateBalance(balance);

        } else {
            round = new Round(askBet());
            setup();
        }
        String keepPlaying = "";
        panel.addText("Good Luck!");
        panel.updateBalance(balance);
        while (!keepPlaying.equals("X")) {
            while (true) {
                if (waitForInput()) {
                    break;
                }
                String decision = panel.getDecision();
                if (decision.equals("SAVE")) {
                    saveGame();
                    return;
                }
                processDecision(decision);
                if (playerHand.countHand() > 21) {
                    playerBust();
                    break;
                } else if (decision.equals("S")) {
                    panel.revealDealer(dealerHand.getHand());
                    panel.addDealerLabel(dealerHand.countHand());
                    int dealerVal = dealerPlay();
                    if (dealerVal > 21) {
                        dealerBust();
                        break;
                    } else if (dealerVal > playerHand.countHand()) {
                        dealerWin();
                        break;
                    } else if (dealerVal < playerHand.countHand()) {
                        playerWin();
                        break;
                    } else {
                        panel.addText("Push! Your bet was returned");
                        panel.updateBalance(balance);
                        break;
                    }
                }
            }
            keepPlaying = roundEnd();
            round = new Round(askBet());
            setup();
        }
    }

    private boolean waitForLoad() {
        while (panel.isLoadGame() == null) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    private boolean waitForInput() {
        while (true) {
            if (panel.isPressed()) {
                panel.setPressed(false);
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                return true;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when dealer busts and adds win to balance.
    public void dealerBust() {
        System.out.println("Dealer Bust!");
        double win = round.getWinReg();
        System.out.println("You win: " + win);
        panel.addText("You Won! Dealer Bust! You win: " + win);
        balance += win;
        printBal();
        panel.updateBalance(balance);
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when player busts and deducts bet from balance.
    public void playerBust() {
        panel.addText("You Lost! BUSTED!");
        balance -= round.getBetSize();
        panel.updateBalance(balance);
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when dealer wins and deducts bet from balance.
    public void dealerWin() {
        panel.addText("You Lost! Dealer had a higher hand.");
        balance -= round.getBetSize();
        panel.updateBalance(balance);
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when player wins and adds win from balance.
    public void playerWin() {
        double win = round.getWinReg();
        panel.addText("You Won! You had a higher hand. You win: " + win);
        balance += win;
        panel.updateBalance(balance);
    }

    // MODIFIES: this
    // EFFECTS: clears hands and returns the user input whether or not the user wants to keep playing.
    public String roundEnd() {
        panel.updateBalance(balance);
        panel.setBetSize(0);
        panel.setSavePressed(false);
        while (true) {
            if (panel.getBetSize() != 0) {
                clearHands();
                return "";
            } else if (panel.isSavePressed()) {
                panel.setSavePressed(false);
                clearHands();
                panel.addText("Saved game to " + JSON_STORE);
                saveGame();
                return "S";
            }
            stall();
        }
    }

    private void stall() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            return;
        }
    }

    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                return;
            }
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to select if they would like to load file.
    //          if not, load a fresh game
    private void selectionMenu() {
        Scanner user = new Scanner(System.in);
        System.out.print("Would you like to load previous game? (y/n): ");
        String u = user.nextLine();
        if (u.equals("y")) {
            loadGame();
        } else {
            playerHand = new Hand(new ArrayList<>());
            dealerHand = new DealerHand(new ArrayList<>());
            deck = makeDeck();
            deck.addAll(makeDeck());
            deck.addAll(makeDeck());

            balance = 1000;
        }


    }

    private void loadGame() {
        try {
            balance = jsonReader.read();
            panel.addText("Loaded previous game from " + JSON_STORE);
            panel.updateBalance(balance);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: prints balance
    public void printBal() {
        System.out.println("Your Balance: " + balance);
    }

    // REQUIRES: deck is not null and must contain 1 element
    // MODIFIES: this
    // EFFECTS: returns first card in deck and removes it from deck.
    public Card dealCard() {
        return deck.remove(0);
    }

    public String getPHand() {
        return "Player: " + playerHand;
    }

    public String getDHand() {
        return "Dealer: " + dealerHand;
    }

    // MODIFIES: this
    // EFFECTS: Deals two cards to dealer and player
    public void setup() {
        for (int i = 0; i < 2; i++) {
            Card c1 = dealCard();
            panel.addPlayerCard(c1);
            playerHand.addCard(c1);
            panel.addPlayerLabel(playerHand.countHand());
            Card c2 = dealCard();
            dealerHand.addCard(c2);
            if (i == 0) {
                panel.addDealerCard(new Card("blank", "C"));
            } else {
                panel.addDealerCard(c2);
            }
            panel.addDealerLabel(dealerHand.countDealer());


        }
        panel.updateBalance(balance);

    }

    // EFFECTS: asks user to input bet size.
    public double askBet() {
        while (true) {
            if (panel.getBetSize() != 0) {
                panel.setPressed(false);
                if (panel.getBetSize() > balance) {
                    panel.displayString("You're too broke for that bucko.");
                } else {
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                break;
            }
        }
        double bet =  panel.getBetSize();
        panel.setBetSize(0);
        return bet;

    }


    // MODIFIES: this, playerHand
    // EFFECTS: Processes players decision, if they hit it adds a card to their hand.
    public void processDecision(String d) {
        if (d.equals("H")) {
            Card c = dealCard();
            panel.addPlayerCard(c);
            System.out.println("Hit. You got " + c);
            playerHand.addCard(c);
            panel.addPlayerLabel(playerHand.countHand());
            System.out.println("Player: " + playerHand);
        } else if (d.equals("S")) {
            System.out.println("Stand.");
        }
    }

    // MODIFIES: this, dealerHand
    // EFFECTS: dealer draws until hand is equal to or over 17 and returns hand value.
    public int dealerPlay() {
        System.out.println("Dealer Shows: " + dealerHand.showHand());
        while (dealerHand.countHand() < 17) {
            Card c = dealCard();
            panel.addDealerCard(c);
            dealerHand.addCard(c);
            panel.addDealerLabel(dealerHand.countHand());
            System.out.println("Dealer Draws: " + dealerHand.showHand());
        }
        return dealerHand.countHand();
    }

    // MODIFIES: this
    // EFFECTS: starts a new game by clear
    public void clearHands() {
        panel.clearHands();
        playerHand.clearHand();
        dealerHand.clearHand();
        panel.revalidate();
    }

    // EFFECTS: creates a 52 card deck with distinct values
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

    public JSONObject toJson() {
        JSONObject main = new JSONObject();
        main.put("playerHand", playerHand.toJson());
        main.put("dealerHand", dealerHand.toJson());
        main.put("deck", new JSONArray(deck));
        main.put("balance", balance);
        main.put("round", round.getBetSize());
        return main;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public DealerHand getDealerHand() {
        return dealerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public void setDealerHand(DealerHand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public Round getRound() {
        return round;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
