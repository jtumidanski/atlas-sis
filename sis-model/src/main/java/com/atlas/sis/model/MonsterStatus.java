package com.atlas.sis.model;

public enum MonsterStatus {
   WEAPON_ATTACK(0x1),
   WEAPON_DEFENSE(0x2),
   NEUTRALISE(0x2, true),
   PHANTOM_IMPRINT(0x4, true), // needs testing
   MAGIC_ATTACK(0x4),
   MAGIC_DEFENSE(0x8),
   ACC(0x10),
   AVOID(0x20),
   SPEED(0x40),
   STUN(0x80),
   FREEZE(0x100),
   POISON(0x200),
   SEAL(0x400),
   SHOWDOWN(0x800),
   WEAPON_ATTACK_UP(0x1000),
   WEAPON_DEFENSE_UP(0x2000),
   MAGIC_ATTACK_UP(0x4000),
   MAGIC_DEFENSE_UP(0x8000),
   DOOM(0x10000),
   SHADOW_WEB(0x20000),
   WEAPON_IMMUNITY(0x40000),
   MAGIC_IMMUNITY(0x80000),
   HARD_SKIN(0x200000), // just added
   NINJA_AMBUSH(0x400000),
   ELEMENTAL_ATTRIBUTE(0x800000), // just added
   VENOMOUS_WEAPON(0x1000000),
   BLIND(0x2000000), // just added
   SEAL_SKILL(0x4000000),
   INERT_MOB(0x10000000),
   WEAPON_REFLECT(0x20000000, true),
   MAGIC_REFLECT(0x40000000, true);

   private final int i;
   private final boolean first;

   MonsterStatus(int i) {
      this.i = i;
      this.first = false;
   }

   MonsterStatus(int i, boolean first) {
      this.i = i;
      this.first = first;
   }

   public boolean isFirst() {
      return first;
   }

   public int getValue() {
      return i;
   }
}