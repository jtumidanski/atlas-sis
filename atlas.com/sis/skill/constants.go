package skill

import (
	"errors"
	"strings"
)

const (
	ElementNeutral  string = "NEUTRAL"
	ElementPhysical string = "PHYSICAL"
	ElementFire     string = "FIRE"
	ElementIce      string = "ICE"
	ElementLighting string = "LIGHTING"
	ElementPoison   string = "POISON"
	ElementHoly     string = "HOLY"
	ElementDarkness string = "DARKNESS"

	SummonMovementTypeNone         int8 = -1
	SummonMovementTypeStationary   int8 = 0
	SummonMovementTypeFollow       int8 = 1
	SummonMovementTypeCircleFollow int8 = 2

	BeginnerBlessingOfTheFairy uint32 = 12
	BeginnerFollowTheLeader    uint32 = 8
	BeginnerMapChair           uint32 = 100
	BeginnerThreeSnails        uint32 = 1000
	BeginnerRecovery           uint32 = 1001
	BeginnerNimbleFeet         uint32 = 1002
	BeginnerMonsterRider       uint32 = 1004
	BeginnerEchoOfHero         uint32 = 1005
	BeginnerBambooRain         uint32 = 1009
	BeginnerInvincibleBarrier  uint32 = 1010
	BeginnerPowerExplosion     uint32 = 1011
	BeginnerSpaceship          uint32 = 1013
	BeginnerSpaceDash          uint32 = 1014
	BeginnerYetiMount1         uint32 = 1017
	BeginnerYetiMount2         uint32 = 1018
	BeginnerWitchBroomstick    uint32 = 1019
	BeginnerBalrogMount        uint32 = 1031

	WarriorImprovedHPRecovery uint32 = 1000000
	WarriorImprovedHPIncrease uint32 = 1000001
	WarriorEndure             uint32 = 1000002
	WarriorIronBody           uint32 = 1001003
	WarriorPowerStrike        uint32 = 1001004
	WarriorSlashBlast         uint32 = 1001005

	FighterSwordMastery     uint32 = 1100000
	FighterAxeMastery       uint32 = 1100001
	FighterFinalAttackSword uint32 = 1100002
	FighterFinalAttackAxe   uint32 = 1100003
	FighterSwordBooster     uint32 = 1101004
	FighterAxeBooster       uint32 = 1101005
	FighterRage             uint32 = 1101006
	FighterPowerGuard       uint32 = 1101007

	PageSwordMastery           uint32 = 1200000
	PageBluntWeaponMastery     uint32 = 1200001
	PageFinalAttackSword       uint32 = 1200002
	PageFinalAttackBluntWeapon uint32 = 1200003
	PageSwordBooster           uint32 = 1201004
	PageBluntWeaponBooster     uint32 = 1201005
	PageThreaten               uint32 = 1201006
	PagePowerGuard             uint32 = 1201007

	SpearmanSpearMastery       uint32 = 1300000
	SpearmanPolearmMastery     uint32 = 1300001
	SpearmanFinalAttackSpear   uint32 = 1300002
	SpearmanFinalAttackPolearm uint32 = 1300003
	SpearmanSpearBooster       uint32 = 1301004
	SpearmanPolearmBooster     uint32 = 1301005
	SpearmanIronWill           uint32 = 1301006
	SpearmanHyperBody          uint32 = 1301007

	CrusaderCombo      uint32 = 1111002
	CrusaderSwordPanic uint32 = 1111003
	CrusaderAxePanic   uint32 = 1111004
	CrusaderSwordComa  uint32 = 1111005
	CrusaderAxeComa    uint32 = 1111006
	CrusaderArmorCrash uint32 = 1111007
	CrusaderShout      uint32 = 1111008

	DragonKnightElementalResistance uint32 = 1310000
	DragonKnightSpearCrusher        uint32 = 1311001
	DragonKnightPolearmCrusher      uint32 = 1311002
	DragonKnightSacrifice           uint32 = 1311005
	DragonKnightDragonRoar          uint32 = 1311006
	DragonKnightPowerCrash          uint32 = 1311007
	DragonKnightDragonBlood         uint32 = 1311008

	WhiteKnightImprovingMPRecovery       uint32 = 1210000
	WhiteKnightChargeBlow                uint32 = 1211002
	WhiteKnightSwordFireCharge           uint32 = 1211003
	WhiteKnightBluntWeaponFireCharge     uint32 = 1211004
	WhiteKnightSwordIceCharge            uint32 = 1211005
	WhiteKnightBluntWeaponIceCharge      uint32 = 1211006
	WhiteKnightSwordLightingCharge       uint32 = 1211007
	WhiteKnightBluntWeaponLightingCharge uint32 = 1211008
	WhiteKnightMagicCrash                uint32 = 1211009

	HeroMonsterMagnet uint32 = 1121001
	HeroAchilles      uint32 = 1120004
	HeroBrandish      uint32 = 1121008
	HeroEnrage        uint32 = 1121010
	HeroGuardian      uint32 = 1120005
	HeroRush          uint32 = 1121006
	HeroStance        uint32 = 1121002
	HeroHerosWill     uint32 = 1121011
	HeroMapleWarrior  uint32 = 1121000

	PaladinMonsterMagnet         uint32 = 1221001
	PaladinAchilles              uint32 = 1220005
	PaladinBlast                 uint32 = 1221009
	PaladinStance                uint32 = 1221002
	PaladinSwordHolyCharge       uint32 = 1221003
	PaladinBluntWeaponHolyCharge uint32 = 1221004
	PaladinRush                  uint32 = 1221007
	PaladinHerosWill             uint32 = 1221012
	PaladinMapleWarrior          uint32 = 1221000

	DarkKnightMonsterMagnet  uint32 = 1321001
	DarkKnightAchilles       uint32 = 1320005
	DarkKnightBeholder       uint32 = 1321007
	DarkKnightStance         uint32 = 1321002
	DarkKnightAuraOfBeholder uint32 = 1320008
	DarkKnightHexOfBeholder  uint32 = 1320009
	DarkKnightRush           uint32 = 1321003
	DarkKnightHerosWill      uint32 = 1321010
	DarkKnightMapleWarrior   uint32 = 1321000

	MagicianImprovedMPRecovery uint32 = 2000000
	MagicianImprovedMPIncrease uint32 = 2000001
	MagicianMagicGuard         uint32 = 2001002
	MagicianMagicArmor         uint32 = 2001003
	MagicianEnergyBolt         uint32 = 2001004
	MagicianMagicClaw          uint32 = 2001005

	FirePoisonWizardMPEater      uint32 = 2100000
	FirePoisonWizardMeditation   uint32 = 2101001
	FirePoisonWizardTeleport     uint32 = 2101002
	FirePoisonWizardSlow         uint32 = 2101003
	FirePoisonWizardFireArrow    uint32 = 2101004
	FirePoisonWizardPoisonBreath uint32 = 2101005

	IceLightningWizardMPEater     uint32 = 2200000
	IceLightningWizardMeditation  uint32 = 2201001
	IceLightningWizardTeleport    uint32 = 2201002
	IceLightningWizardSlow        uint32 = 2201003
	IceLightningWizardColdBeam    uint32 = 2201004
	IceLightningWizardThunderBolt uint32 = 2201005

	ClericMPEater    uint32 = 2300000
	ClericTeleport   uint32 = 2301001
	ClericHeal       uint32 = 2301002
	ClericInvincible uint32 = 2301003
	ClericBless      uint32 = 2301004
	ClericHolyArrow  uint32 = 2301005

	BowmanBlessingOfAmazon uint32 = 3000000
	BowmanCriticalShot     uint32 = 3000001
	BowmanTheEyeOfAmazon   uint32 = 3000002
	BowmanFocus            uint32 = 3001003
	BowmanArrowBlow        uint32 = 3001004
	BowmanDoubleShot       uint32 = 3001005

	HunterBowMastery     uint32 = 3100000
	HunterFinalAttack    uint32 = 3100001
	HunterBowBooster     uint32 = 3101002
	HunterPowerKnockback uint32 = 3101003
	HunterSoulArrow      uint32 = 3101004
	HunterArrowBomb      uint32 = 3101005

	CrossbowmanCrossbowMastery uint32 = 3200000
	CrossbowmanFinalAttack     uint32 = 3200001
	CrossbowmanCrossbowBooster uint32 = 3201002
	CrossbowmanPowerKnockback  uint32 = 3201003
	CrossbowmanSoulArrow       uint32 = 3201004
	CrossbowmanIronArrow       uint32 = 3201005

	ThiefNimbleBody uint32 = 4000000
	ThiefKeenEyes   uint32 = 4000001
	ThiefDisorder   uint32 = 4001002
	ThiefDarkSight  uint32 = 4001003
	ThiefDoubleStab uint32 = 4001334
	ThiefLuckySeven uint32 = 4001344

	AssassinClawMastery   uint32 = 4100000
	AssassinCriticalThrow uint32 = 4100001
	AssassinEndure        uint32 = 4100002
	AssassinClawBooster   uint32 = 4101003
	AssassinHaste         uint32 = 4101004
	AssassinDrain         uint32 = 4101005

	BanditDaggerMastery uint32 = 4200000
	BanditEndure        uint32 = 4200001
	BanditDaggerBooster uint32 = 4201002
	BanditHaste         uint32 = 4201003
	BanditSteal         uint32 = 4201004
	BanditSavageBlow    uint32 = 4201005

	PirateBulletTime     uint32 = 5000000
	PirateFlashFist      uint32 = 5001001
	PirateSomersaultKick uint32 = 5001002
	PirateDoubleShot     uint32 = 5001003
	PirateDash           uint32 = 5001005

	BrawlerImproveMaxHP    uint32 = 5100000
	BrawlerKnucklerMastery uint32 = 5100001
	BrawlerBackSpinBlow    uint32 = 5101002
	BrawlerDoubleUppercut  uint32 = 5101003
	BrawlerCorkscrewBlow   uint32 = 5101004
	BrawlerMPRecovery      uint32 = 5101005
	BrawlerKnucklerBooster uint32 = 5101006
	BrawlerOakBarrel       uint32 = 5101007

	GunslingerGunMastery    uint32 = 5200000
	GunslingerInvisibleShot uint32 = 5201001
	GunslingerGrenade       uint32 = 5201002
	GunslingerGunBooster    uint32 = 5201003
	GunslingerBlankShot     uint32 = 5201004
	GunslingerWings         uint32 = 5201005
	GunslingerRecoilShot    uint32 = 5201006

	FirePoisonMagicianPartialResistance    uint32 = 2110000
	FirePoisonMagicianElementAmplification uint32 = 2110001
	FirePoisonMagicianExplosion            uint32 = 2111002
	FirePoisonMagicianPoisonMist           uint32 = 2111003
	FirePoisonMagicianSeal                 uint32 = 2111004
	FirePoisonMagicianSpellBooster         uint32 = 2111005
	FirePoisonMagicianElementComposition   uint32 = 2111006

	IceLightningMagicianPartialResistance    uint32 = 2210000
	IceLightningMagicianElementAmplification uint32 = 2210001
	IceLightningMagicianIceStrike            uint32 = 2211002
	IceLightningMagicianSeal                 uint32 = 2211004
	IceLightningMagicianSpellBooster         uint32 = 2211005
	IceLightningMagicianElementComposition   uint32 = 2211006

	PriestElementalResistance uint32 = 2310000
	PriestDispel              uint32 = 2311001
	PriestMysticDoor          uint32 = 2311002
	PriestHolySymbol          uint32 = 2311003
	PriestDoom                uint32 = 2311005
	PriestSummonDragon        uint32 = 2311006

	RangerMortalBlow uint32 = 3110001
	RangerPuppet     uint32 = 3111002
	RangerSilverHawk uint32 = 3111005

	SniperMortalBlow  uint32 = 3210001
	SniperPuppet      uint32 = 3211002
	SniperBlizzard    uint32 = 3211003
	SniperGoldenEagle uint32 = 3211005

	RogueNimbleBody uint32 = 4001000
	RogueDarkSight  uint32 = 4001003
	RogueDisorder   uint32 = 4001002
	RogueDoubleStab uint32 = 4001334
	RogueLuckySeven uint32 = 4001344

	HermitAlchemist     uint32 = 4110000
	HermitMesoUp        uint32 = 4111001
	HermitShadowPartner uint32 = 4111002
	HermitShadowWeb     uint32 = 4111003
	HermitShadowMeso    uint32 = 4111004
	HermitAvenger       uint32 = 4111005
	HermitFlashJump     uint32 = 4111006

	ShadowerShadowShifter uint32 = 4220002
	ShadowerVenemousStab  uint32 = 4220005

	ShadowerMapleWarrior  uint32 = 4221000
	ShadowerAssassinate   uint32 = 4221001
	ShadowerTaunt         uint32 = 4221003
	ShadowerNinjaAmbush   uint32 = 4221004
	ShadowerSmokeScreen   uint32 = 4221006
	ShadowerBoomerangStep uint32 = 4221007
	ShadowerHerosWill     uint32 = 4221008

	ChiefBanditChakra        uint32 = 4211001
	ChiefBanditAssaulter     uint32 = 4211002
	ChiefBanditPickPocket    uint32 = 4211003
	ChiefBanditBandOfThieves uint32 = 4211004
	ChiefBanditMesoGuard     uint32 = 4211005
	ChiefBanditMesoExplosion uint32 = 4211006

	GMRoar1        uint32 = 9001001
	GMTeleport     uint32 = 9001002
	GMHide         uint32 = 9001004
	GMResurrection uint32 = 9001005
	GMRoar2        uint32 = 9001006
	GMTeleport2    uint32 = 9001007
	GMHyperBody    uint32 = 9001008
	GMAntiMacro    uint32 = 9001009
	GMHaste        uint32 = 9101000
	GMBless        uint32 = 9101003

	SuperGMHealPlusDispel  uint32 = 9101000
	SuperGMHaste           uint32 = 9101001
	SuperGMHolySymbol      uint32 = 9101002
	SuperGMBless           uint32 = 9101003
	SuperGMHide            uint32 = 9101004
	SuperGMResurrection    uint32 = 9101005
	SuperGMSuperDragonRoar uint32 = 9001001
	SuperGMHyperBody       uint32 = 9101008

	MarauderEnergyDrain    uint32 = 5111004
	MarauderTransformation uint32 = 5111005

	FirePoisonArchMagicianBigBang        uint32 = 2121001
	FirePoisonArchMagicianManaReflection uint32 = 2121002
	FirePoisonArchMagicianParalyze       uint32 = 2121006
	FirePoisonArchMagicianMeteorShower   uint32 = 2121007
	FirePoisonArchMagicianElquines       uint32 = 2121005
	FirePoisonArchMagicianFireDemon      uint32 = 2121003
	FirePoisonArchMagicianInfinity       uint32 = 2121004
	FirePoisonArchMagicianMapleWarrior   uint32 = 2121000
	FirePoisonArchMagicianHerosWill      uint32 = 2121008

	IceLightningArchMagicianBigBang        uint32 = 2221001
	IceLightningArchMagicianManaReflection uint32 = 2221002
	IceLightningArchMagicianChainLightning uint32 = 2221006
	IceLightningArchMagicianBlizzard       uint32 = 2221007
	IceLightningArchMagicianIfrit          uint32 = 2221005
	IceLightningArchMagicianIceDemon       uint32 = 2221003
	IceLightningArchMagicianHerosWill      uint32 = 2221008
	IceLightningArchMagicianInfinity       uint32 = 2221004
	IceLightningArchMagicianMapleWarrior   uint32 = 2221000

	BishopMapleWarrior   uint32 = 2321000
	BishopBigBang        uint32 = 2321001
	BishopManaReflection uint32 = 2321002
	BishopBahamut        uint32 = 2321003
	BishopInfinity       uint32 = 2321004
	BishopHolyShield     uint32 = 2321005
	BishopResurrection   uint32 = 2321006
	BishopGenesis        uint32 = 2321008
	BishopHerosWill      uint32 = 2321009

	ArcherFocus uint32 = 3001003

	BowmasterSharpEyes    uint32 = 3121002
	BowmasterBowExpert    uint32 = 3120005
	BowmasterHamstring    uint32 = 3121007
	BowmasterConcentrate  uint32 = 3121008
	BowmasterPhoenix      uint32 = 3121006
	BowmasterHurricane    uint32 = 3121004
	BowmasterHerosWill    uint32 = 3121009
	BowmasterMapleWarrior uint32 = 3121000

	MarksmanSharpEyes     uint32 = 3221002
	MarksmanBoost         uint32 = 3220004
	MarksmanBlind         uint32 = 3221006
	MarksmanSnipe         uint32 = 3221007
	MarksmanFrostPrey     uint32 = 3221005
	MarksmanPiercingArrow uint32 = 3221001
	MarksmanHerosWill     uint32 = 3221008
	MarksmanMapleWarrior  uint32 = 3221000

	NightLordMapleWarrior  uint32 = 4121000
	NightLordShadowShifter uint32 = 4120002
	NightLordTaunt         uint32 = 4121003
	NightLordNinjaAmbush   uint32 = 4121004
	NightLordVenemousStar  uint32 = 4120005
	NightLordShadowStars   uint32 = 4121006
	NightLordTripleThrow   uint32 = 4121007
	NightLordNinjaStorm    uint32 = 4121008
	NightLordHerosWill     uint32 = 4121009

	BuccaneerMapleWarrior        uint32 = 5121000
	BuccaneerEnergyOrb           uint32 = 5121002
	BuccaneerSuperTransformation uint32 = 5121003
	BuccaneerDemolition          uint32 = 5121004
	BuccaneerSnatch              uint32 = 5121005
	BuccaneerBarrage             uint32 = 5121007
	BuccaneerPiratesRage         uint32 = 5121008
	BuccaneerSpeedInfusion       uint32 = 5121009
	BuccaneerTimeLeap            uint32 = 5121010
	BuccaneerDragonStrike        uint32 = 5121001

	CorsairMapleWarrior      uint32 = 5221000
	CorsairElementalBoost    uint32 = 5220001
	CorsairWrathOfTheOctopi  uint32 = 5220002
	CorsairAerialStrike      uint32 = 5221003
	CorsairRapidFire         uint32 = 5221004
	CorsairBattleShip        uint32 = 5221006
	CorsairHypnotize         uint32 = 5221009
	CorsairSpeedInfusion     uint32 = 5221010
	CorsairBullsEye          uint32 = 5220011
	CorsairBattleshipCannon  uint32 = 5221007
	CorsairBattleshipTorpedo uint32 = 5221008

	OutlawOctopus      uint32 = 5211001
	OutlawGaviota      uint32 = 5211002
	OutlawFlameThrower uint32 = 5211004
	OutlawHomingBeacon uint32 = 5211006
	OutlawIceSplitter  uint32 = 5211005

	NoblesseBlessingOfTheFairy uint32 = 10000012
	NoblesseMapChair           uint32 = 10000100
	NoblesseThreeSnails        uint32 = 10001000
	NoblesseRecovery           uint32 = 10001001
	NoblesseNimbleFeet         uint32 = 10001002
	NoblesseMonsterRider       uint32 = 10001004
	NoblesseEchoOfHero         uint32 = 10001005
	NoblesseMaker              uint32 = 10001007
	NoblesseBambooDrain        uint32 = 10001009
	NoblesseInvincibleBarrier  uint32 = 10001010
	NoblessePowerExplosion     uint32 = 10001011
	NoblesseSpaceShip          uint32 = 1001014
	NoblesseSpaceDash          uint32 = 1001015

	NightWalkerAlchemist     uint32 = 14110003
	NightWalkerDisorder      uint32 = 14001002
	NightWalkerDarkSight     uint32 = 14001003
	NightWalkerLuckySeven    uint32 = 14001004
	NightWalkerDarkness      uint32 = 14001005
	NightWalkerClawBooster   uint32 = 14101002
	NightWalkerClawMastery   uint32 = 14100000
	NightWalkerCriticalThrow uint32 = 14100001
	NightWalkerHaste         uint32 = 14101003
	NightWalkerPoisonBomb    uint32 = 14111006
	NightWalkerShadowPartner uint32 = 14111000
	NightWalkerShadowWeb     uint32 = 14111001
	NightWalkerVanish        uint32 = 14100005
	NightWalkerFlashJump     uint32 = 14101004
	NightWalkerVampire       uint32 = 14101006
	NightWalkerVenom         uint32 = 14110004

	DawnWarriorMaxHPEnhancement    uint32 = 11000000
	DawnWarriorIronBody            uint32 = 11001001
	DawnWarriorSoul                uint32 = 11001004
	DawnWarriorSwordMastery        uint32 = 11100000
	DawnWarriorSwordBooster        uint32 = 11101001
	DawnWarriorFinalAttack         uint32 = 11101002
	DawnWarriorRage                uint32 = 11101003
	DawnWarriorIncreasedMPRecovery uint32 = 11110000
	DawnWarriorCombo               uint32 = 11111001
	DawnWarriorPanic               uint32 = 11111002
	DawnWarriorComa                uint32 = 11111003
	DawnWarriorAdvancedCombo       uint32 = 11110005
	DawnWarriorSoulCharge          uint32 = 11111007

	BlazeWizardElementalReset       uint32 = 12101005
	BlazeWizardElementAmplification uint32 = 12110001
	BlazeWizardFireStrike           uint32 = 12111006
	BlazeWizardFlame                uint32 = 12001004
	BlazeWizardFlameGear            uint32 = 12111005
	BlazeWizardIfrit                uint32 = 12111004
	BlazeWizardIncreasingMaxMP      uint32 = 12000000
	BlazeWizardMagicArmor           uint32 = 12001002
	BlazeWizardMagicGuard           uint32 = 12001001
	BlazeWizardMeditation           uint32 = 12101000
	BlazeWizardSeal                 uint32 = 12111002
	BlazeWizardSlow                 uint32 = 12101001
	BlazeWizardSpellBooster         uint32 = 12101004
	BlazeWizardFirePillar           uint32 = 12101006

	WindArcherCriticalShot uint32 = 13000000
	WindArcherFocus        uint32 = 13001002
	WindArcherStorm        uint32 = 13001004
	WindArcherBowMastery   uint32 = 13100000
	WindArcherBowBooster   uint32 = 13101001
	WindArcherFinalAttack  uint32 = 13101002
	WindArcherSoulArrow    uint32 = 13101003
	WindArcherStormBreak   uint32 = 13101005
	WindArcherWindWalk     uint32 = 13101006
	WindArcherHurricane    uint32 = 13111002
	WindArcherPuppet       uint32 = 13111004
	WindArcherEagleEye     uint32 = 13111005
	WindArcherWindPiercing uint32 = 13111006
	WindArcherWindShot     uint32 = 13111007

	ThunderBreakerDash            uint32 = 15001003
	ThunderBreakerImproveMaxHP    uint32 = 15100000
	ThunderBreakerKnucklerMastery uint32 = 15100001
	ThunderBreakerEnergyCharge    uint32 = 15100004
	ThunderBreakerKnucklerBooster uint32 = 15101002
	ThunderBreakerCorkscrewBlow   uint32 = 15101003
	ThunderBreakerLightning       uint32 = 15001004
	ThunderBreakerLightningCharge uint32 = 15101006
	ThunderBreakerEnergyDrain     uint32 = 15111001
	ThunderBreakerTransformation  uint32 = 15111002
	ThunderBreakerSpeedInfusion   uint32 = 15111005
	ThunderBreakerSpark           uint32 = 15111006
	ThunderBreakerSharkWave       uint32 = 15111007
	ThunderBreakerBarrage         uint32 = 15111004

	LegendThreeSnails        uint32 = 20001000
	LegendRecovery           uint32 = 20001001
	LegendAgileBody          uint32 = 20001002
	LegendLegendarySpirit    uint32 = 20001003
	LegendMonsterRider       uint32 = 20001004
	LegendEchoOfHero         uint32 = 20001005
	LegendJumpDown           uint32 = 20001006
	LegendMaker              uint32 = 20001007
	LegendBambooThrust       uint32 = 20001009
	LegendInvincibleBarrier  uint32 = 20001010
	LegendPowerExplosion     uint32 = 20001011
	LegendMeteorShower       uint32 = 20001011
	LegendBlessingOfTheFairy uint32 = 20000012
	LegendTut1               uint32 = 20000014
	LegendTut2               uint32 = 20000015
	LegendTut3               uint32 = 20000016
	LegendTut4               uint32 = 20000017
	LegendTut5               uint32 = 20000018
	LegendMapChair           uint32 = 20000100

	EvanBlessingOfTheFairy uint32 = 20010012
	EvanThreeSnails        uint32 = 20011000
	EvanRecovery           uint32 = 20011001
	EvanNimbleFeet         uint32 = 20011002
	EvanLegendarySpirit    uint32 = 20011003
	EvanMonsterRider       uint32 = 20011004
	EvanJumpDown           uint32 = 20011006
	EvanEchoOfHero         uint32 = 20011005
	EvanMaker              uint32 = 20011007
	EvanBambooThrust       uint32 = 20011009
	EvanInvincibleBarrier  uint32 = 20011010
	EvanPowerExplosion     uint32 = 20011011
	EvanDragonSoul         uint32 = 22000000
	EvanMagicMissle        uint32 = 22001001
	EvanFireCircle         uint32 = 22101000
	EvanTeleport           uint32 = 22101001
	EvanLightningBolt      uint32 = 22111000
	EvanMagicGuard         uint32 = 22111001
	EvanIceBreath          uint32 = 22121000
	EvanElementalReset     uint32 = 22121001
	EvanMagicFlare         uint32 = 22131000
	EvanMagicShield        uint32 = 22131001
	EvanCriticalMagic      uint32 = 22140000
	EvanDragonThrust       uint32 = 22141001
	EvanMagicBooster       uint32 = 22141002
	EvanSlow               uint32 = 22141003
	EvanMagicAmplification uint32 = 22150000
	EvanFireBreath         uint32 = 22151001
	EvanKillerWings        uint32 = 22151002
	EvanMagicResistance    uint32 = 22151003
	EvanDragonFury         uint32 = 22160000
	EvanEarthquake         uint32 = 22161001
	EvanPhantomImprint     uint32 = 22161002
	EvanRecoveryAura       uint32 = 22161003
	EvanMagicMastery       uint32 = 22170001
	EvanMapleWarrior       uint32 = 22171000
	EvanIllusion           uint32 = 22171002
	EvanFlameWheel         uint32 = 22171003
	EvanHerosWill          uint32 = 22171004
	EvanBlessingOfTheOnyx  uint32 = 22181000
	EvanBlaze              uint32 = 22181001
	EvanDarkFog            uint32 = 22181002
	EvanSoulStone          uint32 = 22181003

	AranDoubleSwing      uint32 = 21000002
	AranTripleSwing      uint32 = 21100001
	AranComboAbility     uint32 = 21000000
	AranCombatStep       uint32 = 21001001
	AranPolearmBooster   uint32 = 21001003
	AranMapleWarrior     uint32 = 21121000
	AranFreezeStanding   uint32 = 21121003
	AranSnowCharge       uint32 = 21111005
	AranHerosWill        uint32 = 21121008
	AranHighDefense      uint32 = 21120004
	AranBodyPressure     uint32 = 21101003
	AranComboDrain       uint32 = 21100005
	AranComboSmash       uint32 = 21100004
	AranComboFenrir      uint32 = 21110004
	AranComboCritical    uint32 = 21110000
	AranFullSwing        uint32 = 21110002
	AranRollingSpin      uint32 = 21110006
	AranHiddenFullDouble uint32 = 21110007
	AranHiddenFullTriple uint32 = 21110008
	AranSmartKnockback   uint32 = 21111001
	AranOverSwing        uint32 = 21120002
	AranComboTempest     uint32 = 21120006
	AranComboBarrier     uint32 = 21120007
	AranHiddenOverDouble uint32 = 21120009
	AranHiddenOverTriple uint32 = 21120010
	AranHighMastery      uint32 = 21120001
)

