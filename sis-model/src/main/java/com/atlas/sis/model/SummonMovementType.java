package com.atlas.sis.model;

public enum SummonMovementType {
   STATIONARY(0), FOLLOW(1), CIRCLE_FOLLOW(3);
   private final int val;

   SummonMovementType(int val) {
      this.val = val;
   }

   public int getValue() {
      return val;
   }
}
