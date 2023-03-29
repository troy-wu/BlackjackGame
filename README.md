
# **BlackJack Simulator**

## CPSC210 Project by Troy Wu

For my project I will be recreating *blackjack*.
It will replicate the play and include all the features of popular casino game, blackjack.

You will start with a set amount of money and there will be a button to start the game. You will then be prompted
to enter the amount you'd like to bet for this round, then you and the dealer will be both dealt two cards with the 
dealer hiding one card. 

You will then be prompted to decide on two options: Hit (Deal another card) or Stand (stick with the cards you have).
The objective of the game is to get as close to 21 without going over 21 using the sum of your cards.
In order to win and double your bet, you must be closer to 21 than the dealer without busting . If you lose, you lose the bet size placed for the round. 
If you tie, you get your money back.
Once you stand or bust, the dealer will reveal their hidden card and draw cards until their hand is greater or equal to 17,
then they stand.

#### The card to value representation is as follows:
- 2 to 10: their respective value
- Jack, Queen and King: 10
- Ace: 1 OR 11

Some potential features that I might add are:
- An option to double down a bet or split cards.
- A graph representing your performance in the current session using your balance as data points after every game.
- Play with multiple hands.
- Ability to add side bets.


This application will be made for those looking to have fun playing a
game or those who are interested in learning the rules of blackjack.
This could also be used to simulate probabilities regarding blackjack
or to practice card counting strategies.

This project is of interest to me because I enjoy card games like poker and enjoy working with probability.
I also enjoy playing blackjack from time to time and believe that this project is a good balance of challenging and rewarding.



## User Stories

- As a user, I want to be able to add a card to my hand.
- As a user, I want to be able to be notified if I have busted
- As a user, I want to be able to stand.
- As a user, I want to be able to input my bet size.
- As a user, I want an element of randomness, I don't want the same hand over and over.
- As a user, I want to be able to see the cards in my hand and what they add up to.
- As a user, I want to be able to see the dealers hand once I have stood.

#### Phase 2

- As a user, I would like to be able to have the option to save my game, which includes my balance
- As a user, I would like to be able to load my balance from a file 

#### Phase 3 Instructions For Grader
1. Run Main
2. You are presented with the option to Play (Start a new game) or Load Game, which loads your previously saved balance
3. You must input a number into the space above the "bet" button, then press bet to place a bet and start the round.
4. You can Hit, which adds the Card to your hand, or Stand.
5. Once the round is over, you can press the save button which saves the balance stored

#### Citations

persistence package, including JSONWriter and JSONReader is based on code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
