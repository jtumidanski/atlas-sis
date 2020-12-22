package com.atlas.sis.rest.attribute;

import java.util.List;

import rest.AttributeResult;

public record SkillAttributes(Boolean action, String element, Integer animationTime, List<StatEffectAttributes> effects)
      implements AttributeResult {
}
