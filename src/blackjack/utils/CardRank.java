package blackjack.utils;

public enum CardRank {

   ACE("A", "Ace", 11, "00000000100000000"),
   TWO("2", "Two", 2, "00010000000001000"),
   THREE("3", "Three", 3, "00010000100001000"),
   FOUR("4", "Four", 4, "00101000000010100"),
   FIVE("5", "Five", 5, "00101000100010100"),
   SIX("6", "Six", 6, "00101001010010100"),
   SEVEN("7", "Seven", 7, "11010001010000011"),
   EIGHT("8", "Eight", 8, "11010001010001011"),
   NINE("9", "Nine", 9, "11000110101100011"),
   TEN("10", "Ten", 10, "11010110001101011"),
   JACK("J", "Jack", 10, "J"),
   QUEEN("Q", "Queen", 10, "Q"),
   KING("K", "King", 10, "K");

   private String id;
   private String name;
   private int value;
   private String code;

   CardRank(String id, String name, int value, String code) {
      this.id = id;
      this.name = name;
      this.value = value;
      this.code = code;
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

   public String getCode() {
      return code;
   }
}
