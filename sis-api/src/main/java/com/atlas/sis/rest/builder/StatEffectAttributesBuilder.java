package com.atlas.sis.rest.builder;

import java.awt.*;
import java.util.List;

import com.app.common.builder.RecordBuilder;
import com.atlas.sis.rest.attribute.BuffStatAmountAttributes;
import com.atlas.sis.rest.attribute.CardItemUpStatsAttributes;
import com.atlas.sis.rest.attribute.MonsterStatusAttributes;
import com.atlas.sis.rest.attribute.StatEffectAttributes;

import builder.AttributeResultBuilder;

public class StatEffectAttributesBuilder extends RecordBuilder<StatEffectAttributes, StatEffectAttributesBuilder>
      implements AttributeResultBuilder {
   private Short weaponAttack;

   private Short magicAttack;

   private Short weaponDefense;

   private Short magicDefense;

   private Short accuracy;

   private Short avoidability;

   private Short speed;

   private Short jump;

   private Short hp;

   private Short mp;

   private Double hpR;

   private Double mpR;

   private Short mhpRRate;

   private Short mmpRRate;

   private Short mobSkill;

   private Short mobSkillLevel;

   private Byte mhpR;

   private Byte mmpR;

   private Short mpCon;

   private Short hpCon;

   private Integer duration;

   private Integer target;

   private Integer barrier;

   private Integer mob;

   private Boolean overTime;

   private Boolean repeatEffect;

   private Integer moveTo;

   private Integer cp;

   private Integer nuffSkill;

   private Boolean skill;

   private Integer x;

   private Integer y;

   private Integer mobCount;

   private Integer moneyCon;

   private Integer cooldown;

   private Integer morphId;

   private Integer ghost;

   private Integer fatigue;

   private Integer berserk;

   private Integer booster;

   private Double prop;

   private Integer itemCon;

   private Integer itemConNo;

   private Integer damage;

   private Integer attackCount;

   private Integer fixDamage;

   private Point lt;

   private Point rb;

   private Short bulletCount;

   private Short bulletConsume;

   private Byte mapProtection;

   private List<String> cureAbnormalStatuses;

   private List<BuffStatAmountAttributes> statups;

   private List<MonsterStatusAttributes> monsterStatus;

   private CardItemUpStatsAttributes cardStats;

   @Override
   public StatEffectAttributes construct() {
      return new StatEffectAttributes(weaponAttack, magicAttack, weaponDefense, magicDefense, accuracy, avoidability,
            speed, jump, hp, mp, hpR, mpR, mhpRRate, mmpRRate, mobSkill, mobSkillLevel, mhpR, mmpR, mpCon, hpCon, duration, target,
            barrier, mob, overTime, repeatEffect, moveTo, cp, nuffSkill, skill, x, y, mobCount, moneyCon, cooldown, morphId, ghost,
            fatigue, berserk, booster, prop, itemCon, itemConNo, damage, attackCount, fixDamage, lt, rb, bulletCount, bulletConsume,
            mapProtection, cureAbnormalStatuses, statups, monsterStatus, cardStats);
   }

   @Override
   public StatEffectAttributesBuilder getThis() {
      return this;
   }

   public StatEffectAttributesBuilder setWeaponAttack(Short weaponAttack) {
      this.weaponAttack = weaponAttack;
      return getThis();
   }

   public StatEffectAttributesBuilder setMagicAttack(Short magicAttack) {
      this.magicAttack = magicAttack;
      return getThis();
   }

   public StatEffectAttributesBuilder setWeaponDefense(Short weaponDefense) {
      this.weaponDefense = weaponDefense;
      return getThis();
   }

   public StatEffectAttributesBuilder setMagicDefense(Short magicDefense) {
      this.magicDefense = magicDefense;
      return getThis();
   }

   public StatEffectAttributesBuilder setAccuracy(Short accuracy) {
      this.accuracy = accuracy;
      return getThis();
   }

   public StatEffectAttributesBuilder setAvoidability(Short avoidability) {
      this.avoidability = avoidability;
      return getThis();
   }

   public StatEffectAttributesBuilder setSpeed(Short speed) {
      this.speed = speed;
      return getThis();
   }

   public StatEffectAttributesBuilder setJump(Short jump) {
      this.jump = jump;
      return getThis();
   }

   public StatEffectAttributesBuilder setHp(Short hp) {
      this.hp = hp;
      return getThis();
   }

   public StatEffectAttributesBuilder setMp(Short mp) {
      this.mp = mp;
      return getThis();
   }

   public StatEffectAttributesBuilder setHpR(Double hpR) {
      this.hpR = hpR;
      return getThis();
   }

   public StatEffectAttributesBuilder setMpR(Double mpR) {
      this.mpR = mpR;
      return getThis();
   }

   public StatEffectAttributesBuilder setMhpRRate(Short mhpRRate) {
      this.mhpRRate = mhpRRate;
      return getThis();
   }

   public StatEffectAttributesBuilder setMmpRRate(Short mmpRRate) {
      this.mmpRRate = mmpRRate;
      return getThis();
   }

   public StatEffectAttributesBuilder setMobSkill(Short mobSkill) {
      this.mobSkill = mobSkill;
      return getThis();
   }

   public StatEffectAttributesBuilder setMobSkillLevel(Short mobSkillLevel) {
      this.mobSkillLevel = mobSkillLevel;
      return getThis();
   }

   public StatEffectAttributesBuilder setMhpR(Byte mhpR) {
      this.mhpR = mhpR;
      return getThis();
   }

   public StatEffectAttributesBuilder setMmpR(Byte mmpR) {
      this.mmpR = mmpR;
      return getThis();
   }

   public StatEffectAttributesBuilder setMpCon(Short mpCon) {
      this.mpCon = mpCon;
      return getThis();
   }

   public StatEffectAttributesBuilder setHpCon(Short hpCon) {
      this.hpCon = hpCon;
      return getThis();
   }

   public StatEffectAttributesBuilder setDuration(Integer duration) {
      this.duration = duration;
      return getThis();
   }

   public StatEffectAttributesBuilder setTarget(Integer target) {
      this.target = target;
      return getThis();
   }

   public StatEffectAttributesBuilder setBarrier(Integer barrier) {
      this.barrier = barrier;
      return getThis();
   }

   public StatEffectAttributesBuilder setMob(Integer mob) {
      this.mob = mob;
      return getThis();
   }

   public StatEffectAttributesBuilder setOverTime(Boolean overTime) {
      this.overTime = overTime;
      return getThis();
   }

   public StatEffectAttributesBuilder setRepeatEffect(Boolean repeatEffect) {
      this.repeatEffect = repeatEffect;
      return getThis();
   }

   public StatEffectAttributesBuilder setMoveTo(Integer moveTo) {
      this.moveTo = moveTo;
      return getThis();
   }

   public StatEffectAttributesBuilder setCp(Integer cp) {
      this.cp = cp;
      return getThis();
   }

   public StatEffectAttributesBuilder setNuffSkill(Integer nuffSkill) {
      this.nuffSkill = nuffSkill;
      return getThis();
   }

   public StatEffectAttributesBuilder setSkill(Boolean skill) {
      this.skill = skill;
      return getThis();
   }

   public StatEffectAttributesBuilder setX(Integer x) {
      this.x = x;
      return getThis();
   }

   public StatEffectAttributesBuilder setY(Integer y) {
      this.y = y;
      return getThis();
   }

   public StatEffectAttributesBuilder setMobCount(Integer mobCount) {
      this.mobCount = mobCount;
      return getThis();
   }

   public StatEffectAttributesBuilder setMoneyCon(Integer moneyCon) {
      this.moneyCon = moneyCon;
      return getThis();
   }

   public StatEffectAttributesBuilder setCooldown(Integer cooldown) {
      this.cooldown = cooldown;
      return getThis();
   }

   public StatEffectAttributesBuilder setMorphId(Integer morphId) {
      this.morphId = morphId;
      return getThis();
   }

   public StatEffectAttributesBuilder setGhost(Integer ghost) {
      this.ghost = ghost;
      return getThis();
   }

   public StatEffectAttributesBuilder setFatigue(Integer fatigue) {
      this.fatigue = fatigue;
      return getThis();
   }

   public StatEffectAttributesBuilder setBerserk(Integer berserk) {
      this.berserk = berserk;
      return getThis();
   }

   public StatEffectAttributesBuilder setBooster(Integer booster) {
      this.booster = booster;
      return getThis();
   }

   public StatEffectAttributesBuilder setProp(Double prop) {
      this.prop = prop;
      return getThis();
   }

   public StatEffectAttributesBuilder setItemCon(Integer itemCon) {
      this.itemCon = itemCon;
      return getThis();
   }

   public StatEffectAttributesBuilder setItemConNo(Integer itemConNo) {
      this.itemConNo = itemConNo;
      return getThis();
   }

   public StatEffectAttributesBuilder setDamage(Integer damage) {
      this.damage = damage;
      return getThis();
   }

   public StatEffectAttributesBuilder setAttackCount(Integer attackCount) {
      this.attackCount = attackCount;
      return getThis();
   }

   public StatEffectAttributesBuilder setFixDamage(Integer fixDamage) {
      this.fixDamage = fixDamage;
      return getThis();
   }

   public StatEffectAttributesBuilder setLt(Point lt) {
      this.lt = lt;
      return getThis();
   }

   public StatEffectAttributesBuilder setRb(Point rb) {
      this.rb = rb;
      return getThis();
   }

   public StatEffectAttributesBuilder setBulletCount(Short bulletCount) {
      this.bulletCount = bulletCount;
      return getThis();
   }

   public StatEffectAttributesBuilder setBulletConsume(Short bulletConsume) {
      this.bulletConsume = bulletConsume;
      return getThis();
   }

   public StatEffectAttributesBuilder setMapProtection(Byte mapProtection) {
      this.mapProtection = mapProtection;
      return getThis();
   }

   public StatEffectAttributesBuilder setCureAbnormalStatuses(List<String> cureAbnormalStatuses) {
      this.cureAbnormalStatuses = cureAbnormalStatuses;
      return getThis();
   }

   public StatEffectAttributesBuilder setStatups(List<BuffStatAmountAttributes> statups) {
      this.statups = statups;
      return getThis();
   }

   public StatEffectAttributesBuilder setMonsterStatus(List<MonsterStatusAttributes> monsterStatus) {
      this.monsterStatus = monsterStatus;
      return getThis();
   }

   public StatEffectAttributesBuilder setCardStats(CardItemUpStatsAttributes cardStats) {
      this.cardStats = cardStats;
      return getThis();
   }
}
