package skill

import (
	"atlas-sis/buff"
	"atlas-sis/configuration"
	"atlas-sis/monster"
	"atlas-sis/wz"
	"atlas-sis/xml"
	"errors"
	"fmt"
	"math"
	"strconv"
)

func Read(skillId uint32) (*Model, error) {
	i, err := findItem(skillId)
	if err != nil {
		return nil, err
	}

	exml, err := xml.Read(i.Path())
	if err != nil {
		return nil, err
	}
	exml, err = exml.ChildByName("skill")
	if err != nil {
		return nil, err
	}
	exml, err = exml.ChildByName(fmt.Sprintf("%07d", skillId))
	if err != nil {
		return nil, err
	}
	return getSkillFromXML(skillId, exml)
}

func findItem(skillId uint32) (*wz.FileEntry, error) {
	path := fmt.Sprintf("%07d", skillId)
	if val, ok := wz.GetFileCache().GetFile(path[:len(path)-4] + ".img.xml"); ok == nil {
		return val, nil
	}
	return nil, errors.New(fmt.Sprintf("skill %d not found", skillId))
}

func getSkillFromXML(skillId uint32, exml *xml.Node) (*Model, error) {
	element := readElement(exml)
	action := false
	buff := false

	skillType, err := exml.GetInteger("skillType")
	if err == nil && skillType == 2 {
		buff = true
	} else {
		action = hasAnyAction(exml)
		buff = getBuff(exml)
		switch skillId {
		case HeroRush, PaladinRush, DarkKnightRush, DragonKnightSacrifice, FirePoisonMagicianExplosion,
			FirePoisonMagicianPoisonMist, ClericHeal, RangerMortalBlow, SniperMortalBlow, AssassinDrain, HermitShadowWeb,
			BanditSteal, ShadowerSmokeScreen, SuperGMHealPlusDispel, HeroMonsterMagnet, PaladinMonsterMagnet,
			DarkKnightMonsterMagnet, EvanIceBreath, EvanFireBreath, GunslingerRecoilShot, MarauderEnergyDrain,
			BlazeWizardFlameGear, NightWalkerShadowWeb, NightWalkerPoisonBomb, NightWalkerVampire, ChiefBanditChakra,
			AranCombatStep, EvanRecoveryAura:
			buff = false
		case BeginnerRecovery, BeginnerNimbleFeet, BeginnerMonsterRider, BeginnerEchoOfHero, BeginnerMapChair,
			WarriorIronBody, FighterAxeBooster, FighterPowerGuard, FighterRage, FighterSwordBooster, CrusaderCombo,
			HeroEnrage, HeroHerosWill, HeroMapleWarrior, HeroStance, PageBluntWeaponBooster, PagePowerGuard,
			PageSwordBooster, PageThreaten, WhiteKnightBluntWeaponFireCharge, WhiteKnightBluntWeaponIceCharge,
			WhiteKnightBluntWeaponLightingCharge, WhiteKnightMagicCrash, WhiteKnightSwordFireCharge,
			WhiteKnightSwordIceCharge, WhiteKnightSwordLightingCharge, PaladinBluntWeaponHolyCharge, PaladinHerosWill,
			PaladinMapleWarrior, PaladinStance, PaladinSwordHolyCharge, SpearmanHyperBody, SpearmanIronWill,
			SpearmanPolearmBooster, SpearmanSpearBooster, DragonKnightDragonBlood, DragonKnightPowerCrash,
			DarkKnightAuraOfBeholder, DarkKnightBeholder, DarkKnightHerosWill, DarkKnightHexOfBeholder,
			DarkKnightMapleWarrior, DarkKnightStance, MagicianMagicGuard, MagicianMagicArmor, FirePoisonWizardMeditation,
			FirePoisonWizardSlow, FirePoisonMagicianSeal, FirePoisonMagicianSpellBooster, FirePoisonArchMagicianHerosWill,
			FirePoisonArchMagicianInfinity, FirePoisonArchMagicianManaReflection, FirePoisonArchMagicianMapleWarrior,
			IceLightningWizardMeditation, IceLightningMagicianSeal, IceLightningWizardSlow,
			IceLightningMagicianSpellBooster, IceLightningArchMagicianHerosWill, IceLightningArchMagicianInfinity,
			IceLightningArchMagicianManaReflection, IceLightningArchMagicianMapleWarrior, ClericInvincible, ClericBless,
			PriestDispel, PriestDoom, PriestHolySymbol, PriestMysticDoor, BishopHerosWill, BishopHolyShield, BishopInfinity,
			BishopManaReflection, BishopMapleWarrior, ArcherFocus, HunterBowBooster, HunterSoulArrow, RangerPuppet,
			BowmasterConcentrate, BowmasterHerosWill, BowmasterMapleWarrior, BowmasterSharpEyes, CrossbowmanCrossbowBooster,
			CrossbowmanSoulArrow, SniperPuppet, MarksmanBlind, MarksmanHerosWill, MarksmanMapleWarrior, MarksmanSharpEyes,
			RogueDarkSight, AssassinClawBooster, AssassinHaste, HermitMesoUp, HermitShadowPartner, NightLordHerosWill,
			NightLordMapleWarrior, NightLordNinjaAmbush, NightLordShadowStars, BanditDaggerBooster, BanditHaste,
			ChiefBanditMesoGuard, ChiefBanditPickPocket, ShadowerHerosWill, ShadowerMapleWarrior, ShadowerNinjaAmbush,
			PirateDash, MarauderTransformation, BuccaneerSuperTransformation, CorsairBattleShip, SuperGMHaste,
			SuperGMHolySymbol, SuperGMBless, SuperGMHide, SuperGMHyperBody, NoblesseBlessingOfTheFairy, NoblesseEchoOfHero,
			NoblesseMonsterRider, NoblesseNimbleFeet, NoblesseRecovery, NoblesseMapChair, DawnWarriorCombo,
			DawnWarriorFinalAttack, DawnWarriorIronBody, DawnWarriorRage, DawnWarriorSoul, DawnWarriorSoulCharge,
			DawnWarriorSwordBooster, BlazeWizardElementalReset, BlazeWizardFlame, BlazeWizardIfrit, BlazeWizardMagicArmor,
			BlazeWizardMagicGuard, BlazeWizardMeditation, BlazeWizardSeal, BlazeWizardSlow, BlazeWizardSpellBooster,
			WindArcherBowBooster, WindArcherEagleEye, WindArcherFinalAttack, WindArcherFocus, WindArcherPuppet,
			WindArcherSoulArrow, WindArcherStorm, WindArcherWindWalk, NightWalkerClawBooster, NightWalkerDarkness,
			NightWalkerDarkSight, NightWalkerHaste, NightWalkerShadowPartner, ThunderBreakerDash, ThunderBreakerEnergyCharge,
			ThunderBreakerEnergyDrain, ThunderBreakerKnucklerBooster, ThunderBreakerLightning, ThunderBreakerSpark,
			ThunderBreakerLightningCharge, ThunderBreakerSpeedInfusion, ThunderBreakerTransformation,
			LegendBlessingOfTheFairy, LegendAgileBody, LegendEchoOfHero, LegendRecovery, LegendMonsterRider,
			LegendMapChair, AranMapleWarrior, AranHerosWill, AranPolearmBooster, AranComboDrain, AranSnowCharge,
			AranBodyPressure, AranSmartKnockback, AranComboBarrier, AranComboAbility, EvanBlessingOfTheFairy, EvanRecovery,
			EvanNimbleFeet, EvanHerosWill, EvanEchoOfHero, EvanMagicBooster, EvanMagicGuard, EvanElementalReset,
			EvanMapleWarrior, EvanMagicResistance, EvanMagicShield, EvanSlow:
			buff = true
		}
	}

	es := make([]Effect, 0)
	level, err := exml.ChildByName("level")
	if err == nil {
		es = getEffects(skillId, buff, level.ChildNodes)
	}

	m := &Model{
		id:            skillId,
		action:        action,
		element:       element,
		animationTime: 0,
		effects:       es,
	}

	return m, nil
}

