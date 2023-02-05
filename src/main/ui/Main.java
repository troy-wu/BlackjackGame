package ui;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        g.setup();
        System.out.println("Player: " + g.getPlayerHand());
        System.out.println("Dealer: " + g.getDealerHand());
    }
}
