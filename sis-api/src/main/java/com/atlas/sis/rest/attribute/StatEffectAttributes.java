package com.atlas.sis.rest.attribute;

import java.awt.Point;
import java.util.List;

import rest.AttributeResult;

public record StatEffectAttributes(Short weaponAttack, Short magicAttack, Short weaponDefense, Short magicDefense,
                                   Short accuracy, Short avoidability, Short speed, Short jump, Short hp, Short mp, Double hpR,
                                   Double mpR, Short mhpRRate, Short mmpRRate, Short mobSkill, Short mobSkillLevel, Byte mhpR,
                                   Byte mmpR, Short mpCon, Short hpCon, Integer duration, Integer target, Integer barrier,
                                   Integer mob, Boolean overTime, Boolean repeatEffect, Integer moveTo, Integer cp,
                                   Integer nuffSkill, Boolean skill, Integer x, Integer y, Integer mobCount, Integer moneyCon,
                                   Integer cooldown, Integer morphId, Integer ghost, Integer fatigue, Integer berserk,
                                   Integer booster, Double prop, Integer itemCon, Integer itemConNo, Integer damage,
                                   Integer attackCount, Integer fixDamage, Point lt, Point rb, Short bulletCount,
                                   Short bulletConsume, Byte mapProtection, List<String> cureAbnormalStatuses,
                                   List<BuffStatAmountAttributes> statups, List<MonsterStatusAttributes> monsterStatus,
                                   CardItemUpStatsAttributes cardStats)
      implements AttributeResult {
}