func getEffects(skillId uint32, buff bool, nodes []xml.Node) []Effect {
	results := make([]Effect, 0)
	for _, node := range nodes {
		result := getEffect(skillId, buff, node)
		results = append(results, result)
	}
	return results
}

func getEffect(skillId uint32, overTime bool, node xml.Node) Effect {
	e := Effect{}
	e.duration = node.GetIntegerWithDefault("time", -1)
	e.hp = uint16(node.GetIntegerWithDefault("hp", 0))
	e.hpr = float64(node.GetIntegerWithDefault("hpR", 0)) / 100.0
	e.mp = uint16(node.GetIntegerWithDefault("mp", 0))
	e.mpr = float64(node.GetIntegerWithDefault("mpR", 0)) / 100.0
	e.hpCon = uint16(node.GetIntegerWithDefault("hpCon", 0))
	e.mpCon = uint16(node.GetIntegerWithDefault("mpCon", 0))
	e.prop = float64(node.GetIntegerWithDefault("prop", 0)) / 100.0
	e.cp = uint32(node.GetIntegerWithDefault("cp", 0))
	e.cureAbnormalStatuses = getAbnormalStatuses(node)
	e.nuffSkill = uint32(node.GetIntegerWithDefault("nuffSkill", 0))
	e.mobCount = uint32(node.GetIntegerWithDefault("mobCount", 1))
	e.cooldown = uint32(node.GetIntegerWithDefault("cooltime", 0))
	e.morphId = uint32(node.GetIntegerWithDefault("morph", 0))
	e.ghost = uint32(node.GetIntegerWithDefault("ghost", 0))
	e.fatigue = uint32(node.GetIntegerWithDefault("incFatigue", 0))
	e.repeatEffect = node.GetIntegerWithDefault("repeatEffect", 0) > 0
	applyMobInformation(e, node)
	e.mob = getMob(node)
	e.skill = true
	if e.duration > -1 {
		e.overtime = true
	} else {
		e.duration *= 1000
		e.overtime = overTime
	}

	e.weaponAttack = uint16(node.GetIntegerWithDefault("pad", 0))
	e.weaponDefense = uint16(node.GetIntegerWithDefault("pdd", 0))
	e.magicAttack = uint16(node.GetIntegerWithDefault("mad", 0))
	e.magicDefense = uint16(node.GetIntegerWithDefault("mdd", 0))
	e.accuracy = uint16(node.GetIntegerWithDefault("acc", 0))
	e.avoidability = uint16(node.GetIntegerWithDefault("eva", 0))
	e.speed = uint16(node.GetIntegerWithDefault("speed", 0))
	e.jump = uint16(node.GetIntegerWithDefault("jump", 0))
	e.barrier = uint32(node.GetIntegerWithDefault("barrier", 0))

	statups := make([]Statup, 0)
	statups = produceBuffStatAmount(statups, buff.AuraValue, e.barrier)

	e.mapProtection = getMapProtection(skillId)
	statups = produceBuffStatAmount(statups, buff.MapProtectionValue, uint32(e.mapProtection))

	if e.overtime && getSummonMovementType(skillId) == SummonMovementTypeNone {
		if IsMapChair(skillId) {
			statups = produceBuffStatAmount(statups, buff.MapChairValue, 1)
		} else if (skillId == BeginnerNimbleFeet || skillId == NoblesseNimbleFeet || skillId == EvanNimbleFeet || skillId == LegendAgileBody) && configuration.Get().UseUltraNimbleFeet {
			e.jump *= 4
			e.speed *= 15
		}
		statups = produceBuffStatAmount(statups, buff.WeaponAttackValue, uint32(e.weaponAttack))
		statups = produceBuffStatAmount(statups, buff.WeaponDefenseValue, uint32(e.weaponDefense))
		statups = produceBuffStatAmount(statups, buff.MagicAttackValue, uint32(e.magicAttack))
		statups = produceBuffStatAmount(statups, buff.MagicDefenseValue, uint32(e.magicDefense))
		statups = produceBuffStatAmount(statups, buff.AccuracyValue, uint32(e.accuracy))
		statups = produceBuffStatAmount(statups, buff.AvoidabilityValue, uint32(e.avoidability))
		statups = produceBuffStatAmount(statups, buff.SpeedValue, uint32(e.speed))
		statups = produceBuffStatAmount(statups, buff.JumpValue, uint32(e.jump))
	}

	//TODO LT

	x := int16(node.GetIntegerWithDefault("x", 0))
	if (skillId == BeginnerRecovery || skillId == NoblesseRecovery || skillId == EvanRecovery || skillId == LegendRecovery) && configuration.Get().UseUltraRecovery {
		x *= 10
	}
	e.x = x
	e.y = int16(node.GetIntegerWithDefault("y", 0))
	e.damage = uint32(node.GetIntegerWithDefault("damage", 100))
	e.fixDamage = node.GetIntegerWithDefault("fixdamage", -1)
	e.attackCount = uint32(node.GetIntegerWithDefault("attackCount", 1))
	e.bulletCount = uint16(node.GetIntegerWithDefault("bulletCount", 1))
	e.bulletConsume = uint16(node.GetIntegerWithDefault("bulletConsume", 0))
	e.moneyCon = uint32(node.GetIntegerWithDefault("moneyCon", 0))
	e.itemCon = uint32(node.GetIntegerWithDefault("itemCon", 0))
	e.itemConNo = uint32(node.GetIntegerWithDefault("itemConNo", 0))
	e.moveTo = node.GetIntegerWithDefault("moveTo", -1)

	ms := make(map[string]uint32)

	switch skillId {
	case BeginnerRecovery, NoblesseRecovery, LegendRecovery, EvanRecovery:
		statups = produceBuffStatAmount(statups, buff.RecoveryValue, uint32(e.x))
		break
	case BeginnerEchoOfHero, NoblesseEchoOfHero, LegendEchoOfHero, EvanEchoOfHero:
		statups = produceBuffStatAmount(statups, buff.EchoOfHeroValue, uint32(e.x))
		break
	case BeginnerMonsterRider, NoblesseMonsterRider, LegendMonsterRider, CorsairBattleShip:
		//TODO others
		statups = produceBuffStatAmount(statups, buff.MonsterRidingValue, skillId)
		break
	case BeginnerInvincibleBarrier, NoblesseInvincibleBarrier, LegendInvincibleBarrier, EvanInvincibleBarrier:
		statups = produceBuffStatAmount(statups, buff.DivineBodyValue, 1)
		break
	case FighterPowerGuard, PagePowerGuard:
		statups = produceBuffStatAmount(statups, buff.PowerGuardValue, uint32(e.x))
		break
	case SpearmanHyperBody, GMHyperBody, SuperGMHyperBody:
		statups = produceBuffStatAmount(statups, buff.HyperBodyHPValue, uint32(e.x))
		statups = produceBuffStatAmount(statups, buff.HyperBodyMPValue, uint32(e.y))
		break
	case CrusaderCombo, DawnWarriorCombo:
		statups = produceBuffStatAmount(statups, buff.ComboValue, 1)
		break
	case WhiteKnightBluntWeaponFireCharge, WhiteKnightBluntWeaponIceCharge, WhiteKnightBluntWeaponLightingCharge,
		WhiteKnightSwordFireCharge, WhiteKnightSwordIceCharge, WhiteKnightSwordLightingCharge,
		PaladinBluntWeaponHolyCharge, PaladinSwordHolyCharge, DawnWarriorSoulCharge, ThunderBreakerLightningCharge:
		statups = produceBuffStatAmount(statups, buff.WhiteKnightChargeValue, uint32(e.x))
		break
	case DragonKnightDragonBlood:
		statups = produceBuffStatAmount(statups, buff.DragonBloodValue, uint32(e.x))
		break
	case HeroStance, PaladinStance, DarkKnightStance, AranFreezeStanding:
		statups = produceBuffStatAmount(statups, buff.StanceValue, uint32(math.Floor(e.prop*100)))
		break
	case DawnWarriorFinalAttack, WindArcherFinalAttack:
		statups = produceBuffStatAmount(statups, buff.FinalAttackValue, uint32(e.x))
		break
	case MagicianMagicGuard, BlazeWizardMagicGuard, EvanMagicGuard:
		statups = produceBuffStatAmount(statups, buff.MagicGuardValue, uint32(e.x))
		break
	case ClericInvincible:
		statups = produceBuffStatAmount(statups, buff.InvincibleValue, uint32(e.x))
		break
	case PriestHolySymbol, SuperGMHolySymbol:
		statups = produceBuffStatAmount(statups, buff.HolySymbolValue, uint32(e.x))
		break
	case FirePoisonArchMagicianInfinity, IceLightningArchMagicianInfinity:
		statups = produceBuffStatAmount(statups, buff.InfinityValue, uint32(e.x))
		break
	case FirePoisonArchMagicianManaReflection, IceLightningArchMagicianManaReflection, BishopManaReflection:
		statups = produceBuffStatAmount(statups, buff.ManaReflectionValue, 1)
		break
	case BishopHolyShield:
		statups = produceBuffStatAmount(statups, buff.HolyShieldValue, uint32(e.x))
		break
	case BlazeWizardElementalReset, EvanElementalReset:
		statups = produceBuffStatAmount(statups, buff.ElementalResetValue, uint32(e.x))
		break
	case EvanMagicShield:
		statups = produceBuffStatAmount(statups, buff.MagicShieldValue, uint32(e.x))
		break
	case EvanMagicResistance:
		statups = produceBuffStatAmount(statups, buff.MagicResistanceValue, uint32(e.x))
		break
	case EvanSlow:
		statups = produceBuffStatAmount(statups, buff.SlowValue, uint32(e.x))
		break
		//TODO this is weird right?
	case PriestMysticDoor, HunterSoulArrow, CrossbowmanSoulArrow, WindArcherSoulArrow:
		statups = produceBuffStatAmount(statups, buff.SoulArrowValue, uint32(e.x))
		break
	case RangerPuppet, SniperPuppet, WindArcherPuppet, OutlawOctopus, CorsairWrathOfTheOctopi:
		statups = produceBuffStatAmount(statups, buff.PuppetValue, 1)
		break
	case BowmasterConcentrate:
		statups = produceBuffStatAmount(statups, buff.ConcentrateValue, uint32(e.x))
		break
	case BowmasterHamstring:
		statups = produceBuffStatAmount(statups, buff.HamstringValue, uint32(e.x))
		ms[monster.StatusSpeed] = uint32(e.x)
		break
	case MarksmanBlind:
		statups = produceBuffStatAmount(statups, buff.BlindValue, uint32(e.x))
		ms[monster.StatusAccuracy] = uint32(e.x)
		break
	case BowmasterSharpEyes, MarksmanSharpEyes:
		statups = produceBuffStatAmount(statups, buff.SharpEyesValue, uint32(e.x<<8|e.y))
		break
	case WindArcherWindWalk:
		statups = produceBuffStatAmount(statups, buff.WindWalkValue, uint32(e.x))
		break
	case RogueDarkSight, NightWalkerDarkSight:
		statups = produceBuffStatAmount(statups, buff.DarkSightValue, uint32(e.x))
		break
	case HermitMesoUp:
		statups = produceBuffStatAmount(statups, buff.MesoUpValue, uint32(e.x))
		break
	case HermitShadowPartner, NightWalkerShadowPartner:
		statups = produceBuffStatAmount(statups, buff.ShadowPartnerValue, uint32(e.x))
		break
	case ChiefBanditMesoGuard:
		statups = produceBuffStatAmount(statups, buff.MesoGuardValue, uint32(e.x))
		break
	case ChiefBanditPickPocket:
		statups = produceBuffStatAmount(statups, buff.PickPocketValue, uint32(e.x))
		break
	case NightLordShadowStars:
		statups = produceBuffStatAmount(statups, buff.ShadowClawValue, 0)
		break
	case PirateDash, ThunderBreakerDash, BeginnerSpaceDash, NoblesseSpaceDash:
		statups = produceBuffStatAmount(statups, buff.Dash2Value, uint32(e.x))
		statups = produceBuffStatAmount(statups, buff.DashValue, uint32(e.y))
		break
	case CorsairSpeedInfusion, BuccaneerSpeedInfusion, ThunderBreakerSpeedInfusion:
		statups = produceBuffStatAmount(statups, buff.SpeedInfusionValue, uint32(e.x))
		break
	case OutlawHomingBeacon, CorsairBullsEye:
		statups = produceBuffStatAmount(statups, buff.HomingBeaconValue, uint32(e.x))
		break
	case ThunderBreakerSpark:
		statups = produceBuffStatAmount(statups, buff.SparkValue, uint32(e.x))
		break
	case AranPolearmBooster, FighterAxeBooster, FighterSwordBooster, PageBluntWeaponBooster, PageSwordBooster,
		SpearmanPolearmBooster, SpearmanSpearBooster, HunterBowBooster, CrossbowmanCrossbowBooster, AssassinClawBooster,
		BanditDaggerBooster, FirePoisonMagicianSpellBooster, IceLightningMagicianSpellBooster, BrawlerKnucklerBooster,
		GunslingerGunBooster, DawnWarriorSwordBooster, BlazeWizardSpellBooster, WindArcherBowBooster,
		NightWalkerClawBooster, ThunderBreakerKnucklerBooster, EvanMagicBooster, BeginnerPowerExplosion,
		NoblessePowerExplosion, LegendPowerExplosion:
		statups = produceBuffStatAmount(statups, buff.BoosterValue, uint32(e.x))
		break
	case HeroMapleWarrior, PaladinMapleWarrior, DarkKnightMapleWarrior, FirePoisonArchMagicianMapleWarrior,
		IceLightningArchMagicianMapleWarrior, BishopMapleWarrior, BowmasterMapleWarrior, MarksmanMapleWarrior,
		NightLordMapleWarrior, ShadowerMapleWarrior, CorsairMapleWarrior, BuccaneerMapleWarrior, AranMapleWarrior,
		EvanMapleWarrior:
		statups = produceBuffStatAmount(statups, buff.MapleWarriorValue, uint32(e.x))
		break
	case RangerSilverHawk, SniperGoldenEagle:
		statups = produceBuffStatAmount(statups, buff.SummonValue, 1)
		ms[monster.StatusStun] = 1
		break
	case FirePoisonArchMagicianElquines, MarksmanFrostPrey:
		statups = produceBuffStatAmount(statups, buff.SummonValue, 1)
		ms[monster.StatusFreeze] = 1
		break
	case PriestSummonDragon, BowmasterPhoenix, IceLightningArchMagicianIfrit, BishopBahamut, DarkKnightBeholder,
		OutlawGaviota, DawnWarriorSoul, BlazeWizardFlame, WindArcherStorm, NightWalkerDarkness, ThunderBreakerLightning,
		BlazeWizardIfrit:
		statups = produceBuffStatAmount(statups, buff.SummonValue, 1)
		break
	case CrusaderArmorCrash, DragonKnightPowerCrash, WhiteKnightMagicCrash:
		ms[monster.StatusSeal] = 1
		break
	case RogueDisorder, PageThreaten:
		ms[monster.StatusWeaponAttack] = uint32(e.x)
		ms[monster.StatusWeaponDefense] = uint32(e.x)
		break
	case CorsairHypnotize:
		ms[monster.StatusInertMob] = 1
		break
	case NightLordNinjaAmbush, ShadowerNinjaAmbush:
		ms[monster.StatusNinjaAmbush] = e.damage
		break
	case DragonKnightDragonRoar:
		e.hpr = float64(-e.x) / 100.0
		ms[monster.StatusStun] = 1
		break
	case CrusaderAxeComa, CrusaderSwordComa, CrusaderShout, WhiteKnightChargeBlow, HunterArrowBomb,
		ChiefBanditAssaulter, ShadowerBoomerangStep, BrawlerBackSpinBlow, BrawlerDoubleUppercut, BuccaneerDemolition,
		BuccaneerSnatch, BuccaneerBarrage, GunslingerBlankShot, DawnWarriorComa, ThunderBreakerBarrage, AranRollingSpin,
		EvanFireBreath, EvanBlaze:
		ms[monster.StatusStun] = 1
		break
	case NightLordTaunt, ShadowerTaunt:
		ms[monster.StatusShowdown] = uint32(e.x)
		ms[monster.StatusMagicDefense] = uint32(e.x)
		ms[monster.StatusWeaponDefense] = uint32(e.x)
		break
	case IceLightningWizardColdBeam, IceLightningMagicianIceStrike, IceLightningArchMagicianBlizzard,
		IceLightningMagicianElementComposition, SniperBlizzard, OutlawIceSplitter, FirePoisonArchMagicianParalyze,
		AranComboTempest, EvanIceBreath:
		ms[monster.StatusFreeze] = 1
		e.duration *= 2
		break
	case FirePoisonWizardSlow, IceLightningWizardSlow, BlazeWizardSlow:
		ms[monster.StatusSpeed] = uint32(e.x)
		break
	case FirePoisonWizardPoisonBreath, FirePoisonMagicianElementComposition:
		ms[monster.StatusPoison] = 1
		break
	case PriestDoom:
		ms[monster.StatusDoom] = 1
		break
	case IceLightningMagicianSeal, FirePoisonMagicianSeal, BlazeWizardSeal:
		ms[monster.StatusSeal] = 1
		break
	case HermitShadowWeb, NightWalkerShadowWeb:
		ms[monster.StatusShadowWeb] = 1
		break
	case FirePoisonArchMagicianFireDemon, IceLightningArchMagicianIceDemon:
		ms[monster.StatusPoison] = 1
		ms[monster.StatusFreeze] = 1
		break
	case EvanPhantomImprint:
		ms[monster.StatusPhantomImprint] = uint32(e.x)
		break
	case AranComboAbility:
		statups = produceBuffStatAmount(statups, buff.AranComboValue, 100)
		break
	case AranComboBarrier:
		statups = produceBuffStatAmount(statups, buff.ComboBarrierValue, uint32(e.x))
		break
	case AranComboDrain:
		statups = produceBuffStatAmount(statups, buff.ComboDrainValue, uint32(e.x))
		break
	case AranSmartKnockback:
		statups = produceBuffStatAmount(statups, buff.SmartKnockBackValue, uint32(e.x))
		break
	case AranBodyPressure:
		statups = produceBuffStatAmount(statups, buff.BodyPressureValue, uint32(e.x))
		break
	case AranSnowCharge:
		statups = produceBuffStatAmount(statups, buff.WhiteKnightChargeValue, uint32(e.duration))
		break
	}

	statups = produceBuffStatAmount(statups, buff.MorphValue, e.morphId)
	e.monsterStatus = ms
	e.statups = statups

	return e
}

