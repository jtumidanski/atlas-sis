package com.atlas.sis.model;

public enum AbnormalStatus {
   NULL(0x0),
   SLOW(0x1, 126),
   SEDUCE(0x80, 128),
   FISHABLE(0x100),
   ZOMBIFY(0x4000),
   CONFUSE(0x80000, 132),
   STUN(0x2000000000000L, 123),
   POISON(0x4000000000000L, 125),
   SEAL(0x8000000000000L, 120),
   DARKNESS(0x10000000000000L, 121),
   WEAKEN(0x4000000000000000L, 122),
   CURSE(0x8000000000000000L, 124);

   private long i;
   private boolean first;
   private int mobSkill;

   AbnormalStatus(long i) {
      this(i, false, 0);
   }

   AbnormalStatus(long i, int skill) {
      this(i, false, skill);
   }

   AbnormalStatus(long i, boolean first, int skill) {
      this.i = i;
      this.first = first;
      this.mobSkill = skill;
   }

   public static AbnormalStatus ordinal(int ord) {
      try {
         return AbnormalStatus.values()[ord];
      } catch (IndexOutOfBoundsException io) {
         return NULL;
      }
   }

   public static final AbnormalStatus[] CPQ_DISEASES =
         {AbnormalStatus.SLOW, AbnormalStatus.SEDUCE, AbnormalStatus.STUN, AbnormalStatus.POISON,
               AbnormalStatus.SEAL, AbnormalStatus.DARKNESS, AbnormalStatus.WEAKEN, AbnormalStatus.CURSE};

   public static AbnormalStatus getRandom() {
      AbnormalStatus[] diseases = CPQ_DISEASES;
      return diseases[(int) (Math.random() * diseases.length)];
   }

   public static AbnormalStatus getBySkill(final int skill) {
      for (AbnormalStatus d : AbnormalStatus.values()) {
         if (d.getDisease() == skill && d.getDisease() != 0) {
            return d;
         }
      }
      return null;
   }

   public long getValue() {
      return i;
   }

   public boolean isFirst() {
      return first;
   }

   public int getDisease() {
      return mobSkill;
   }
}