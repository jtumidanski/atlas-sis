package com.atlas.sis.model;

public enum Element {
   NEUTRAL(0), PHYSICAL(1), FIRE(2, true), ICE(3, true), LIGHTING(4), POISON(5), HOLY(6, true), DARKNESS(7);

   private int value;
   private boolean special = false;

   Element(int v) {
      this.value = v;
   }

   Element(int v, boolean special) {
      this.value = v;
      this.special = special;
   }

   public static Element getFromChar(char c) {
      switch (Character.toUpperCase(c)) {
         case 'F':
            return FIRE;
         case 'I':
            return ICE;
         case 'L':
            return LIGHTING;
         case 'S':
            return POISON;
         case 'H':
            return HOLY;
         case 'D':
            return DARKNESS;
         case 'P':
            return NEUTRAL;
      }
      throw new IllegalArgumentException("unknown element char " + c);
   }

   public boolean isSpecial() {
      return special;
   }

   public int getValue() {
      return value;
   }
}
