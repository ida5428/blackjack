package blackjack;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

import blackjack.utils.*;
import static blackjack.utils.Formatting.*;

public class Main {
   public static void main(String[] args) throws InterruptedException {
      final Scanner sc = new Scanner(System.in);
      final boolean isPlayer = true;
      final boolean announceCard = true;

      boolean isPlaying = true;
      boolean introSeq = true;
      boolean dealerHasFaceDown = true;
      boolean canPlayerHit = true;
      boolean canDealerHit = true;
      boolean canPlayAgain = true;

      ArrayList<Card> deck = null;
      ArrayList<Card> playerCards = null;
      ArrayList<Card> dealerCards = null;

      while (isPlaying) {
         if (introSeq) {
            dealerHasFaceDown = true;
            canPlayerHit = true;
            canDealerHit = true;
            canPlayAgain = true;

            deck = setDeck();
            playerCards = new ArrayList<>();
            dealerCards = new ArrayList<>();

            dealCard(deck, playerCards, isPlayer, announceCard);
            dealCard(deck, dealerCards, !isPlayer, announceCard);
            dealCard(deck, playerCards, isPlayer, announceCard);
            dealCard(deck, dealerCards, !isPlayer, !announceCard);

            printCards(playerCards, isPlayer, dealerHasFaceDown);
            printCards(dealerCards, !isPlayer, dealerHasFaceDown);

            introSeq = false;
         }

         if (getHandValue(playerCards) == 21) {
            print(GREEN, "You got blackjack. Standing.");
            System.out.println("It is now the " + colour(BLUE, "dealers turn") + ".");
            print(YELLOW, "The dealer is revealing their card.");
            dealerHasFaceDown = false;
            canPlayerHit = false;
         }

         while (canPlayerHit) {
            print(GREEN, "\n > ");
            String input = sc.nextLine().toLowerCase();

            if (equalsAny(input, "h", "hit")) {
               dealCard(deck, playerCards, isPlayer, announceCard);

               if (getHandValue(playerCards) > 21) {
                  print(RED, "You've broken your hand.\n");
               } else if (getHandValue(playerCards) == 21) {
                  print(GREEN, "You got 21.\n");
               }

               if (getHandValue(playerCards) >= 21) {
                  System.out.println("It is now the " + colour(BLUE, "dealers turn") + ".");
                  print(YELLOW, "The dealer is revealing their card.");
                  dealerHasFaceDown = false;
                  canPlayerHit = false;
               }
            } else if (equalsAny(input, "s", "stand")) {
               print(YELLOW, "You stand.\n");
               System.out.println("It is now the " + colour(BLUE, "dealers turn") + ".");
               print(YELLOW, "The dealer is revealing their card.\n");
               dealerHasFaceDown = false;
               canPlayerHit = false;
            } else {
               clearLine();
               cursorUp();
               continue;
            }

            printCards(playerCards, isPlayer, dealerHasFaceDown);
            printCards(dealerCards, !isPlayer, dealerHasFaceDown);
         }

         while (canDealerHit) {
            Thread.sleep(3000);
            if (getHandValue(dealerCards) >= 17) {
               break;
            }

            dealCard(deck, dealerCards, !isPlayer, announceCard);

            printCards(playerCards, isPlayer, dealerHasFaceDown);
            printCards(dealerCards, !isPlayer, dealerHasFaceDown);
         }

         int playerHand = getHandValue(playerCards);
         int dealerHand = getHandValue(dealerCards);

         if (playerHand > 21 && dealerHand > 21) {
            System.out.println("You both broke your hand. It's a " + colour(YELLOW, "draw") + ".\n");
         } else if (playerHand > 21) {
            System.out.println("Your hand broke. " + colour(RED, "You lose") + ".\n");
         } else if (dealerHand > 21) {
            System.out.println("The Dealers hand broke. " + colour(GREEN, "You win") + ".\n");
         } else if (playerHand == dealerHand) {
            System.out.println("It's a " + colour(YELLOW, "draw") + ".\n");
         } else if (playerHand == 21) {
            System.out.println("You got 21. " + colour(GREEN, "You win") + ".\n");
         } else if (dealerHand == 21) {
            System.out.println("Dealer got 21. " + colour(RED, "You lose") + ".\n");
         } else if (playerHand > dealerHand) {
            System.out.println(colour(GREEN, "You win ") + "with " + playerHand + ".\n");
         } else {
            System.out.println("Dealer wins with " + dealerHand + ". " + colour(RED, "You lose") + ".\n");
         }

         while (canPlayAgain) {
            print(BLUE, "Do you want to play again?\n");
            print(GREEN, " > ");
            String input = sc.nextLine().toLowerCase();

            if (equalsAny(input, "y", "yes")) {
               introSeq = true;
               canPlayAgain = false;
               continue;
            } else if (equalsAny(input, "n", "no")) {
               isPlaying = false;
               canPlayAgain = false;
               continue;
            } else {
               clearLine();
               cursorUp();
               continue;
            }
         }
      }

      print(MAGENTA, "Thank you for playing!\n");
      sc.close();
   }

   public static boolean equalsAny(String value, String... options) {
      for (String option : options) {
         if (option.equals(value)) {
            return true;
         }
      }
      return false;
   }

