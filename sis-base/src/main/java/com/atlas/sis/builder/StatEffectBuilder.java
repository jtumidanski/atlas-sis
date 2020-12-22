package com.atlas.sis.builder;

import java.awt.*;
import java.util.List;
import java.util.Map;

import com.app.common.builder.RecordBuilder;
import com.atlas.sis.model.AbnormalStatus;
import com.atlas.sis.model.BuffStatAmount;
import com.atlas.sis.model.CardItemUpStats;
import com.atlas.sis.model.MonsterStatus;
import com.atlas.sis.model.StatEffect;
import com.atlas.sis.model.SummonMovementType;
import com.atlas.sis.skill.Bishop;
import com.atlas.sis.skill.BlazeWizard;
import com.atlas.sis.skill.BowMaster;
import com.atlas.sis.skill.Corsair;
import com.atlas.sis.skill.DarkKnight;
import com.atlas.sis.skill.DawnWarrior;
import com.atlas.sis.skill.FirePoisonArchMage;
import com.atlas.sis.skill.IceLighteningArchMagician;
import com.atlas.sis.skill.Marksman;
import com.atlas.sis.skill.NightWalker;
import com.atlas.sis.skill.Outlaw;
import com.atlas.sis.skill.Priest;
import com.atlas.sis.skill.Ranger;
import com.atlas.sis.skill.Sniper;
import com.atlas.sis.skill.ThunderBreaker;
import com.atlas.sis.skill.WindArcher;

public class StatEffectBuilder extends RecordBuilder<StatEffect, StatEffectBuilder> {
   private final Integer skillId;

   private Integer duration;

   private Short hp;

   private Double hpR;

   private Short mp;

   private Double mpR;

   private Short hpCon;

   private Short mpCon;

   private Double prop;

   private Integer cp;

   private List<AbnormalStatus> cureAbnormalStatuses;

   private Integer nuffSkill;

   private Integer mobCount;

   private Integer cooldown;

   private Integer morphId;

   private Integer ghost;

   private Integer fatigue;

   private Boolean repeatEffect;

   private Short mobSkill;

   private Short mobSkillLevel;

   private Integer target;

   private Integer mob = 0;

   private Integer sourceId;

   private boolean skill;

   private boolean overTime;

   private Short weaponAttack;

   private Short magicAttack;

   private Short weaponDefense;

   private Short magicDefense;

   private Short accuracy;

   private Short avoidability;

   private Short speed;

   private Short jump;

   private Integer barrier;

   private Byte mapProtection;

   private Integer berserk = 0;

   private Integer booster = 0;

   private Byte mhpR = 0;

   private Byte mmpR = 0;

   private Short mhpRRate = 0;

   private Short mmpRRate = 0;

   private CardItemUpStats cardStats;

   private Point lt;

   private Point rb;

   private Integer x;

   private Integer y;

   private Integer damage;

   private Integer fixDamage;

   private Integer attackCount;

   private Short bulletCount;

   private Short bulletConsume;

   private Integer moneyCon;

   private Integer itemCon;

   private Integer itemConNo;

   private Integer moveTo;

   private Map<MonsterStatus, Integer> monsterStatus;

   private List<BuffStatAmount> statups;

   public StatEffectBuilder(Integer skillId) {
      this.skillId = skillId;
   }

   @Override
   public StatEffectBuilder getThis() {
      return this;
   }

   @Override
   public StatEffect construct() {
      return new StatEffect(skillId, weaponAttack, magicAttack, weaponDefense, magicDefense, accuracy, avoidability, speed, jump,
            hp, mp, hpR, mpR, mhpRRate, mmpRRate, mobSkill, mobSkillLevel, mhpR, mmpR, mpCon, hpCon, duration, target, barrier,
            mob, overTime, repeatEffect, moveTo, cp, nuffSkill, skill, x, y, mobCount, moneyCon, cooldown, morphId, ghost,
            fatigue, berserk, booster, prop, itemCon, itemConNo, damage, attackCount, fixDamage, lt, rb, bulletCount,
            bulletConsume, mapProtection, cureAbnormalStatuses, statups, monsterStatus, cardStats);
   }

   public StatEffectBuilder setDuration(Integer duration) {
      this.duration = duration;
      return getThis();
   }

   public StatEffectBuilder setHp(Short hp) {
      this.hp = hp;
      return getThis();
   }

   public StatEffectBuilder setHpR(Double hpR) {
      this.hpR = hpR;
      return getThis();
   }

   public StatEffectBuilder setMp(Short mp) {
      this.mp = mp;
      return getThis();
   }

