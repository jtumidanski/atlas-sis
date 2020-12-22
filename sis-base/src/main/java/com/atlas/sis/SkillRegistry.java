package com.atlas.sis;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.atlas.shared.wz.MapleData;
import com.atlas.shared.wz.MapleDataEntry;
import com.atlas.shared.wz.MapleDataProvider;
import com.atlas.shared.wz.MapleDataProviderFactory;
import com.atlas.shared.wz.MapleDataTool;
import com.atlas.sis.builder.SkillBuilder;
import com.atlas.sis.builder.StatEffectBuilder;
import com.atlas.sis.model.AbnormalStatus;
import com.atlas.sis.model.Area;
import com.atlas.sis.model.BuffStat;
import com.atlas.sis.model.BuffStatAmount;
import com.atlas.sis.model.CardItemUpStats;
import com.atlas.sis.model.Element;
import com.atlas.sis.model.MonsterStatus;
import com.atlas.sis.model.Skill;
import com.atlas.sis.model.StatEffect;
import com.atlas.sis.skill.Aran;
import com.atlas.sis.skill.Archer;
import com.atlas.sis.skill.Assassin;
import com.atlas.sis.skill.Bandit;
import com.atlas.sis.skill.Beginner;
import com.atlas.sis.skill.Bishop;
import com.atlas.sis.skill.BlazeWizard;
import com.atlas.sis.skill.BowMaster;
import com.atlas.sis.skill.Brawler;
import com.atlas.sis.skill.Buccaneer;
import com.atlas.sis.skill.ChiefBandit;
import com.atlas.sis.skill.Cleric;
import com.atlas.sis.skill.Corsair;
import com.atlas.sis.skill.Crossbowman;
import com.atlas.sis.skill.Crusader;
import com.atlas.sis.skill.DarkKnight;
import com.atlas.sis.skill.DawnWarrior;
import com.atlas.sis.skill.DragonKnight;
import com.atlas.sis.skill.Evan;
import com.atlas.sis.skill.FPWizard;
import com.atlas.sis.skill.Fighter;
import com.atlas.sis.skill.FirePoisonArchMage;
import com.atlas.sis.skill.FirePoisonMagician;
import com.atlas.sis.skill.GM;
import com.atlas.sis.skill.Gunslinger;
import com.atlas.sis.skill.Hermit;
import com.atlas.sis.skill.Hero;
import com.atlas.sis.skill.Hunter;
import com.atlas.sis.skill.ILWizard;
import com.atlas.sis.skill.IceLighteningArchMagician;
import com.atlas.sis.skill.IceLighteningMagician;
import com.atlas.sis.skill.Legend;
import com.atlas.sis.skill.Magician;
import com.atlas.sis.skill.Marauder;
import com.atlas.sis.skill.Marksman;
import com.atlas.sis.skill.NightLord;
import com.atlas.sis.skill.NightWalker;
import com.atlas.sis.skill.Noblesse;
import com.atlas.sis.skill.Outlaw;
import com.atlas.sis.skill.Page;
import com.atlas.sis.skill.Paladin;
import com.atlas.sis.skill.Pirate;
import com.atlas.sis.skill.Priest;
import com.atlas.sis.skill.Ranger;
import com.atlas.sis.skill.Rogue;
import com.atlas.sis.skill.Shadower;
import com.atlas.sis.skill.Sniper;
import com.atlas.sis.skill.Spearman;
import com.atlas.sis.skill.SuperGM;
import com.atlas.sis.skill.ThunderBreaker;
import com.atlas.sis.skill.Warrior;
import com.atlas.sis.skill.WhiteKnight;
import com.atlas.sis.skill.WindArcher;
import com.atlas.sis.util.StatEffectUtil;

public class SkillRegistry {
   private static final Object lock = new Object();

   private static volatile SkillRegistry instance;

   private final Map<Integer, Skill> skills;

   public static SkillRegistry getInstance() {
      SkillRegistry result = instance;
      if (result == null) {
         synchronized (lock) {
            result = instance;
            if (result == null) {
               result = new SkillRegistry();
               instance = result;
            }
         }
      }
      return result;
   }

   private SkillRegistry() {
      MapleDataProvider datasource = MapleDataProviderFactory.getDataProvider(new File("/service/wz/Skill.wz"));

      skills = datasource.root()
            .files().stream()
            .map(MapleDataEntry::name)
            .filter(name -> name.length() <= 8)
            .map(datasource::data)
            .flatMap(Optional::stream)
            .flatMap(data -> StreamSupport.stream(data.spliterator(), false))
            .filter(data -> data.name().equals("skill"))
            .flatMap(data -> StreamSupport.stream(data.spliterator(), false))
            .filter(Objects::nonNull)
            .map(this::loadFromData)
            .collect(Collectors.toMap(Skill::id, skill -> skill));
   }

   protected boolean noHit(MapleData data) {
      return data.childByPath("hit").isEmpty();
   }

   protected boolean noBall(MapleData data) {
      return data.childByPath("ball").isEmpty();
   }

   protected boolean hasEffect(MapleData data) {
      return data.childByPath("effect").isPresent();
   }

   protected boolean getBuff(MapleData data) {
      boolean isBuff = hasEffect(data) && noHit(data) && noBall(data);
      if (isBuff) {
         return true;
      }

      return getAction(data)
            .flatMap(actionData -> MapleDataTool.getString("0", actionData))
            .map(action -> action.equals("alert2"))
            .orElse(false);
   }

