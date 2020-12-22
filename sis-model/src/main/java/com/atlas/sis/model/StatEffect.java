package com.atlas.sis.model;

import java.awt.*;
import java.util.List;
import java.util.Map;

public record StatEffect(int skillId, short weaponAttack, short magicAttack, short weaponDefense, short magicDefense,
                         short accuracy, short avoidability, short speed, short jump, short hp, short mp, double hpR, double mpR,
                         short mhpRRate, short mmpRRate, short mobSkill, short mobSkillLevel, byte mhpR, byte mmpR, short mpCon,
                         short hpCon, int duration, int target, int barrier, int mob, boolean overTime, boolean repeatEffect,
                         int moveTo, int cp, int nuffSkill, boolean skill, int x, int y, int mobCount, int moneyCon, int cooldown,
                         int morphId, int ghost, int fatigue, int berserk, int booster, double prop, int itemCon, int itemConNo,
                         int damage, int attackCount, int fixDamage, Point lt, Point rb, short bulletCount, short bulletConsume,
                         byte mapProtection, List<AbnormalStatus> cureAbnormalStatuses, List<BuffStatAmount> statups,
                         Map<MonsterStatus, Integer> monsterStatus, CardItemUpStats cardStats) {
}