func ElementFromChar(char string) (string, error) {
	switch strings.ToUpper(char) {
	case "F":
		return ElementFire, nil
	case "I":
		return ElementIce, nil
	case "L":
		return ElementLighting, nil
	case "S":
		return ElementPoison, nil
	case "H":
		return ElementHoly, nil
	case "D":
		return ElementDarkness, nil
	case "P":
		return ElementNeutral, nil
	}
	return "", errors.New("unknown element")
}

func IsMapChair(skillId uint32) bool {
	return skillId == BeginnerMapChair || skillId == NoblesseMapChair || skillId == LegendMapChair
}

func getSummonMovementType(skillId uint32) int8 {
	switch skillId {
	case RangerPuppet, SniperPuppet, WindArcherPuppet, OutlawOctopus, CorsairWrathOfTheOctopi:
		return SummonMovementTypeStationary
	case RangerSilverHawk, SniperGoldenEagle, PriestSummonDragon, MarksmanFrostPrey, BowmasterPhoenix, OutlawGaviota:
		return SummonMovementTypeCircleFollow
	case DarkKnightBeholder, FirePoisonArchMagicianElquines, IceLightningArchMagicianIfrit, BishopBahamut, DawnWarriorSoul, BlazeWizardFlame, BlazeWizardIfrit, WindArcherStorm, NightWalkerDarkness, ThunderBreakerLightning:
		return SummonMovementTypeFollow
	default:
		return SummonMovementTypeNone
	}
}
