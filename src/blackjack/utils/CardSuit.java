package blackjack.utils;

public enum CardSuit {

   HEARTS("\u2665", "Hearts"),
   DIAMONDS("\u2666", "Diamonds"),
   CLUBS("\u2663",  "Clubs"),
   SPADES("\u2660", "Spades");

   private String id;
   private String name;

   CardSuit(String id, String name) {
      this.id = id;
      this.name = name;
   }

   public String getID() {
      return id;
   }

   public String getName() {
      return name;
   }
}
