package com.atlas.sis.rest.processor;

import java.util.List;
import java.util.stream.Collectors;

import com.app.common.builder.RecordBuilder;
import com.atlas.sis.model.CardItemUpStats;
import com.atlas.sis.model.Skill;
import com.atlas.sis.model.StatEffect;
import com.atlas.sis.rest.attribute.AreaAttributes;
import com.atlas.sis.rest.attribute.BuffStatAmountAttributes;
import com.atlas.sis.rest.attribute.MonsterStatusAttributes;
import com.atlas.sis.rest.attribute.SkillAttributes;
import com.atlas.sis.rest.builder.CardItemUpStatsAttributesBuilder;
import com.atlas.sis.rest.builder.SkillAttributesBuilder;
import com.atlas.sis.rest.builder.StatEffectAttributesBuilder;

import builder.ResultObjectBuilder;

public final class ResultObjectFactory {
   public static ResultObjectBuilder create(Skill data) {
      return new ResultObjectBuilder(SkillAttributes.class, data.id())
            .setAttribute(new SkillAttributesBuilder()
                  .setAction(data.action())
                  .setAnimationTime(data.animationTime())
                  .setElement(data.element().name())
                  .setEffects(data.effects()
                        .stream()
                        .map(ResultObjectFactory::create)
                        .map(RecordBuilder::build)
                        .collect(Collectors.toList())
                  )
            );
   }

   protected static StatEffectAttributesBuilder create(StatEffect effect) {
      return new StatEffectAttributesBuilder()
            .setWeaponAttack(effect.weaponAttack())
            .setMagicAttack(effect.magicAttack())
            .setWeaponDefense(effect.weaponDefense())
            .setMagicDefense(effect.magicDefense())
            .setAccuracy(effect.accuracy())
            .setAvoidability(effect.avoidability())
            .setSpeed(effect.speed())
            .setJump(effect.jump())
            .setHp(effect.hp())
            .setMp(effect.mp())
            .setHpR(effect.hpR())
            .setMpR(effect.mpR())
            .setMhpRRate(effect.mhpRRate())
            .setMmpRRate(effect.mmpRRate())
            .setMobSkill(effect.mobSkill())
            .setMobSkillLevel(effect.mobSkillLevel())
            .setMhpR(effect.mhpR())
            .setMmpR(effect.mmpR())
            .setMpCon(effect.mpCon())
            .setHpCon(effect.hpCon())
            .setDuration(effect.duration())
            .setTarget(effect.target())
            .setBarrier(effect.barrier())
            .setMob(effect.mob())
            .setOverTime(effect.overTime())
            .setRepeatEffect(effect.repeatEffect())
            .setMoveTo(effect.moveTo())
            .setCp(effect.cp())
            .setNuffSkill(effect.nuffSkill())
            .setSkill(effect.skill())
            .setX(effect.x())
            .setY(effect.y())
            .setMobCount(effect.mobCount())
            .setMoneyCon(effect.moneyCon())
            .setCooldown(effect.cooldown())
            .setMorphId(effect.morphId())
            .setGhost(effect.ghost())
            .setFatigue(effect.fatigue())
            .setBerserk(effect.berserk())
            .setBooster(effect.booster())
            .setProp(effect.prop())
            .setItemCon(effect.itemCon())
            .setItemConNo(effect.itemConNo())
            .setDamage(effect.damage())
            .setAttackCount(effect.attackCount())
            .setFixDamage(effect.fixDamage())
            .setLt(effect.lt())
            .setRb(effect.rb())
            .setBulletCount(effect.bulletCount())
            .setBulletConsume(effect.bulletConsume())
            .setMapProtection(effect.mapProtection())
            .setCureAbnormalStatuses(getCureAbnormalStatuses(effect))
            .setStatups(getStatups(effect))
            .setMonsterStatus(getMonsterStatus(effect))
            .setCardStats(
                  effect.cardStats() == null ? null :
                        new CardItemUpStatsAttributesBuilder()
                              .setItemCode(effect.cardStats().itemCode())
                              .setProbability(effect.cardStats().probability())
                              .setInParty(effect.cardStats().inParty())
                              .setAreas(getAreas(effect.cardStats()))
                              .build()
            );
   }

   protected static List<AreaAttributes> getAreas(CardItemUpStats cardItemUpStats) {
      return cardItemUpStats.areas()
            .stream()
            .map(area -> new AreaAttributes(area.start(), area.end()))
            .collect(Collectors.toList());
   }

   protected static List<MonsterStatusAttributes> getMonsterStatus(StatEffect effect) {
      return effect.monsterStatus().entrySet()
            .stream()
            .map(entry -> new MonsterStatusAttributes(entry.getKey().name(), entry.getValue()))
            .collect(Collectors.toList());
   }

   protected static List<BuffStatAmountAttributes> getStatups(StatEffect effect) {
      return effect.statups()
            .stream()
            .map(buffStat -> new BuffStatAmountAttributes(buffStat.buffStat().name(), buffStat.value()))
            .collect(Collectors.toList());
   }

   protected static List<String> getCureAbnormalStatuses(StatEffect effect) {
      return effect.cureAbnormalStatuses()
            .stream()
            .map(Enum::name)
            .collect(Collectors.toList());
   }
}
