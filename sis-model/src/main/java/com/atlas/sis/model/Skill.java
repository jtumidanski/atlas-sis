package com.atlas.sis.model;

import java.util.List;

public record Skill(int id, Element element, boolean action, int animationTime, List<StatEffect> effects) {
}