   protected Skill loadFromData(MapleData data) {
      int id = Integer.parseInt(data.name());
      SkillBuilder builder = new SkillBuilder(id)
            .setElement(readElement(data));

      boolean isBuff = false;
      Optional<Integer> skillType = MapleDataTool.getInteger("skillType", data);
      if (skillType.isPresent()) {
         if (skillType.get() == 2) {
            isBuff = true;
         }
      } else {
         builder.setAction(hasAnyAction(data));
         isBuff = getBuff(data);

         isBuff = switch (id) {
            case Hero.RUSH, Paladin.RUSH, DarkKnight.RUSH, DragonKnight.SACRIFICE, FirePoisonMagician.EXPLOSION,
                  FirePoisonMagician.POISON_MIST, Cleric.HEAL, Ranger.MORTAL_BLOW, Sniper.MORTAL_BLOW, Assassin.DRAIN,
                  Hermit.SHADOW_WEB, Bandit.STEAL, Shadower.SMOKE_SCREEN, SuperGM.HEAL_PLUS_DISPEL, Hero.MONSTER_MAGNET,
                  Paladin.MONSTER_MAGNET, DarkKnight.MONSTER_MAGNET, Evan.ICE_BREATH, Evan.FIRE_BREATH, Gunslinger.RECOIL_SHOT,
                  Marauder.ENERGY_DRAIN, BlazeWizard.FLAME_GEAR, NightWalker.SHADOW_WEB, NightWalker.POISON_BOMB,
                  NightWalker.VAMPIRE, ChiefBandit.CHAKRA, Aran.COMBAT_STEP, Evan.RECOVERY_AURA -> false;
            case Beginner.RECOVERY, Beginner.NIMBLE_FEET, Beginner.MONSTER_RIDER, Beginner.ECHO_OF_HERO, Beginner.MAP_CHAIR,
                  Warrior.IRON_BODY, Fighter.AXE_BOOSTER, Fighter.POWER_GUARD, Fighter.RAGE, Fighter.SWORD_BOOSTER,
                  Crusader.ARMOR_CRASH, Crusader.COMBO, Hero.ENRAGE, Hero.HEROS_WILL, Hero.MAPLE_WARRIOR, Hero.STANCE,
                  Page.BW_BOOSTER, Page.POWER_GUARD, Page.SWORD_BOOSTER, Page.THREATEN, WhiteKnight.BW_FIRE_CHARGE,
                  WhiteKnight.BW_ICE_CHARGE, WhiteKnight.BW_LIT_CHARGE, WhiteKnight.MAGIC_CRASH, WhiteKnight.SWORD_FIRE_CHARGE,
                  WhiteKnight.SWORD_ICE_CHARGE, WhiteKnight.SWORD_LIT_CHARGE, Paladin.BW_HOLY_CHARGE, Paladin.HEROS_WILL,
                  Paladin.MAPLE_WARRIOR, Paladin.STANCE, Paladin.SWORD_HOLY_CHARGE, Spearman.HYPER_BODY, Spearman.IRON_WILL,
                  Spearman.POLEARM_BOOSTER, Spearman.SPEAR_BOOSTER, DragonKnight.DRAGON_BLOOD, DragonKnight.POWER_CRASH,
                  DarkKnight.AURA_OF_BEHOLDER, DarkKnight.BEHOLDER, DarkKnight.HEROS_WILL, DarkKnight.HEX_OF_BEHOLDER,
                  DarkKnight.MAPLE_WARRIOR, DarkKnight.STANCE, Magician.MAGIC_GUARD, Magician.MAGIC_ARMOR, FPWizard.MEDITATION,
                  FPWizard.SLOW, FirePoisonMagician.SEAL, FirePoisonMagician.SPELL_BOOSTER, FirePoisonArchMage.HEROS_WILL,
                  FirePoisonArchMage.INFINITY, FirePoisonArchMage.MANA_REFLECTION, FirePoisonArchMage.MAPLE_WARRIOR,
                  ILWizard.MEDITATION, IceLighteningMagician.SEAL, ILWizard.SLOW, IceLighteningMagician.SPELL_BOOSTER,
                  IceLighteningArchMagician.HEROS_WILL, IceLighteningArchMagician.INFINITY,
                  IceLighteningArchMagician.MANA_REFLECTION, IceLighteningArchMagician.MAPLE_WARRIOR, Cleric.INVINCIBLE,
                  Cleric.BLESS, Priest.DISPEL, Priest.DOOM, Priest.HOLY_SYMBOL, Priest.MYSTIC_DOOR, Bishop.HEROS_WILL,
                  Bishop.HOLY_SHIELD, Bishop.INFINITY, Bishop.MANA_REFLECTION, Bishop.MAPLE_WARRIOR, Archer.FOCUS,
                  Hunter.BOW_BOOSTER, Hunter.SOUL_ARROW, Ranger.PUPPET, BowMaster.CONCENTRATE, BowMaster.HEROS_WILL,
                  BowMaster.MAPLE_WARRIOR, BowMaster.SHARP_EYES, Crossbowman.CROSSBOW_BOOSTER, Crossbowman.SOUL_ARROW,
                  Sniper.PUPPET, Marksman.BLIND, Marksman.HEROS_WILL, Marksman.MAPLE_WARRIOR, Marksman.SHARP_EYES,
                  Rogue.DARK_SIGHT, Assassin.CLAW_BOOSTER, Assassin.HASTE, Hermit.MESO_UP, Hermit.SHADOW_PARTNER,
                  NightLord.HEROS_WILL, NightLord.MAPLE_WARRIOR, NightLord.NINJA_AMBUSH, NightLord.SHADOW_STARS,
                  Bandit.DAGGER_BOOSTER, Bandit.HASTE, ChiefBandit.MESO_GUARD, ChiefBandit.PICKPOCKET, Shadower.HEROS_WILL,
                  Shadower.MAPLE_WARRIOR, Shadower.NINJA_AMBUSH, Pirate.DASH, Marauder.TRANSFORMATION,
                  Buccaneer.SUPER_TRANSFORMATION, Corsair.BATTLE_SHIP, GM.HIDE, SuperGM.HASTE, SuperGM.HOLY_SYMBOL, SuperGM.BLESS,
                  SuperGM.HIDE, SuperGM.HYPER_BODY, Noblesse.BLESSING_OF_THE_FAIRY, Noblesse.ECHO_OF_HERO, Noblesse.MONSTER_RIDER,
                  Noblesse.NIMBLE_FEET, Noblesse.RECOVERY, Noblesse.MAP_CHAIR, DawnWarrior.COMBO, DawnWarrior.FINAL_ATTACK,
                  DawnWarrior.IRON_BODY, DawnWarrior.RAGE, DawnWarrior.SOUL, DawnWarrior.SOUL_CHARGE, DawnWarrior.SWORD_BOOSTER,
                  BlazeWizard.ELEMENTAL_RESET, BlazeWizard.FLAME, BlazeWizard.IFRIT, BlazeWizard.MAGIC_ARMOR,
                  BlazeWizard.MAGIC_GUARD, BlazeWizard.MEDITATION, BlazeWizard.SEAL, BlazeWizard.SLOW, BlazeWizard.SPELL_BOOSTER,
                  WindArcher.BOW_BOOSTER, WindArcher.EAGLE_EYE, WindArcher.FINAL_ATTACK, WindArcher.FOCUS, WindArcher.PUPPET,
                  WindArcher.SOUL_ARROW, WindArcher.STORM, WindArcher.WIND_WALK, NightWalker.CLAW_BOOSTER, NightWalker.DARKNESS,
                  NightWalker.DARK_SIGHT, NightWalker.HASTE, NightWalker.SHADOW_PARTNER, ThunderBreaker.DASH,
                  ThunderBreaker.ENERGY_CHARGE, ThunderBreaker.ENERGY_DRAIN, ThunderBreaker.KNUCKLER_BOOSTER,
                  ThunderBreaker.LIGHTNING, ThunderBreaker.SPARK, ThunderBreaker.LIGHTNING_CHARGE, ThunderBreaker.SPEED_INFUSION,
                  ThunderBreaker.TRANSFORMATION, Legend.BLESSING_OF_THE_FAIRY, Legend.AGILE_BODY, Legend.ECHO_OF_HERO,
                  Legend.RECOVERY, Legend.MONSTER_RIDER, Legend.MAP_CHAIR, Aran.MAPLE_WARRIOR, Aran.HEROS_WILL,
                  Aran.POLE_ARM_BOOSTER, Aran.COMBO_DRAIN, Aran.SNOW_CHARGE, Aran.BODY_PRESSURE, Aran.SMART_KNOCK_BACK,
                  Aran.COMBO_BARRIER, Aran.COMBO_ABILITY, Evan.BLESSING_OF_THE_FAIRY, Evan.RECOVERY, Evan.NIMBLE_FEET,
                  Evan.HEROS_WILL, Evan.ECHO_OF_HERO, Evan.MAGIC_BOOSTER, Evan.MAGIC_GUARD, Evan.ELEMENTAL_RESET,
                  Evan.MAPLE_WARRIOR, Evan.MAGIC_RESISTANCE, Evan.MAGIC_SHIELD, Evan.SLOW -> true;
            default -> isBuff;
         };
      }

      boolean finalIsBuff = isBuff;
      data.childByPath("level").stream()
            .flatMap(child -> StreamSupport.stream(child.spliterator(), false))
            .map(level -> loadSkillEffect(level, id, finalIsBuff))
            .forEach(builder::addLevelEffect);

      int animationTime = data.childByPath("effect").stream()
            .flatMap(child -> StreamSupport.stream(child.spliterator(), false))
            .map(entry -> MapleDataTool.getIntConvert("delay", entry))
            .flatMap(Optional::stream)
            .mapToInt(i -> i)
            .sum();
      builder.setAnimationTime(animationTime);

      return builder.build();
   }