   public static ArrayList<Card> setDeck() {
      ArrayList<Card> deck = new ArrayList<>();

      CardSuit[] cardSuits = {
         CardSuit.HEARTS,
         CardSuit.DIAMONDS,
         CardSuit.CLUBS,
         CardSuit.SPADES
      };

      CardRank[] cardRanks = {
         CardRank.ACE,
         CardRank.TWO,
         CardRank.THREE,
         CardRank.FOUR,
         CardRank.FIVE,
         CardRank.SIX,
         CardRank.SEVEN,
         CardRank.EIGHT,
         CardRank.NINE,
         CardRank.TEN,
         CardRank.JACK,
         CardRank.QUEEN,
         CardRank.KING
      };

      for (CardSuit suit : cardSuits) {
         for (CardRank rank : cardRanks) {
            deck.add(new Card(suit, rank));
         }
      }

      return deck;
   }

   public static void dealCard(ArrayList<Card> deck, ArrayList<Card> hand, boolean isPlayer, boolean announceCard) {
      int indexNum = new Random().nextInt(deck.size());

      Card randomCard = deck.get(indexNum);
      String rank = randomCard.getRank().getName();
      String suit = randomCard.getSuit().getName();

      hand.add(randomCard);
      deck.remove(indexNum);

      if (isPlayer && announceCard) {
         System.out.println("You are dealt the " + colour(BLUE, rank) + " of " + colour(MAGENTA, suit));
      } else if (!isPlayer && announceCard) {
         System.out.println("The dealer draws a " + colour(BLUE, rank) + " of " + colour(MAGENTA, suit));
      } else if (!isPlayer && !announceCard) {
         System.out.println("The dealer places a card " + colour(YELLOW, "face down") + ".");
      }
   }

   public static int getHandValue(ArrayList<Card> hand) {
      int value = 0;
      int aces = 0;

      for (Card card : hand) {
         value += card.getRank().getValue();
         if (card.getRank() == CardRank.ACE) {
            aces++;
         }
      }

      while (value > 21 && aces > 0) {
         value -= 10;
         aces--;
      }

      return value;
   }

   public static void printCards(ArrayList<Card> cards, boolean isPlayer, boolean dealerHasFaceDown) {
      if (isPlayer) {
         print(CYAN, "\nYour cards: ");
         print(BLUE, getHandValue(cards) + "\n");
      } else {
         print(CYAN, "\nDealers cards: ");
         print(BLUE, (dealerHasFaceDown ? "??" : getHandValue(cards)) + "\n");
      }

      for (int i = 0; i < 11; i++) {
         for (Card card : cards) {
            boolean hiddenCard = !isPlayer && dealerHasFaceDown && cards.indexOf(card) == 1;

            String rank = card.getRank().getID();
            String suit = card.getSuit().getID();

            String cardTemplate = generateCardTemplate(card.getCode(), suit, hiddenCard);
            String[] splitTemplate = cardTemplate.split("\n");
            String line = splitTemplate[i];

            if (!hiddenCard) {
               suit = colour(equalsAny(suit, "\u2665", "\u2666") ? RED : MAGENTA, suit);
               rank = colour(WHITE, rank);
               line = line.replace("s", suit).replace("rr", rank + (rank.contains("10") ? "" : " "));
            }

            System.out.print(line + "  ");
         }
         System.out.println();
      }
   }

   public static String generateCardTemplate(String selection, String suit, boolean isHidden) {
      if (isHidden) {
         return """
            ╭─────────────────╮
            │ ??              │
            │ ? .         .   │
            │   .    .    .   │
            │   .         .   │
            │   .    .    .   │
            │   .         .   │
            │   .    .    .   │
            │   .         . ? │
            │               ??│
            ╰─────────────────╯
         """;
      }

      switch (selection) {
         case "J" -> {
            return """
               ╭─────────────────╮
               │ rr              │
               │ s 0         0   │
               │        0        │
               │   0         0   │
               │      jack       │
               │   0         0   │
               │        0        │
               │   0         0 s │
               │               rr│
               ╰─────────────────╯
            """;
         } case "Q" -> {
            return """
               ╭─────────────────╮
               │ rr              │
               │ s 0         0   │
               │        0        │
               │   0         0   │
               │      queen      │
               │   0         0   │
               │        0        │
               │   0         0 s │
               │               rr│
               ╰─────────────────╯
            """;
         } case "K" -> {
            return """
               ╭─────────────────╮
               │ rr   _.+._      │
               │ s  (^\\/^\\/^)    │
               │     \\@*@*@/     │
               │     {_____}     │
               │    ///"'"\\\\\\    │
               │    (/6   6\\)    │
               │     ||=^=||     │
               │     \\\\\\\\///   s │
               │      \\\\///    rr│
               ╰─────────────────╯
            """;
         }
      }

      String cardTemplate = """
         ╭─────────────────╮
         │ rr              │
         │ s x         x   │
         │   x    x    x   │
         │   x         x   │
         │   x    x    x   │
         │   x         x   │
         │   x    x    x   │
         │   x         x s │
         │               r│
         ╰─────────────────╯
      """;

      for (int i = 0; i < selection.length(); i++) {
         char point = selection.charAt(i);

         if (point == '0') {
            cardTemplate = cardTemplate.replaceFirst("x", " ");
         } else if (point == '1') {
            cardTemplate = cardTemplate.replaceFirst("x", suit);
         }
      }

      return cardTemplate;
   }
}
