package ui;

import model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Using Java's JPanel, this class represents the full user interaction of the blackjack game
public class PlayArea extends JPanel {
    private JLabel playerLabel;
    private JLabel dealerLabel;
    private JPanel betPanel;
    private JPanel balancePanel;
    private JPanel optionsPanel;
    private JPanel dealerBottomPanel;
    private JPanel playerBottomPanel;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private double betSize;
    private JPanel startScreenPanel;
    private String decision;
    private boolean isPressed = false;
    private boolean savePressed = false;
    private Boolean loadGame = null;

    // MODIFIES: this
    // EFFECTS: sets up the GUI with a start screen and a playscreen
    public PlayArea() {
        // Set the layout manager to BorderLayout
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));

        // Create the start screen panel
        createStartScreen();

        // Set the background color of the sub-panels for visibility
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: creates a start screen, with a functioning play and load game button.
    private void createStartScreen() {
        startScreenPanel = new JPanel(new BorderLayout());
        startScreenPanel.setBackground(new Color(38, 209, 49));
        JLabel titleLabel = new JLabel("BLACKJACK - BY TROY", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        startScreenPanel.add(titleLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createButton("Load Game", true));
        buttonPanel.add(createButton("Play!", false));
        startScreenPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(startScreenPanel, BorderLayout.CENTER);
    }

    // EFFECTS: returns a button with given label and boolean value that it sets LoadGame to.
    private JButton createButton(String label, boolean b) {
        JButton button = new JButton(label);
        button.setFont(new Font("SansSerif", Font.BOLD, 30));
        button.setPreferredSize(new Dimension(250,75));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame = b;
                startGame();
            }
        });
        return button;
    }

    // MODIFIES: this
    // EFFECTS: Set's up the initial state of the GUI.
    private void startGame() {
        // Remove the start screen panel
        remove(startScreenPanel);
        // Add the dealer cards panel
        addCardPanels();

        // Add the bet chips panel
        addBetChipsPanel();

        // Add the game options panel
        addGameOptionsPanel();

        // Revalidate the panel to update the layout
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: adds both the dealerPanel and playerPanel to the parent Jpanel
    private void addCardPanels() {
        dealerPanel = new JPanel(new BorderLayout());
        JLabel dealerLabel = new JLabel("Dealer", JLabel.CENTER);
        dealerLabel.setPreferredSize(new Dimension(30, 50));
        dealerPanel.add(dealerLabel, BorderLayout.CENTER);

        dealerBottomPanel = new JPanel();
        dealerBottomPanel.setLayout(new BoxLayout(dealerBottomPanel, BoxLayout.X_AXIS));
        dealerBottomPanel.setBackground(new Color(39, 78, 19));

        // Add the two nested panels to the main panel with BorderLayout
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(dealerPanel, BorderLayout.NORTH);
        resultPanel.add(dealerBottomPanel, BorderLayout.CENTER);
        JPanel playerPanel = addPlayerCardsPanel();
        JSplitPane res = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerPanel, resultPanel);
        res.setDividerLocation(0.5);
        add(res, BorderLayout.CENTER);
        revalidate();

    }

    // MODIFIES: this
    // EFFECTS: takes in a card and displays it onto the dealers JPanel
    public void addDealerCard(Card c) {
        JLabel label;
        if (c.getVal().equals("blank")) {
            label = new JLabel(new ImageIcon(resize("src/images/back_light.png")));
        } else {
            label = new JLabel(new ImageIcon(PlayArea.getImage(c)));
        }
        dealerBottomPanel.add(label);
        revalidate();
    }

    // EFFECTS: returns the formatted Player Panel
    private JPanel addPlayerCardsPanel() {
        playerPanel = new JPanel(new BorderLayout());
        playerLabel = new JLabel("Player", JLabel.CENTER);
        playerLabel.setPreferredSize(new Dimension(30, 50));
        playerPanel.add(playerLabel, BorderLayout.CENTER);

        playerBottomPanel = new JPanel();
        playerBottomPanel.setLayout(new BoxLayout(playerBottomPanel, BoxLayout.X_AXIS));
        playerBottomPanel.setBackground(new Color(39, 78, 19));

        // Add the two nested panels to the main panel with BorderLayout
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setMinimumSize(new Dimension(600, 100));
        resultPanel.add(playerPanel, BorderLayout.NORTH);
        resultPanel.add(playerBottomPanel, BorderLayout.CENTER);
        return resultPanel;
    }

    // MODIFIES: this
    // EFFECTS: Adds a label, with a corresponding hand value to display for the player
    public void addPlayerLabel(int number) {
        playerPanel.removeAll();
        playerLabel = new JLabel("Player: " + number, JLabel.CENTER);
        playerLabel.setPreferredSize(new Dimension(30, 50));
        playerPanel.add(playerLabel, BorderLayout.CENTER);
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Adds a label, with a corresponding hand value to display for the dealer
    public void addDealerLabel(int number) {
        dealerPanel.removeAll();
        dealerLabel = new JLabel("Dealer: " + number, JLabel.CENTER);
        dealerLabel.setPreferredSize(new Dimension(30, 50));
        dealerPanel.add(dealerLabel, BorderLayout.CENTER);
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Adds the given card to the player panel
    public void addPlayerCard(Card c) {
        JLabel label = new JLabel(new ImageIcon(PlayArea.getImage(c)));
        label.setMaximumSize(new Dimension(100,150));
        playerBottomPanel.add(label);
        revalidate();
    }

    // EFFECTS: returns a resized, formatted image given an image URL;
    public BufferedImage resize(String url) {
        BufferedImage org = null;
        try {
            org = ImageIO.read(new File(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage res = new BufferedImage(100, 150, org.getType());
        Graphics2D g = res.createGraphics();
        g.drawImage(org, 0, 0, 100, 150, null);
        g.dispose();
        return res;
    }

    // MODIFIES: this
    // EFFECTS: Adds the interaction for the chip betting panel
    private void addBetChipsPanel() {
        betPanel = new JPanel(new BorderLayout());
        betPanel.setBackground(Color.GRAY);
        betPanel.add(createBetPanelNorth(), BorderLayout.CENTER);
        betPanel.add(createBalancePanel(), BorderLayout.SOUTH);
        betPanel.add(createSaveButton(), BorderLayout.EAST);
        add(betPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: returns the Save button which sets savePressed to true if pressed;
    private JButton createSaveButton() {
        JButton save = new JButton("SAVE");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePressed = true;
            }
        });
        save.setMinimumSize(new Dimension(100,20));
        save.setMaximumSize(new Dimension(200,30));
        return save;
    }

    // EFFECTS: returns the north panel of the betpanel.
    private JPanel createBetPanelNorth() {
        JPanel betPanelNorth = new JPanel();
        betPanelNorth.setLayout(new BoxLayout(betPanelNorth, BoxLayout.Y_AXIS));
        JTextField textField = new JTextField(10);
        betPanelNorth.add(textField);
        addButton(betPanelNorth, "Bet", e -> betSize = Integer.parseInt(textField.getText()), new Color(94, 140, 189));
        addButton(betPanelNorth, "Hit", e -> updateDecision("H"), new Color(76, 175, 80));
        addButton(betPanelNorth, "Stand", e -> updateDecision("S"), new Color(158, 8, 8));
        return betPanelNorth;
    }

    // REQUIRES: decision must be either "H" or "S".
    // MODIFIES: this
    // EFFECTS: updates the decision with given string.
    private void updateDecision(String decision) {
        isPressed = true;
        this.decision = decision;
    }

    // EFFECTS: returns the panel representing the panel
    private JPanel createBalancePanel() {
        JPanel balancePanel = new JPanel();
        balancePanel.add(new JLabel("Balance"));
        return balancePanel;
    }

    // MODIFIES: this
    // EFFECTS: displays a label corresponding to the balance of the user.
    public void updateBalance(double b) {
        balancePanel = new JPanel();
        balancePanel.add(new JLabel("Balance: " + b));
        betPanel.add(balancePanel, BorderLayout.SOUTH);
        revalidate();
    }


    // MODIFIES: panel
    // EFFECTS: Adds a button with given text, color and listener to the given panel.
    private void addButton(JPanel panel, String text, ActionListener listener, Color color) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(100, 50));
        button.setBackground(color);
        panel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: Adds options panel at top of the screen.
    private void addGameOptionsPanel() {
        optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBackground(Color.LIGHT_GRAY);

        add(optionsPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: Adds a text containing the status of the game at the top of the screen.
    public void addText(String text) {
        optionsPanel.removeAll();
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 30));
        optionsPanel.add(label, BorderLayout.CENTER);
    }

    // REQUIRES: Card must be a valid card, (one in a 52 card deck)
    // EFFECTS: takes a given card and returns a resized image of it
    public static BufferedImage getImage(Card card) {
        String formatted = "";
        String suit = card.getSuit();
        if (suit.equals("C")) {
            formatted = "clubs";
        } else if (suit.equals("D")) {
            formatted = "diamonds";
        } else if (suit.equals("H")) {
            formatted = "hearts";
        } else if (suit.equals("S")) {
            formatted = "spades";
        }
        formatted += "_" + card.getVal();
        try {
            BufferedImage org = ImageIO.read(new File("src/images/" + formatted + ".png"));
            BufferedImage res = new BufferedImage(100, 150, org.getType());
            Graphics2D g = res.createGraphics();
            g.drawImage(org, 0, 0, 100, 150, null);
            g.dispose();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the dealer hand in the UI.
    public void revealDealer(ArrayList<Card> cards) {
        dealerBottomPanel.removeAll();
        for (Card c : cards) {
            addDealerCard(c);
        }

    }

    // MODIFIES: this
    // EFFECTS: clears both player and dealers label and cards.
    public void clearHands() {
        playerPanel.removeAll();
        playerLabel = new JLabel("Player", JLabel.CENTER);
        playerLabel.setPreferredSize(new Dimension(30, 50));
        playerPanel.add(playerLabel, BorderLayout.CENTER);
        dealerPanel.removeAll();
        dealerLabel = new JLabel("Dealer", JLabel.CENTER);
        dealerLabel.setPreferredSize(new Dimension(30, 50));
        dealerPanel.add(dealerLabel, BorderLayout.CENTER);
        playerBottomPanel.removeAll();
        dealerBottomPanel.removeAll();
        revalidate();
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public String getDecision() {
        return decision;
    }

    public double getBetSize() {
        return betSize;
    }

    public void setBetSize(double i) {
        betSize = i;
    }

    public boolean isSavePressed() {
        return savePressed;
    }

    public void setSavePressed(boolean savePressed) {
        this.savePressed = savePressed;
    }

    public Boolean isLoadGame() {
        return loadGame;
    }

    public void setLoadGame(Boolean loadGame) {
        this.loadGame = loadGame;
    }
}