   public StatEffectBuilder setMpR(Double mpR) {
      this.mpR = mpR;
      return getThis();
   }

   public StatEffectBuilder setHpCon(Short hpCon) {
      this.hpCon = hpCon;
      return getThis();
   }

   public StatEffectBuilder setMpCon(Short mpCon) {
      this.mpCon = mpCon;
      return getThis();
   }

   public StatEffectBuilder setProp(Double prop) {
      this.prop = prop;
      return getThis();
   }

   public StatEffectBuilder setCp(Integer cp) {
      this.cp = cp;
      return getThis();
   }

   public StatEffectBuilder setCureAbnormalStatuses(List<AbnormalStatus> cureAbnormalStatuses) {
      this.cureAbnormalStatuses = cureAbnormalStatuses;
      return getThis();
   }

   public StatEffectBuilder setNuffSkill(Integer nuffSkill) {
      this.nuffSkill = nuffSkill;
      return getThis();
   }

   public StatEffectBuilder setMobCount(Integer mobCount) {
      this.mobCount = mobCount;
      return getThis();
   }

   public StatEffectBuilder setCooldown(Integer cooldown) {
      this.cooldown = cooldown;
      return getThis();
   }

   public StatEffectBuilder setMorphId(Integer morphId) {
      this.morphId = morphId;
      return getThis();
   }

   public StatEffectBuilder setGhost(Integer ghost) {
      this.ghost = ghost;
      return getThis();
   }

   public StatEffectBuilder setFatigue(Integer fatigue) {
      this.fatigue = fatigue;
      return getThis();
   }

   public StatEffectBuilder setRepeatEffect(Boolean repeatEffect) {
      this.repeatEffect = repeatEffect;
      return getThis();
   }

   public StatEffectBuilder setMobSkill(Short mobSkill) {
      this.mobSkill = mobSkill;
      return getThis();
   }

   public StatEffectBuilder setMobSkillLevel(Short mobSkillLevel) {
      this.mobSkillLevel = mobSkillLevel;
      return getThis();
   }

   public StatEffectBuilder setTarget(Integer target) {
      this.target = target;
      return getThis();
   }

   public StatEffectBuilder setMob(Integer mob) {
      this.mob = mob;
      return getThis();
   }

   public StatEffectBuilder setSourceId(Integer sourceId) {
      this.sourceId = sourceId;
      return getThis();
   }

   public StatEffectBuilder setSkill(boolean skill) {
      this.skill = skill;
      return getThis();
   }

   public StatEffectBuilder setOverTime(boolean overTime) {
      this.overTime = overTime;
      return getThis();
   }

   public StatEffectBuilder setWeaponAttack(Short weaponAttack) {
      this.weaponAttack = weaponAttack;
      return getThis();
   }

   public StatEffectBuilder setMagicAttack(Short magicAttack) {
      this.magicAttack = magicAttack;
      return getThis();
   }

   public StatEffectBuilder setWeaponDefense(Short weaponDefense) {
      this.weaponDefense = weaponDefense;
      return getThis();
   }

   public StatEffectBuilder setMagicDefense(Short magicDefense) {
      this.magicDefense = magicDefense;
      return getThis();
   }

   public StatEffectBuilder setAccuracy(Short accuracy) {
      this.accuracy = accuracy;
      return getThis();
   }

   public StatEffectBuilder setAvoidability(Short avoidability) {
      this.avoidability = avoidability;
      return getThis();
   }

   public StatEffectBuilder setSpeed(Short speed) {
      this.speed = speed;
      return getThis();
   }

   public StatEffectBuilder setJump(Short jump) {
      this.jump = jump;
      return getThis();
   }

   public StatEffectBuilder setBarrier(Integer barrier) {
      this.barrier = barrier;
      return getThis();
   }

   public StatEffectBuilder setMapProtection(Byte mapProtection) {
      this.mapProtection = mapProtection;
      return getThis();
   }

   public StatEffectBuilder setBerserk(Integer berserk) {
      this.berserk = berserk;
      return getThis();
   }

   public StatEffectBuilder setBooster(Integer booster) {
      this.booster = booster;
      return getThis();
   }

   public StatEffectBuilder setMhpR(Byte mhpR) {
      this.mhpR = mhpR;
      return getThis();
   }

   public StatEffectBuilder setMmpR(Byte mmpR) {
      this.mmpR = mmpR;
      return getThis();
   }

   public StatEffectBuilder setMhpRRate(Short mhpRRate) {
      this.mhpRRate = mhpRRate;
      return getThis();
   }

   public StatEffectBuilder setMmpRRate(Short mmpRRate) {
      this.mmpRRate = mmpRRate;
      return getThis();
   }