   protected StatEffect loadSkillEffect(MapleData data, int skillId, boolean overtime) {
      return loadSkillEffect(data, skillId, true, overtime);
   }

   protected StatEffect loadSkillEffect(MapleData data, int skillId, boolean skill, boolean overtime) {
      StatEffectBuilder builder = new StatEffectBuilder(skillId)
            .setDuration(MapleDataTool.getIntConvert("time", data).orElse(-1))
            .setHp(MapleDataTool.getInteger("hp", data).orElse(0).shortValue())
            .setHpR(MapleDataTool.getInteger("hpR", data).orElse(0) / 100.0)
            .setMp(MapleDataTool.getInteger("mp", data).orElse(0).shortValue())
            .setMpR(MapleDataTool.getInteger("mpR", data).orElse(0) / 100.0)
            .setHpCon(MapleDataTool.getInteger("hpCon", data).orElse(0).shortValue())
            .setMpCon(MapleDataTool.getInteger("mpCon", data).orElse(0).shortValue())
            .setProp(MapleDataTool.getInteger("prop", data).orElse(100) / 100.0)
            .setCp(MapleDataTool.getInteger("cp", data).orElse(0))
            .setCureAbnormalStatuses(getAbnormalStatusCures(data))
            .setNuffSkill(MapleDataTool.getInteger("nuffSkill", data).orElse(0))
            .setMobCount(MapleDataTool.getInteger("mobCount", data).orElse(1))
            .setCooldown(MapleDataTool.getInteger("cooltime", data).orElse(0))
            .setMorphId(MapleDataTool.getInteger("morph", data).orElse(0))
            .setGhost(MapleDataTool.getInteger("ghost", data).orElse(0))
            .setFatigue(MapleDataTool.getInteger("incFatigue", data).orElse(0))
            .setRepeatEffect(MapleDataTool.getInteger("repeatEffect", data).orElse(0) > 0);

      data.childByPath("0")
            .filter(child -> child.children().size() > 0)
            .ifPresentOrElse(child -> setMobInformation(builder, child), () -> defaultMobInformation(builder));
      data.childByPath("mob")
            .filter(child -> child.children() != null)
            .filter(child -> child.children().size() > 0)
            .ifPresent(child -> builder.setMob(MapleDataTool.getInteger("mob", child).orElse(0)));

      builder.setSourceId(skillId);
      builder.setSkill(skill);

      if (builder.isSkill() && builder.getDuration() > -1) {
         builder.setOverTime(true);
      } else {
         // items have their times stored in ms, of course
         builder.setDuration(builder.getDuration() * 1000);
         builder.setOverTime(overtime);
      }

      builder.setWeaponAttack(MapleDataTool.getInteger("pad", data).orElse(0).shortValue())
            .setWeaponDefense(MapleDataTool.getInteger("pdd", data).orElse(0).shortValue())
            .setMagicAttack(MapleDataTool.getInteger("mad", data).orElse(0).shortValue())
            .setMagicDefense(MapleDataTool.getInteger("mdd", data).orElse(0).shortValue())
            .setAccuracy(MapleDataTool.getIntConvert("acc", data).orElse(0).shortValue())
            .setAvoidability(MapleDataTool.getInteger("eva", data).orElse(0).shortValue())
            .setSpeed(MapleDataTool.getInteger("speed", data).orElse(0).shortValue())
            .setJump(MapleDataTool.getInteger("jump", data).orElse(0).shortValue())
            .setBarrier(MapleDataTool.getInteger("barrier", data).orElse(0));

      List<BuffStatAmount> statups = new ArrayList<>();
      produceBuffStatAmount(BuffStat.AURA, builder.getBarrier()).ifPresent(statups::add);

      builder.setMapProtection(mapProtection(skillId));
      produceBuffStatAmount(BuffStat.MAP_PROTECTION, (int) builder.getMapProtection()).ifPresent(statups::add);

      if (builder.isOverTime() && builder.getSummonMovementType() == null) {
         if (!skill) {
            if (isPyramidBuff(skillId)) {
               builder.setBerserk(MapleDataTool.getInteger("berserk", data).orElse(0));
               builder.setBooster(MapleDataTool.getInteger("booster", data).orElse(0));
               produceBuffStatAmount(BuffStat.BERSERK, builder.getBerserk()).ifPresent(statups::add);
               produceBuffStatAmount(BuffStat.BOOSTER, builder.getBooster()).ifPresent(statups::add);
            } else if (StatEffectUtil.isDojoBuff(skillId) || StatEffectUtil.isHpMpRecovery(skillId)) {
               builder.setMhpR(MapleDataTool.getInteger("mhpR", data).orElse(0).byteValue());
               builder.setMhpRRate((short) (MapleDataTool.getInteger("mhpRRate", data).orElse(0) * 100));
               builder.setMmpR(MapleDataTool.getInteger("mmpR", data).orElse(0).byteValue());
               builder.setMmpRRate((short) (MapleDataTool.getInteger("mmpRRate", data).orElse(0) * 100));
               produceBuffStatAmount(BuffStat.HP_RECOVERY, builder.getMhpR().intValue()).ifPresent(statups::add);
               produceBuffStatAmount(BuffStat.MP_RECOVERY, builder.getMmpR().intValue()).ifPresent(statups::add);
            } else if (StatEffectUtil.isRateCoupon(skillId)) {
               int expR = MapleDataTool.getInteger("expR", data).orElse(0);
               switch (expR) {
                  case 1 -> produceBuffStatAmount(BuffStat.COUPON_EXP1, 1).ifPresent(statups::add);
                  case 2 -> produceBuffStatAmount(BuffStat.COUPON_EXP2, 1).ifPresent(statups::add);
                  case 3 -> produceBuffStatAmount(BuffStat.COUPON_EXP3, 1).ifPresent(statups::add);
                  case 4 -> produceBuffStatAmount(BuffStat.COUPON_EXP4, 1).ifPresent(statups::add);
               }

               int dropR = MapleDataTool.getInteger("drpR", data).orElse(0);
               switch (dropR) {
                  case 1 -> produceBuffStatAmount(BuffStat.COUPON_DRP1, 1).ifPresent(statups::add);
                  case 2 -> produceBuffStatAmount(BuffStat.COUPON_DRP2, 1).ifPresent(statups::add);
                  case 3 -> produceBuffStatAmount(BuffStat.COUPON_DRP3, 1).ifPresent(statups::add);
               }
            } else if (StatEffectUtil.isMonsterCard(skillId)) {
               List<Area> areas;
               boolean inParty = false;
               Optional<MapleData> con = data.childByPath("con");
               if (con.isPresent()) {
                  areas = new ArrayList<>(3);

                  for (MapleData conData : con.get().children()) {
                     int type = MapleDataTool.getInteger("type", conData).orElse(-1);
                     if (type == 0) {
                        int startMap = MapleDataTool.getInteger("sMap", conData).orElse(0);
                        int endMap = MapleDataTool.getInteger("eMap", conData).orElse(0);
                        areas.add(new Area(startMap, endMap));
                     } else if (type == 2) {
                        inParty = true;
                     }
                  }
               } else {
                  areas = Collections.emptyList();
               }

               int prob = 0;

               if (MapleDataTool.getInteger("mesoupbyitem", data).orElse(0) != 0) {
                  produceBuffStatAmount(BuffStat.MESO_UP_BY_ITEM, 4).ifPresent(statups::add);
                  prob = MapleDataTool.getInteger("prob", data).orElse(1);
               }

               int itemUpType = MapleDataTool.getInteger("itemupbyitem", data).orElse(0);
               int itemUpCode = Integer.MAX_VALUE;
               if (itemUpType != 0) {
                  produceBuffStatAmount(BuffStat.ITEM_UP_BY_ITEM, 4).ifPresent(statups::add);
                  prob = MapleDataTool.getInteger("prob", data).orElse(1);

                  switch (itemUpType) {
                     case 2 -> itemUpCode = MapleDataTool.getInteger("itemCode", data).orElse(1);
                     case 3 -> itemUpCode = MapleDataTool.getInteger("itemRange", data).orElse(1);    // 3 digits
                  }
               }

               if (MapleDataTool.getInteger("respectPimmune", data).orElse(0) != 0) {
                  produceBuffStatAmount(BuffStat.RESPECT_PLAYER_IMMUNE, 4).ifPresent(statups::add);
               }

               if (MapleDataTool.getInteger("respectMimmune", data).orElse(0) != 0) {
                  produceBuffStatAmount(BuffStat.RESPECT_MONSTER_IMMUNE, 4).ifPresent(statups::add);
               }

               if (MapleDataTool.getString("defenseAtt", data).isPresent()) {
                  produceBuffStatAmount(BuffStat.DEFENSE_ATT, 4).ifPresent(statups::add);
               }

               if (MapleDataTool.getString("defenseState", data).isPresent()) {
                  produceBuffStatAmount(BuffStat.DEFENSE_STATE, 4).ifPresent(statups::add);
               }

               int thaw = MapleDataTool.getInteger("thaw", data).orElse(0);
               if (thaw != 0) {
                  produceBuffStatAmount(BuffStat.MAP_PROTECTION, thaw > 0 ? 1 : 2).ifPresent(statups::add);
               }

               builder.setCardStats(new CardItemUpStats(itemUpCode, prob, areas, inParty));
            } else if (StatEffectUtil.isExpIncrease(skillId)) {
               produceBuffStatAmount(BuffStat.EXP_INCREASE, MapleDataTool.getInteger("expinc", data).orElse(0))
                     .ifPresent(statups::add);
            }
         } else {
            if (StatEffectUtil.isMapChair(skillId)) {
               produceBuffStatAmount(BuffStat.MAP_CHAIR, 1).ifPresent(statups::add);
            } else if ((skillId == Beginner.NIMBLE_FEET || skillId == Noblesse.NIMBLE_FEET || skillId == Evan.NIMBLE_FEET
                  || skillId == Legend.AGILE_BODY) && ConfigurationRegistry.getInstance().getConfiguration().useUltraNimbleFeet) {
               builder.setJump((short) (builder.getSpeed() * 4));
               builder.setSpeed((short) (builder.getSpeed() * 15));
            }
         }

         produceBuffStatAmount(BuffStat.WEAPON_ATTACK, (int) builder.getWeaponAttack()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.WEAPON_DEFENSE, (int) builder.getWeaponDefense()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.MAGIC_ATTACK, (int) builder.getMagicAttack()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.MAGIC_DEFENSE, (int) builder.getMagicDefense()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.ACC, (int) builder.getAccuracy()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.AVOID, (int) builder.getAvoidability()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.SPEED, (int) builder.getSpeed()).ifPresent(statups::add);
         produceBuffStatAmount(BuffStat.JUMP, (int) builder.getJump()).ifPresent(statups::add);
      }

      data.childByPath("lt")
            .ifPresent(ltd -> {
               builder.setLt(ltd.data().map(lt -> (Point) lt).orElseThrow());
               builder.setRb(data.childByPath("rb").flatMap(MapleData::data).map(rb -> (Point) rb).orElseThrow());

               if (ConfigurationRegistry.getInstance().getConfiguration().useMaxRangeEchoOfHero &&
                     (skillId == Beginner.ECHO_OF_HERO || skillId == Noblesse.ECHO_OF_HERO || skillId == Legend.ECHO_OF_HERO
                           || skillId == Evan.ECHO_OF_HERO)) {
                  builder.setLt(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE));
                  builder.setRb(new Point(Integer.MAX_VALUE, Integer.MAX_VALUE));
               }
            });