func getMob(node xml.Node) uint32 {
	c, err := node.ChildByName("mob")
	if err != nil || len(c.ChildNodes) <= 0 {
		return 0
	}
	return uint32(c.GetIntegerWithDefault("mob", 0))
}

func applyMobInformation(e Effect, node xml.Node) {
	c, err := node.ChildByName("0")
	if err == nil && len(c.ChildNodes) > 0 {
		e.mobSkill = uint16(node.GetIntegerWithDefault("mobSkill", 0))
		e.mobSkillLevel = uint16(node.GetIntegerWithDefault("level", 0))
		e.target = uint32(node.GetIntegerWithDefault("target", 0))
	} else {
		e.mobSkill = 0
		e.mobSkillLevel = 0
		e.target = 0
	}
}

func getAbnormalStatuses(node xml.Node) []string {
	statuses := make([]string, 0)
	if node.GetIntegerWithDefault("poison", 0) > 0 {
		statuses = append(statuses, "POISON")
	}
	if node.GetIntegerWithDefault("seal", 0) > 0 {
		statuses = append(statuses, "SEAL")
	}
	if node.GetIntegerWithDefault("darkness", 0) > 0 {
		statuses = append(statuses, "DARKNESS")
	}
	if node.GetIntegerWithDefault("weakness", 0) > 0 {
		statuses = append(statuses, "WEAKEN", "SLOW")
	}
	if node.GetIntegerWithDefault("curse", 0) > 0 {
		statuses = append(statuses, "CURSE")
	}
	return statuses
}

