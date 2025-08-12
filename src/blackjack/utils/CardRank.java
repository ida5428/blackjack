package blackjack.utils;

public enum CardRank {

   ACE("A", "Ace", 11),
   TWO("2", "Two", 2),
   THREE("3", "Three", 3),
   FOUR("4", "Four", 4),
   FIVE("5", "Five", 5),
   SIX("6", "Six", 6),
   SEVEN("7", "Seven", 7),
   EIGHT("8", "Eight", 8),
   NINE("9", "Nine", 9),
   TEN("10", "Ten", 10),
   JACK("J", "Jack", 10),
   QUEEN("Q", "Queen", 10),
   KING("K", "King", 10);

   private String id;
   private String name;
   private int value;

   CardRank(String id, String name, int value) {
      this.id = id;
      this.name = name;
      this.value = value;
   }

   public String getID() {
      return id;
   }

   public String getName() {
      return name;
   }

   public int getValue() {
      return value;
   }
}
