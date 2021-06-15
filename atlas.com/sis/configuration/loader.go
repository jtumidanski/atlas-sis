package configuration

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"os"
)

func loadConfiguration() (*Configuration, error) {
	fn := "config.yaml"
	if val, ok := os.LookupEnv("CONFIG_FILE"); ok {
		fn = val
	}
	yamlFile, err := ioutil.ReadFile(fn)
	if err != nil {
		return nil, err
	}

	con := &Configuration{}
	err = yaml.Unmarshal(yamlFile, con)
	if err != nil {
		return nil, err
	}
	return con, nil
}
