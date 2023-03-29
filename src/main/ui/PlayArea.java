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

public class PlayArea extends JPanel {
    private JPanel dealerPanel;
    private JPanel playerPanel;
    private JPanel betPanel;
    private JPanel optionsPanel;
    private JPanel dealerBottomPanel;
    private JPanel playerBottomPanel;
    private int betSize;
    private JButton playButton;
    private JPanel startScreenPanel;

    public PlayArea() {
        // Set the layout manager to BorderLayout
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));

        // Create the start screen panel
        startScreenPanel = new JPanel(new BorderLayout());
        startScreenPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("BLACKJACK - BY TROY", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        startScreenPanel.add(titleLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        buttonPanel.add(playButton);
        startScreenPanel.add(buttonPanel, BorderLayout.CENTER);
        add(startScreenPanel, BorderLayout.CENTER);

        // Set the background color of the sub-panels for visibility
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        revalidate();
    }

    private void startGame() {
        // Remove the start screen panel
        remove(startScreenPanel);

        addPlayerCardsPanel();

        // Add the dealer cards panel
        addDealerCardsPanel();

        // Add the player cards panel


        // Add the bet chips panel
        addBetChipsPanel();

        // Add the game options panel
        addGameOptionsPanel();

        // Revalidate the panel to update the layout
        revalidate();
    }

    private void addDealerCardsPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel dealerLabel = new JLabel("Dealer", JLabel.CENTER);
        dealerLabel.setPreferredSize(new Dimension(30, 50));
        topPanel.add(dealerLabel, BorderLayout.CENTER);

        dealerBottomPanel = new JPanel();
        dealerBottomPanel.setLayout(new BoxLayout(dealerBottomPanel, BoxLayout.X_AXIS));
        dealerBottomPanel.setBackground(new Color(39, 78, 19));

        // Add the two nested panels to the main panel with BorderLayout
        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.add(topPanel, BorderLayout.NORTH);
        dealerPanel.add(dealerBottomPanel, BorderLayout.CENTER);
        JSplitPane res = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, addPlayerCardsPanel(), dealerPanel);
        res.setDividerLocation(0.5);
        add(res, BorderLayout.CENTER);
        revalidate();
    }

    public void addDealerCard(Card c) {
        JLabel label = new JLabel(new ImageIcon(PlayArea.getImage(c)));
        dealerBottomPanel.add(label);
        revalidate();
    }

    private JPanel addPlayerCardsPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel playerLabel = new JLabel("Player", JLabel.CENTER);
        playerLabel.setPreferredSize(new Dimension(30, 50));
        topPanel.add(playerLabel, BorderLayout.CENTER);

        playerBottomPanel = new JPanel();
        playerBottomPanel.setLayout(new BoxLayout(playerBottomPanel, BoxLayout.X_AXIS));
        playerBottomPanel.setBackground(new Color(39, 78, 19));

        // Add the two nested panels to the main panel with BorderLayout
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setMinimumSize(new Dimension(600, 100));
        playerPanel.add(topPanel, BorderLayout.NORTH);
        playerPanel.add(playerBottomPanel, BorderLayout.CENTER);
        return playerPanel;
    }

    public void addPlayerCard(Card c) {
        JLabel label = new JLabel(new ImageIcon(PlayArea.getImage(c)));
        playerBottomPanel.add(label);
        revalidate();
    }

    private void addBetChipsPanel() {
        JPanel betChipsPanel = new JPanel(new BorderLayout());
        betChipsPanel.setBackground(Color.GRAY);
        betPanel = betChipsPanel;
        JPanel betPanelNorth = new JPanel();
        betPanelNorth.setLayout(new BoxLayout(betPanelNorth, BoxLayout.LINE_AXIS));
        betPanelNorth.add(new JLabel("Bet"));
        JTextField textField = new JTextField(10);
        betPanelNorth.add(textField);
        addButton(betPanelNorth, "Bet", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bet = textField.getText();
                betSize = Integer.parseInt(bet);
            }
        });
        addButton(betPanelNorth, "test", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLabel(dealerPanel, Integer.toString(betSize));
                revalidate();
            }
        });
        betPanelNorth.add(new JLabel("Chips"));

        betChipsPanel.add(betPanelNorth, BorderLayout.NORTH);

        add(betChipsPanel, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        panel.add(label);
    }

    private void addTextField(JPanel panel, int columns) {
        JTextField textField = new JTextField(columns);
        panel.add(textField);
    }

    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        panel.add(button);
    }

    private void addGameOptionsPanel() {
        JPanel gameOptionsPanel = new JPanel();
        JLabel gameOptionsLabel = new JLabel("Game Options");
        optionsPanel = gameOptionsPanel;
        gameOptionsPanel.add(gameOptionsLabel);
        gameOptionsPanel.setBackground(Color.LIGHT_GRAY);

        add(gameOptionsPanel, BorderLayout.NORTH);
    }

    public void displayString(String text) {
        // Create a new JLabel with the specified text
        JLabel label = new JLabel(text);

        // Set the preferred size of the label
        label.setPreferredSize(new Dimension(200, 50));

        // Add the label to the PlayArea panel
        add(label);

        // Call revalidate and repaint on the panel to update the layout
        revalidate();
        repaint();
    }

    public static BufferedImage getImage(Card card) {
        String formatted = "";
        String suit = card.getSuit();
        if (suit.equals("C")) {
            formatted = "clubs";
        } else if (suit.equals("D")) {
            formatted = "diamonds";
        } else if (suit.equals("H")) {
            formatted = "Hearts";
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
}

