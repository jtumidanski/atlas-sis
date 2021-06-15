package xml

import (
	"encoding/xml"
	"errors"
	"strconv"
)

type Node struct {
	XMLName      xml.Name      `xml:"imgdir"`
	Name         string        `xml:"name,attr"`
	ChildNodes   []Node        `xml:"imgdir"`
	IntegerNodes []IntegerNode `xml:"int"`
	StringNodes  []StringNode  `xml:"string"`
}

func (i *Node) ChildByName(name string) (*Node, error) {
	for _, c := range i.ChildNodes {
		if c.Name == name {
			return &c, nil
		}
	}
	return nil, errors.New("child not found")
}

func (i *Node) GetShort(name string, def uint16) uint16 {
	for _, c := range i.IntegerNodes {
		if c.Name == name {
			res, err := strconv.ParseUint(c.Value, 10, 16)
			if err != nil {
				return def
			}
			return uint16(res)
		}
	}
	return def
}

func (i *Node) GetString(name string, def string) string {
	for _, c := range i.StringNodes {
		if c.Name == name {
			return c.Value
		}
	}
	return def
}

func (i *Node) GetInteger(name string) (int32, error) {
	for _, c := range i.IntegerNodes {
		if c.Name == name {
			res, err := strconv.ParseUint(c.Value, 10, 32)
			if err != nil {
				return 0, err
			}
			return int32(res), nil
		}
	}
	return 0, errors.New("node not found")
}

func (i *Node) GetIntegerWithDefault(name string, def int32) int32 {
	for _, c := range i.IntegerNodes {
		if c.Name == name {
			res, err := strconv.ParseUint(c.Value, 10, 32)
			if err != nil {
				return def
			}
			return int32(res)
		}
	}
	return def
}

type IntegerNode struct {
	Name  string `xml:"name,attr"`
	Value string `xml:"value,attr"`
}

type StringNode struct {
	Name  string `xml:"name,attr"`
	Value string `xml:"value,attr"`
}