      int x = MapleDataTool.getInteger("x", data).orElse(0);

      if ((skillId == Beginner.RECOVERY || skillId == Noblesse.RECOVERY || skillId == Evan.RECOVERY
            || skillId == Legend.RECOVERY) && ConfigurationRegistry.getInstance().getConfiguration().useUltraRecovery) {
         x *= 10;
      }
      builder.setX(x)
            .setY(MapleDataTool.getInteger("y", data).orElse(0))
            .setDamage(MapleDataTool.getIntConvert("damage", data).orElse(100))
            .setFixDamage(MapleDataTool.getIntConvert("fixdamage", data).orElse(-1))
            .setAttackCount(MapleDataTool.getIntConvert("attackCount", data).orElse(1))
            .setBulletCount(MapleDataTool.getIntConvert("bulletCount", data).orElse(1).shortValue())
            .setBulletConsume(MapleDataTool.getIntConvert("bulletConsume", data).orElse(0).shortValue())
            .setMoneyCon(MapleDataTool.getIntConvert("moneyCon", data).orElse(0))
            .setItemCon(MapleDataTool.getInteger("itemCon", data).orElse(0))
            .setItemConNo(MapleDataTool.getInteger("itemConNo", data).orElse(0))
            .setMoveTo(MapleDataTool.getInteger("moveTo", data).orElse(-1));