   public StatEffectBuilder setCardStats(CardItemUpStats cardStats) {
      this.cardStats = cardStats;
      return getThis();
   }

   public StatEffectBuilder setLt(Point lt) {
      this.lt = lt;
      return getThis();
   }

   public StatEffectBuilder setRb(Point rb) {
      this.rb = rb;
      return getThis();
   }

   public StatEffectBuilder setX(Integer x) {
      this.x = x;
      return getThis();
   }

   public StatEffectBuilder setY(Integer y) {
      this.y = y;
      return getThis();
   }

   public StatEffectBuilder setDamage(Integer damage) {
      this.damage = damage;
      return getThis();
   }

   public StatEffectBuilder setFixDamage(Integer fixDamage) {
      this.fixDamage = fixDamage;
      return getThis();
   }

   public StatEffectBuilder setAttackCount(Integer attackCount) {
      this.attackCount = attackCount;
      return getThis();
   }

   public StatEffectBuilder setBulletCount(Short bulletCount) {
      this.bulletCount = bulletCount;
      return getThis();
   }

   public StatEffectBuilder setBulletConsume(Short bulletConsume) {
      this.bulletConsume = bulletConsume;
      return getThis();
   }

   public StatEffectBuilder setMoneyCon(Integer moneyCon) {
      this.moneyCon = moneyCon;
      return getThis();
   }

   public StatEffectBuilder setItemCon(Integer itemCon) {
      this.itemCon = itemCon;
      return getThis();
   }

   public StatEffectBuilder setItemConNo(Integer itemConNo) {
      this.itemConNo = itemConNo;
      return getThis();
   }

   public StatEffectBuilder setMoveTo(Integer moveTo) {
      this.moveTo = moveTo;
      return getThis();
   }

   public StatEffectBuilder setMonsterStatus(Map<MonsterStatus, Integer> monsterStatus) {
      this.monsterStatus = monsterStatus;
      return getThis();
   }

   public StatEffectBuilder setStatups(List<BuffStatAmount> statups) {
      this.statups = statups;
      return getThis();
   }

   public Integer getDuration() {
      return duration;
   }

   public boolean isSkill() {
      return skill;
   }

   public Integer getBarrier() {
      return barrier;
   }

   public Byte getMapProtection() {
      return mapProtection;
   }

   public boolean isOverTime() {
      return overTime;
   }

   public SummonMovementType getSummonMovementType() {
      if (!skill) {
         return null;
      }
      return switch (sourceId) {
         case Ranger.PUPPET, Sniper.PUPPET, WindArcher.PUPPET, Outlaw.OCTOPUS,
               Corsair.WRATH_OF_THE_OCTOPI -> SummonMovementType.STATIONARY;
         case Ranger.SILVER_HAWK, Sniper.GOLDEN_EAGLE, Priest.SUMMON_DRAGON, Marksman.FROST_PREY, BowMaster.PHOENIX,
               Outlaw.GAVIOTA -> SummonMovementType.CIRCLE_FOLLOW;
         case DarkKnight.BEHOLDER, FirePoisonArchMage.ELQUINES, IceLighteningArchMagician.IFRIT, Bishop.BAHAMUT,
               DawnWarrior.SOUL, BlazeWizard.FLAME, BlazeWizard.IFRIT, WindArcher.STORM, NightWalker.DARKNESS,
               ThunderBreaker.LIGHTNING -> SummonMovementType.FOLLOW;
         default -> null;
      };
   }

   public Integer getBerserk() {
      return berserk;
   }

   public Integer getBooster() {
      return booster;
   }

   public Byte getMhpR() {
      return mhpR;
   }

   public Byte getMmpR() {
      return mmpR;
   }

   public Short getSpeed() {
      return speed;
   }

   public Short getWeaponAttack() {
      return weaponAttack;
   }

   public Short getMagicAttack() {
      return magicAttack;
   }

   public Short getWeaponDefense() {
      return weaponDefense;
   }

   public Short getMagicDefense() {
      return magicDefense;
   }

   public Short getAccuracy() {
      return accuracy;
   }

   public Short getAvoidability() {
      return avoidability;
   }

   public Short getJump() {
      return jump;
   }

   public Integer getX() {
      return x;
   }

   public Integer getY() {
      return y;
   }

   public Integer getDamage() {
      return damage;
   }

   public Integer getGhost() {
      return ghost;
   }

   public Integer getMorphId() {
      return morphId;
   }

   public boolean isMorph() {
      return morphId > 0;
   }

   public Double getProp() {
      return prop;
   }
}
