package skill

import "sync"

type Registry struct {
	registry map[uint32]*Model
	mutex    sync.RWMutex
}

var once sync.Once
var registry *Registry

func GetRegistry() *Registry {
	once.Do(func() {
		registry = initRegistry()
	})
	return registry
}

func initRegistry() *Registry {
	s := &Registry{make(map[uint32]*Model), sync.RWMutex{}}
	return s
}

func (r *Registry) GetSkill(skillId uint32) (*Model, error) {
	r.mutex.RLock()
	if val, ok := r.registry[skillId]; ok {
		r.mutex.RUnlock()
		return val, nil
	}
	r.mutex.RUnlock()

	r.mutex.Lock()
	s, err := Read(skillId)
	if err != nil {
		r.mutex.Unlock()
		return nil, err
	}
	r.registry[skillId] = s
	r.mutex.Unlock()
	return s, nil
}
