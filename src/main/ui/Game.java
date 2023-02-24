package ui;

import exceptions.InputException;
import model.Card;
import model.DealerHand;
import model.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private Hand playerHand;
    private DealerHand dealerHand;
    private ArrayList<Card> deck;
    private Round round;
    private double balance;

    // EFFECTS: initializes player hand and dealer hand and creates deck
    public Game() {
        playerHand = new Hand(new ArrayList<Card>());
        dealerHand = new DealerHand(new ArrayList<Card>());
        deck = makeDeck();
        balance = 500;
        startingMessage();
    }


    // MODIFIES; this
    // EFFECTS: main function that runs blackjack game and logic.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void main() {
        String keepPlaying = "";
        while (!keepPlaying.equals("X")) {
            round = new Round(askBet());
            setup();
            while (true) {
                String decision = askDecision();
                processDecision(decision);
                if (playerHand.countHand() > 21) {
                    playerBust();
                    break;
                } else if (decision.equals("S")) {
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
                        System.out.println("Push! Your bet was returned");
                        printBal();
                        break;
                    }
                }
            }
            keepPlaying = roundEnd();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when dealer busts and adds win to balance.
    public void dealerBust() {
        System.out.println("Dealer Bust!");
        double win = round.getWinReg();
        System.out.println("You win: " + win);
        balance += win;
        printBal();
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when player busts and deducts bet from balance.
    public void playerBust() {
        System.out.println("Busted!");
        balance -= round.getBetSize();
        printBal();
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when dealer wins and deducts bet from balance.
    public void dealerWin() {
        System.out.println("You Lost! Dealer had a higher hand.");
        balance -= round.getBetSize();
        printBal();
    }

    // MODIFIES: this
    // EFFECTS: prints dialogue when player wins and adds win from balance.
    public void playerWin() {
        System.out.println("You Won! You had a higher hand.");
        double win = round.getWinReg();
        System.out.println("You win: " + win);
        balance += win;
        printBal();
    }

    // MODIFIES: this
    // EFFECTS: clears hands and returns the user input whether or not the user wants to keep playing.
    public String roundEnd() {
        clearHands();
        System.out.println();
        System.out.println("Press Enter to Keep Playing! (Enter 'X' to stop)");
        Scanner user = new Scanner(System.in);
        return user.nextLine();
    }

    // EFFECTS: prints a starting message on launch
    private void startingMessage() {
        Scanner user = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");
        System.out.println("Press Enter to Start!");
        user.nextLine();
        System.out.println("Your Starting Balance is: " + balance);
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

    public String getPlayerHand() {
        return "Player: " + playerHand;
    }

    public String getDealerHand() {
        return "Dealer: " + dealerHand;
    }

    // MODIFIES: this
    // EFFECTS: Deals two cards to dealer and player
    public void setup() {
        for (int i = 0; i < 2; i++) {
            playerHand.addCard(dealCard());
            dealerHand.addCard(dealCard());
        }
        System.out.println(getPlayerHand());
        System.out.println(getDealerHand());

    }

    // EFFECTS: asks user to input bet size.
    public double askBet() {
        System.out.print("Please input bet size: ");
        Scanner user = new Scanner(System.in);
        double bet = user.nextDouble();

        while (bet > balance) {
            System.out.println("Not enough cash bud.");
            System.out.print("Please input bet size: ");
            bet = user.nextDouble();
        }
        return bet;
    }

    // EFFECTS: Asks player the decision to hit or stand, throws exception if invalid input. returns decision.
    public String askDecision() {
        Scanner user = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("What would you like to do? HIT(H) or STAND(S): ");
                String input = user.nextLine();
                boolean invalid = !(input.equals("H") || input.equals("S"));
                if (invalid) {
                    throw new InputException();
                }
                return input;
            } catch (InputException e) {
                System.out.println("Invalid Entry, try again.");
            }
        }
    }

    // MODIFIES: this, playerHand
    // EFFECTS: Processes players decision, if they hit it adds a card to their hand.
    public void processDecision(String d) {
        if (d.equals("H")) {
            Card c = dealCard();
            System.out.println("Hit. You got " + c);
            playerHand.addCard(c);
            System.out.println("Player: " + playerHand);
        } else if (d.equals("S")) {
            System.out.println("Stand.");
        }
    }

    // MODIFIES: this, dealerHand
    // EFFECTS: dealer draws until hand is equal to or over 17 and returns hand value.
    public int dealerPlay() {
        System.out.println("Dealer Shows: " + dealerHand.showHand());
        while (dealerHand.countHand() <= 17) {
            dealerHand.addCard(dealCard());
            System.out.println("Dealer Draws: " + dealerHand.showHand());
        }
        return dealerHand.countHand();
    }

    // MODIFIES: this
    // EFFECTS: starts a new game by clear
    public void clearHands() {
        playerHand.clearHand();
        dealerHand.clearHand();
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


}
