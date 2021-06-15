package configuration

import (
	log "github.com/sirupsen/logrus"
	"sync"
)

type Registry struct {
	c *Configuration
}

var once sync.Once
var registry *Registry

func Get() *Configuration {
	once.Do(func() {
		c, err := loadConfiguration()
		if err != nil {
			log.WithError(err).Fatalf("Retrieving configuration for service.")
		}
		registry = &Registry{
			c: c,
		}
	})
	return registry.c
}

type Configuration struct {
	UseUltraNimbleFeet    bool `yaml:"useUltraNimbleFeet"`
	UseMaxRangeEchoOfHero bool `yaml:"useMaxRangeEchoOfHero"`
	UseUltraRecovery      bool `yaml:"useUltraRecovery"`
}
