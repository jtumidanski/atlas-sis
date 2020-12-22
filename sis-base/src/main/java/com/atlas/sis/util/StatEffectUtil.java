package com.atlas.sis.util;

import com.atlas.sis.skill.Beginner;
import com.atlas.sis.skill.Legend;
import com.atlas.sis.skill.Noblesse;

public final class StatEffectUtil {
   private StatEffectUtil() {
   }

   public static boolean isDojoBuff(int sourceId) {
      return sourceId >= 2022359 && sourceId <= 2022421;
   }

   public static boolean isHpMpRecovery(int sourceId) {
      return sourceId == 2022198 || sourceId == 2022337;
   }

   public static boolean isRateCoupon(int sourceId) {
      int itemType = sourceId / 1000;
      return itemType == 5211 || itemType == 5360;
   }

   public static boolean isMonsterCard(int sourceId) {
      int itemType = sourceId / 10000;
      return itemType == 238;
   }

   public static boolean isExpIncrease(int sourceId) {
      return sourceId >= 2022450 && sourceId <= 2022452;
   }

   public static boolean isMapChair(int sourceId) {
      return sourceId == Beginner.MAP_CHAIR || sourceId == Noblesse.MAP_CHAIR || sourceId == Legend.MAP_CHAIR;
   }
}