func getMapProtection(sourceId uint32) byte {
	if sourceId == 2022001 || sourceId == 2022186 {
		return 1 //elnath cold
	} else if sourceId == 2022040 {
		return 2 //aqua road underwater
	} else {
		return 0
	}
}

func produceBuffStatAmount(existing []Statup, buff string, value uint32) []Statup {
	if value != 0 {
		return append(existing, Statup{buff: buff, amount: value})
	}
	return existing
}

func getBuff(exml *xml.Node) bool {
	buff := hasEffect(exml) && noHit(exml) && noBall(exml)
	if buff {
		return true
	}

	a, err := exml.ChildByName("action")
	if err != nil {
		return false
	}
	astr := a.GetString("0", "")
	return astr == "alert2"
}

func noBall(exml *xml.Node) bool {
	_, err := exml.ChildByName("ball")
	return err != nil
}

func noHit(exml *xml.Node) bool {
	_, err := exml.ChildByName("hit")
	return err != nil
}

func hasEffect(exml *xml.Node) bool {
	_, err := exml.ChildByName("effect")
	return err == nil
}

func hasAnyAction(exml *xml.Node) bool {
	_, err := exml.ChildByName("action")
	if err == nil {
		return true
	}
	_, err = exml.ChildByName("prepare/action")
	if err == nil {
		return true
	}
	skillId, err := strconv.Atoi(exml.Name)
	if err != nil {
		return false
	}
	return actionBySkill(uint32(skillId))
}

func actionBySkill(skillId uint32) bool {
	switch skillId {
	case GunslingerInvisibleShot, CorsairHypnotize:
		return true
	default:
		return false
	}
}

func readElement(exml *xml.Node) string {
	i := exml.GetString("elemAttr", "P")
	r, err := ElementFromChar(i)
	if err != nil {
		return ElementNeutral
	}
	return r
}
