package skill

import (
	"atlas-sis/json"
	"github.com/gorilla/mux"
	"github.com/sirupsen/logrus"
	"net/http"
	"strconv"
)

func HandleGetSkillRequest(l logrus.FieldLogger) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		vars := mux.Vars(r)
		skillId, err := strconv.Atoi(vars["skillId"])
		if err != nil {
			l.WithError(err).Errorf("Error parsing skillId as uint32")
			w.WriteHeader(http.StatusBadRequest)
			return
		}

		s, err := GetRegistry().GetSkill(uint32(skillId))
		if err != nil {
			l.WithError(err).Debugf("Unable to locate skill %d.", skillId)
			w.WriteHeader(http.StatusNotFound)
			return
		}

		result := dataContainer{
			Data: dataBody{
				Id:   strconv.Itoa(int(s.Id())),
				Type: "",
				Attributes: attributes{
					Action:        s.Action(),
					Element:       s.Element(),
					AnimationTime: s.AnimationTime(),
					Effects:       makeEffects(s.Effects()),
				},
			},
		}

		w.WriteHeader(http.StatusOK)
		err = json.ToJSON(result, w)
		if err != nil {
			l.WithError(err).Errorf("Writing response.")
		}
		return
	}
}

func makeEffects(es []Effect) []effects {
	results := make([]effects, 0)
	for _, e := range es {
		result := effects{
			WeaponAttack:         e.weaponAttack,
			MagicAttack:          e.magicAttack,
			WeaponDefense:        e.weaponDefense,
			MagicDefense:         e.magicDefense,
			Accuracy:             e.accuracy,
			Avoidability:         e.avoidability,
			Speed:                e.speed,
			Jump:                 e.jump,
			HP:                   e.hp,
			MP:                   e.mp,
			HPR:                  e.hpr,
			MPR:                  e.mpr,
			MHPRRate:             e.mhprRate,
			MMPRRate:             e.mmprRate,
			MobSkill:             e.mobSkill,
			MobSkillLevel:        e.mobSkillLevel,
			MHPR:                 e.mhpR,
			MMPR:                 e.mmpR,
			HPCon:                e.hpCon,
			MPCon:                e.mpCon,
			Duration:             e.duration,
			Target:               e.target,
			Barrier:              e.barrier,
			Mob:                  e.mob,
			OverTime:             e.overtime,
			RepeatEffect:         e.repeatEffect,
			MoveTo:               e.moveTo,
			CP:                   e.cp,
			NuffSkill:            e.nuffSkill,
			Skill:                e.skill,
			X:                    e.x,
			Y:                    e.y,
			MobCount:             e.mobCount,
			MoneyCon:             e.moneyCon,
			Cooldown:             e.cooldown,
			MorphId:              e.morphId,
			Ghost:                e.ghost,
			Fatigue:              e.fatigue,
			Berserk:              e.berserk,
			Booster:              e.booster,
			Prop:                 e.prop,
			ItemCon:              e.itemCon,
			ItemConNo:            e.itemConNo,
			Damage:               e.damage,
			AttackCount:          e.attackCount,
			FixDamage:            e.fixDamage,
			BulletCount:          e.bulletCount,
			BulletConsume:        e.bulletConsume,
			MapProtection:        e.mapProtection,
			CureAbnormalStatuses: e.cureAbnormalStatuses,
			Statups:              makeStatups(e.statups),
			MonsterStatus:        makeMonsterStatus(e.monsterStatus),
			CardStats:            cardItemUp{},
		}
		results = append(results, result)
	}
	return results
}

func makeMonsterStatus(status map[string]uint32) []monsterStatus {
	results := make([]monsterStatus, 0)
	for k, v := range status {
		results = append(results, monsterStatus{Status: k, Value: v})
	}
	return results
}

func makeStatups(statups []Statup) []buffStatAmount {
	results := make([]buffStatAmount, 0)
	for _, s := range statups {
		result := buffStatAmount{
			Buff:   s.buff,
			Amount: s.amount,
		}
		results = append(results, result)
	}
	return results
}