      Map<MonsterStatus, Integer> monsterStatus = new HashMap<>();
      if (skill) {
         switch (skillId) {
            // BEGINNER
            case Beginner.RECOVERY:
            case Noblesse.RECOVERY:
            case Legend.RECOVERY:
            case Evan.RECOVERY:
               statups.add(new BuffStatAmount(BuffStat.RECOVERY, x));
               break;
            case Beginner.ECHO_OF_HERO:
            case Noblesse.ECHO_OF_HERO:
            case Legend.ECHO_OF_HERO:
            case Evan.ECHO_OF_HERO:
               statups.add(new BuffStatAmount(BuffStat.ECHO_OF_HERO, builder.getX()));
               break;
            case Beginner.MONSTER_RIDER:
            case Noblesse.MONSTER_RIDER:
            case Legend.MONSTER_RIDER:
            case Corsair.BATTLE_SHIP:
            case Beginner.SPACESHIP:
            case Noblesse.SPACESHIP:
            case Beginner.YETI_MOUNT1:
            case Beginner.YETI_MOUNT2:
            case Noblesse.YETI_MOUNT1:
            case Noblesse.YETI_MOUNT2:
            case Legend.YETI_MOUNT1:
            case Legend.YETI_MOUNT2:
            case Beginner.WITCH_BROOMSTICK:
            case Noblesse.WITCH_BROOMSTICK:
            case Legend.WITCH_BROOMSTICK:
            case Beginner.BALROG_MOUNT:
            case Noblesse.BALROG_MOUNT:
            case Legend.BALROG_MOUNT:
               statups.add(new BuffStatAmount(BuffStat.MONSTER_RIDING, skillId));
               break;
            case Beginner.INVINCIBLE_BARRIER:
            case Noblesse.INVINCIBLE_BARRIER:
            case Legend.INVISIBLE_BARRIER:
            case Evan.INVINCIBLE_BARRIER:
               statups.add(new BuffStatAmount(BuffStat.DIVINE_BODY, 1));
               break;
            case Fighter.POWER_GUARD:
            case Page.POWER_GUARD:
               statups.add(new BuffStatAmount(BuffStat.POWER_GUARD, x));
               break;
            case Spearman.HYPER_BODY:
            case GM.HYPER_BODY:
            case SuperGM.HYPER_BODY:
               statups.add(new BuffStatAmount(BuffStat.HYPER_BODY_HP, x));
               statups.add(new BuffStatAmount(BuffStat.HYPER_BODY_MP, builder.getY()));
               break;
            case Crusader.COMBO:
            case DawnWarrior.COMBO:
               statups.add(new BuffStatAmount(BuffStat.COMBO, 1));
               break;
            case WhiteKnight.BW_FIRE_CHARGE:
            case WhiteKnight.BW_ICE_CHARGE:
            case WhiteKnight.BW_LIT_CHARGE:
            case WhiteKnight.SWORD_FIRE_CHARGE:
            case WhiteKnight.SWORD_ICE_CHARGE:
            case WhiteKnight.SWORD_LIT_CHARGE:
            case Paladin.BW_HOLY_CHARGE:
            case Paladin.SWORD_HOLY_CHARGE:
            case DawnWarrior.SOUL_CHARGE:
            case ThunderBreaker.LIGHTNING_CHARGE:
               statups.add(new BuffStatAmount(BuffStat.WK_CHARGE, x));
               break;
            case DragonKnight.DRAGON_BLOOD:
               statups.add(new BuffStatAmount(BuffStat.DRAGON_BLOOD, builder.getX()));
               break;
            case Hero.STANCE:
            case Paladin.STANCE:
            case DarkKnight.STANCE:
            case Aran.FREEZE_STANDING:
               statups.add(new BuffStatAmount(BuffStat.STANCE, Double.valueOf(Math.floor(builder.getProp() * 100.0)).intValue()));
               break;
            case DawnWarrior.FINAL_ATTACK:
            case WindArcher.FINAL_ATTACK:
               statups.add(new BuffStatAmount(BuffStat.FINAL_ATTACK, x));
               break;
            // MAGICIAN
            case Magician.MAGIC_GUARD:
            case BlazeWizard.MAGIC_GUARD:
            case Evan.MAGIC_GUARD:
               statups.add(new BuffStatAmount(BuffStat.MAGIC_GUARD, x));
               break;
            case Cleric.INVINCIBLE:
               statups.add(new BuffStatAmount(BuffStat.INVINCIBLE, x));
               break;
            case Priest.HOLY_SYMBOL:
            case SuperGM.HOLY_SYMBOL:
               statups.add(new BuffStatAmount(BuffStat.HOLY_SYMBOL, x));
               break;
            case FirePoisonArchMage.INFINITY:
            case IceLighteningArchMagician.INFINITY:
            case Bishop.INFINITY:
               statups.add(new BuffStatAmount(BuffStat.INFINITY, x));
               break;
            case FirePoisonArchMage.MANA_REFLECTION:
            case IceLighteningArchMagician.MANA_REFLECTION:
            case Bishop.MANA_REFLECTION:
               statups.add(new BuffStatAmount(BuffStat.MANA_REFLECTION, 1));
               break;
            case Bishop.HOLY_SHIELD:
               statups.add(new BuffStatAmount(BuffStat.HOLY_SHIELD, x));
               break;
            case BlazeWizard.ELEMENTAL_RESET:
            case Evan.ELEMENTAL_RESET:
               statups.add(new BuffStatAmount(BuffStat.ELEMENTAL_RESET, x));
               break;
            case Evan.MAGIC_SHIELD:
               statups.add(new BuffStatAmount(BuffStat.MAGIC_SHIELD, x));
               break;
            case Evan.MAGIC_RESISTANCE:
               statups.add(new BuffStatAmount(BuffStat.MAGIC_RESISTANCE, x));
               break;
            case Evan.SLOW:
               statups.add(new BuffStatAmount(BuffStat.SLOW, x));
               // BOWMAN
            case Priest.MYSTIC_DOOR:
            case Hunter.SOUL_ARROW:
            case Crossbowman.SOUL_ARROW:
            case WindArcher.SOUL_ARROW:
               statups.add(new BuffStatAmount(BuffStat.SOUL_ARROW, x));
               break;
            case Ranger.PUPPET:
            case Sniper.PUPPET:
            case WindArcher.PUPPET:
            case Outlaw.OCTOPUS:
            case Corsair.WRATH_OF_THE_OCTOPI:
               statups.add(new BuffStatAmount(BuffStat.PUPPET, 1));
               break;
            case BowMaster.CONCENTRATE:
               statups.add(new BuffStatAmount(BuffStat.CONCENTRATE, x));
               break;
            case BowMaster.HAMSTRING:
               statups.add(new BuffStatAmount(BuffStat.HAMSTRING, x));
               monsterStatus.put(MonsterStatus.SPEED, x);
               break;
            case Marksman.BLIND:
               statups.add(new BuffStatAmount(BuffStat.BLIND, x));
               monsterStatus.put(MonsterStatus.ACC, x);
               break;
            case BowMaster.SHARP_EYES:
            case Marksman.SHARP_EYES:
               statups.add(new BuffStatAmount(BuffStat.SHARP_EYES, builder.getX() << 8 | builder.getY()));
               break;
            case WindArcher.WIND_WALK:
               statups.add(new BuffStatAmount(BuffStat.WIND_WALK, x));
               break;
            case Rogue.DARK_SIGHT:
            case NightWalker.DARK_SIGHT:
               statups.add(new BuffStatAmount(BuffStat.DARK_SIGHT, x));
               break;
            case Hermit.MESO_UP:
               statups.add(new BuffStatAmount(BuffStat.MESOUP, x));
               break;
            case Hermit.SHADOW_PARTNER:
            case NightWalker.SHADOW_PARTNER:
               statups.add(new BuffStatAmount(BuffStat.SHADOW_PARTNER, x));
               break;
            case ChiefBandit.MESO_GUARD:
               statups.add(new BuffStatAmount(BuffStat.MESO_GUARD, x));
               break;
            case ChiefBandit.PICKPOCKET:
               statups.add(new BuffStatAmount(BuffStat.PICKPOCKET, x));
               break;
            case NightLord.SHADOW_STARS:
               statups.add(new BuffStatAmount(BuffStat.SHADOW_CLAW, 0));
               break;
            // PIRATE
            case Pirate.DASH:
            case ThunderBreaker.DASH:
            case Beginner.SPACE_DASH:
            case Noblesse.SPACE_DASH:
               statups.add(new BuffStatAmount(BuffStat.DASH2, builder.getX()));
               statups.add(new BuffStatAmount(BuffStat.DASH, builder.getY()));
               break;
            case Corsair.SPEED_INFUSION:
            case Buccaneer.SPEED_INFUSION:
            case ThunderBreaker.SPEED_INFUSION:
               statups.add(new BuffStatAmount(BuffStat.SPEED_INFUSION, x));
               break;
            case Outlaw.HOMING_BEACON:
            case Corsair.BULLS_EYE:
               statups.add(new BuffStatAmount(BuffStat.HOMING_BEACON, x));
               break;
            case ThunderBreaker.SPARK:
               statups.add(new BuffStatAmount(BuffStat.SPARK, x));
               break;
            // MULTIPLE
            case Aran.POLE_ARM_BOOSTER:
            case Fighter.AXE_BOOSTER:
            case Fighter.SWORD_BOOSTER:
            case Page.BW_BOOSTER:
            case Page.SWORD_BOOSTER:
            case Spearman.POLEARM_BOOSTER:
            case Spearman.SPEAR_BOOSTER:
            case Hunter.BOW_BOOSTER:
            case Crossbowman.CROSSBOW_BOOSTER:
            case Assassin.CLAW_BOOSTER:
            case Bandit.DAGGER_BOOSTER:
            case FirePoisonMagician.SPELL_BOOSTER:
            case IceLighteningMagician.SPELL_BOOSTER:
            case Brawler.KNUCKLER_BOOSTER:
            case Gunslinger.GUN_BOOSTER:
            case DawnWarrior.SWORD_BOOSTER:
            case BlazeWizard.SPELL_BOOSTER:
            case WindArcher.BOW_BOOSTER:
            case NightWalker.CLAW_BOOSTER:
            case ThunderBreaker.KNUCKLER_BOOSTER:
            case Evan.MAGIC_BOOSTER:
            case Beginner.POWER_EXPLOSION:
            case Noblesse.POWER_EXPLOSION:
            case Legend.POWER_EXPLOSION:
               statups.add(new BuffStatAmount(BuffStat.BOOSTER, x));
               break;
            case Hero.MAPLE_WARRIOR:
            case Paladin.MAPLE_WARRIOR:
            case DarkKnight.MAPLE_WARRIOR:
            case FirePoisonArchMage.MAPLE_WARRIOR:
            case IceLighteningArchMagician.MAPLE_WARRIOR:
            case Bishop.MAPLE_WARRIOR:
            case BowMaster.MAPLE_WARRIOR:
            case Marksman.MAPLE_WARRIOR:
            case NightLord.MAPLE_WARRIOR:
            case Shadower.MAPLE_WARRIOR:
            case Corsair.MAPLE_WARRIOR:
            case Buccaneer.MAPLE_WARRIOR:
            case Aran.MAPLE_WARRIOR:
            case Evan.MAPLE_WARRIOR:
               statups.add(new BuffStatAmount(BuffStat.MAPLE_WARRIOR, builder.getX()));
               break;
            // SUMMON
            case Ranger.SILVER_HAWK:
            case Sniper.GOLDEN_EAGLE:
               statups.add(new BuffStatAmount(BuffStat.SUMMON, 1));
               monsterStatus.put(MonsterStatus.STUN, 1);
               break;
            case FirePoisonArchMage.ELQUINES:
            case Marksman.FROST_PREY:
               statups.add(new BuffStatAmount(BuffStat.SUMMON, 1));
               monsterStatus.put(MonsterStatus.FREEZE, 1);
               break;
            case Priest.SUMMON_DRAGON:
            case BowMaster.PHOENIX:
            case IceLighteningArchMagician.IFRIT:
            case Bishop.BAHAMUT:
            case DarkKnight.BEHOLDER:
            case Outlaw.GAVIOTA:
            case DawnWarrior.SOUL:
            case BlazeWizard.FLAME:
            case WindArcher.STORM:
            case NightWalker.DARKNESS:
            case ThunderBreaker.LIGHTNING:
            case BlazeWizard.IFRIT:
               statups.add(new BuffStatAmount(BuffStat.SUMMON, 1));
               break;
            // ----------------------------- MONSTER STATUS ---------------------------------- //
            case Crusader.ARMOR_CRASH:
            case DragonKnight.POWER_CRASH:
            case WhiteKnight.MAGIC_CRASH:
               monsterStatus.put(MonsterStatus.SEAL_SKILL, 1);
               break;
            case Rogue.DISORDER:
            case Page.THREATEN:
               monsterStatus.put(MonsterStatus.WEAPON_ATTACK, builder.getX());
               monsterStatus.put(MonsterStatus.WEAPON_DEFENSE, builder.getY());
               break;
            case Corsair.HYPNOTIZE:
               monsterStatus.put(MonsterStatus.INERT_MOB, 1);
               break;
            case NightLord.NINJA_AMBUSH:
            case Shadower.NINJA_AMBUSH:
               monsterStatus.put(MonsterStatus.NINJA_AMBUSH, builder.getDamage());
               break;
            case DragonKnight.DRAGON_ROAR:
               builder.setHpR(-x / 100.0);
               monsterStatus.put(MonsterStatus.STUN, 1);
               break;
            case Crusader.AXE_COMA:
            case Crusader.SWORD_COMA:
            case Crusader.SHOUT:
            case WhiteKnight.CHARGE_BLOW:
            case Hunter.ARROW_BOMB:
            case ChiefBandit.ASSAULTER:
            case Shadower.BOOMERANG_STEP:
            case Brawler.BACK_SPIN_BLOW:
            case Brawler.DOUBLE_UPPERCUT:
            case Buccaneer.DEMOLITION:
            case Buccaneer.SNATCH:
            case Buccaneer.BARRAGE:
            case Gunslinger.BLANK_SHOT:
            case DawnWarrior.COMA:
            case ThunderBreaker.BARRAGE:
            case Aran.ROLLING_SPIN:
            case Evan.FIRE_BREATH:
            case Evan.BLAZE:
               monsterStatus.put(MonsterStatus.STUN, 1);
               break;
            case NightLord.TAUNT:
            case Shadower.TAUNT:
               monsterStatus.put(MonsterStatus.SHOWDOWN, builder.getX());
               monsterStatus.put(MonsterStatus.MAGIC_DEFENSE, builder.getX());
               monsterStatus.put(MonsterStatus.WEAPON_DEFENSE, builder.getX());
               break;
            case ILWizard.COLD_BEAM:
            case IceLighteningMagician.ICE_STRIKE:
            case IceLighteningArchMagician.BLIZZARD:
            case IceLighteningMagician.ELEMENT_COMPOSITION:
            case Sniper.BLIZZARD:
            case Outlaw.ICE_SPLITTER:
            case FirePoisonArchMage.PARALYZE:
            case Aran.COMBO_TEMPEST:
            case Evan.ICE_BREATH:
               monsterStatus.put(MonsterStatus.FREEZE, 1);
               builder.setDuration(builder.getDuration() * 2); // freezing skills are a little strange
               break;
            case FPWizard.SLOW:
            case ILWizard.SLOW:
            case BlazeWizard.SLOW:
               monsterStatus.put(MonsterStatus.SPEED, builder.getX());
               break;
            case FPWizard.POISON_BREATH:
            case FirePoisonMagician.ELEMENT_COMPOSITION:
               monsterStatus.put(MonsterStatus.POISON, 1);
               break;
            case Priest.DOOM:
               monsterStatus.put(MonsterStatus.DOOM, 1);
               break;
            case IceLighteningMagician.SEAL:
            case FirePoisonMagician.SEAL:
            case BlazeWizard.SEAL:
               monsterStatus.put(MonsterStatus.SEAL, 1);
               break;
            case Hermit.SHADOW_WEB: // shadow web
            case NightWalker.SHADOW_WEB:
               monsterStatus.put(MonsterStatus.SHADOW_WEB, 1);
               break;
            case FirePoisonArchMage.FIRE_DEMON:
            case IceLighteningArchMagician.ICE_DEMON:
               monsterStatus.put(MonsterStatus.POISON, 1);
               monsterStatus.put(MonsterStatus.FREEZE, 1);
               break;
            case Evan.PHANTOM_IMPRINT:
               monsterStatus.put(MonsterStatus.PHANTOM_IMPRINT, x);
            case Aran.COMBO_ABILITY:
               statups.add(new BuffStatAmount(BuffStat.ARAN_COMBO, 100));
               break;
            case Aran.COMBO_BARRIER:
               statups.add(new BuffStatAmount(BuffStat.COMBO_BARRIER, builder.getX()));
               break;
            case Aran.COMBO_DRAIN:
               statups.add(new BuffStatAmount(BuffStat.COMBO_DRAIN, builder.getX()));
               break;
            case Aran.SMART_KNOCK_BACK:
               statups.add(new BuffStatAmount(BuffStat.SMART_KNOCK_BACK, builder.getX()));
               break;
            case Aran.BODY_PRESSURE:
               statups.add(new BuffStatAmount(BuffStat.BODY_PRESSURE, builder.getX()));
               break;
            case Aran.SNOW_CHARGE:
               statups.add(new BuffStatAmount(BuffStat.WK_CHARGE, builder.getDuration()));
               break;
            default:
               break;
         }
      }
      if (builder.isMorph()) {
         statups.add(new BuffStatAmount(BuffStat.MORPH, builder.getMorphId()));
      }
      if (builder.getGhost() > 0 && !skill) {
         statups.add(new BuffStatAmount(BuffStat.GHOST_MORPH, builder.getGhost()));
      }
      builder.setMonsterStatus(monsterStatus);
      builder.setStatups(statups);
      return builder.build();
   }

   protected boolean isPyramidBuff(int sourceId) {
      return sourceId >= 2022585 && sourceId <= 2022617;
   }

   protected byte mapProtection(int sourceId) {
      if (sourceId == 2022001 || sourceId == 2022186) {
         return 1;   //elnath cold
      } else if (sourceId == 2022040) {
         return 2;   //aqua road underwater
      } else {
         return 0;
      }
   }

   protected Optional<BuffStatAmount> produceBuffStatAmount(BuffStat buffStat, Integer val) {
      if (val != 0) {
         return Optional.of(new BuffStatAmount(buffStat, val));
      }
      return Optional.empty();
   }

   protected void setMobInformation(StatEffectBuilder builder, MapleData child) {
      builder.setMobSkill(MapleDataTool.getInteger("mobSkill", child).orElse(0).shortValue());
      builder.setMobSkillLevel(MapleDataTool.getInteger("level", child).orElse(0).shortValue());
      builder.setTarget(MapleDataTool.getInteger("target", child).orElse(0));
   }

   protected void defaultMobInformation(StatEffectBuilder builder) {
      builder.setMobSkill((short) 0);
      builder.setMobSkillLevel((short) 0);
      builder.setTarget(0);
   }

   protected List<AbnormalStatus> getAbnormalStatusCures(MapleData data) {
      List<AbnormalStatus> cure = new ArrayList<>();
      if (MapleDataTool.getInteger("poison", data).orElse(0) > 0) {
         cure.add(AbnormalStatus.POISON);
      }
      if (MapleDataTool.getInteger("seal", data).orElse(0) > 0) {
         cure.add(AbnormalStatus.SEAL);
      }
      if (MapleDataTool.getInteger("darkness", data).orElse(0) > 0) {
         cure.add(AbnormalStatus.DARKNESS);
      }
      if (MapleDataTool.getInteger("weakness", data).orElse(0) > 0) {
         cure.add(AbnormalStatus.WEAKEN);
         cure.add(AbnormalStatus.SLOW);
      }
      if (MapleDataTool.getInteger("curse", data).orElse(0) > 0) {
         cure.add(AbnormalStatus.CURSE);
      }
      return cure;
   }

   protected boolean hasAnyAction(MapleData data) {
      return getAction(data)
            .map(child -> true)
            .or(() -> getActionByPrepareAction(data))
            .orElseGet(() -> getActionByName(data));
   }

   protected Optional<MapleData> getAction(MapleData data) {
      return data.childByPath("action");
   }

   protected Optional<Boolean> getActionByPrepareAction(MapleData data) {
      return data.childByPath("prepare/action")
            .map(child -> true);
   }

   protected boolean getActionByName(MapleData data) {
      int id = Integer.parseInt(data.name());
      return switch (id) {
         case Gunslinger.INVISIBLE_SHOT, Corsair.HYPNOTIZE -> true;
         default -> false;
      };
   }

   protected Element readElement(MapleData data) {
      return MapleDataTool.getString("elemAttr", data)
            .map(elem -> elem.charAt(0))
            .map(Element::getFromChar)
            .orElse(Element.NEUTRAL);
   }

   public Optional<Skill> getSkill(int id) {
      if (!skills.isEmpty()) {
         return Optional.ofNullable(skills.get(id));
      }
      return Optional.empty();
   }
}
