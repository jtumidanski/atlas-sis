package skill

type Model struct {
	id            uint32
	action        bool
	element       string
	animationTime uint32
	effects       []Effect
}

type Effect struct {
	weaponAttack  uint16
	magicAttack   uint16
	weaponDefense uint16
	magicDefense  uint16
	accuracy      uint16
	avoidability  uint16
	speed         uint16
	jump          uint16
	hp            uint16
	mp            uint16
	hpr           float64
	mpr           float64
	mhprRate      uint16
	mmprRate      uint16
	mobSkill      uint16
	mobSkillLevel uint16
	mhpR          byte
	mmpR          byte
	hpCon         uint16
	mpCon         uint16
	duration      int32
	target        uint32
	barrier       uint32
	mob           uint32
	overtime      bool
	repeatEffect  bool
	moveTo        int32
	cp            uint32
	nuffSkill     uint32
	skill         bool
	x             int16
	y             int16
	mobCount      uint32
	moneyCon      uint32
	cooldown      uint32
	morphId       uint32
	ghost         uint32
	fatigue       uint32
	berserk       uint32
	booster       uint32
	prop          float64
	itemCon       uint32
	itemConNo     uint32
	damage        uint32
	attackCount   uint32
	fixDamage     int32
	//LT Point
	//RB Point
	bulletCount          uint16
	bulletConsume        uint16
	mapProtection        byte
	cureAbnormalStatuses []string
	statups              []Statup
	monsterStatus        map[string]uint32
}

func (e Effect) Duration() int32 {
	return e.duration
}

func (e Effect) StatUps() []Statup {
	return e.statups
}

func (e Effect) HP() uint16 {
	return e.hp
}

func (m Model) Effects() []Effect {
	return m.effects
}

func (m Model) Id() uint32 {
	return m.id
}

func (m Model) Action() bool {
	return m.action
}

func (m Model) Element() string {
	return m.element
}

func (m Model) AnimationTime() uint32 {
	return m.animationTime
}

type Statup struct {
	buff   string
	amount uint32
}

func (s Statup) Mask() string {
	return s.buff
}

func (s Statup) Amount() uint32 {
	return s.amount
}
