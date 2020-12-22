package com.atlas.sis.rest.builder;

import java.util.List;

import com.app.common.builder.RecordBuilder;
import com.atlas.sis.rest.attribute.SkillAttributes;
import com.atlas.sis.rest.attribute.StatEffectAttributes;

import builder.AttributeResultBuilder;

public class SkillAttributesBuilder extends RecordBuilder<SkillAttributes, SkillAttributesBuilder>
      implements AttributeResultBuilder {
   private Boolean action;

   private String element;

   private Integer animationTime;

   private List<StatEffectAttributes> effects;

   @Override
   public SkillAttributes construct() {
      return new SkillAttributes(action, element, animationTime, effects);
   }

   @Override
   public SkillAttributesBuilder getThis() {
      return this;
   }

   public SkillAttributesBuilder setAction(Boolean action) {
      this.action = action;
      return getThis();
   }

   public SkillAttributesBuilder setElement(String element) {
      this.element = element;
      return getThis();
   }

   public SkillAttributesBuilder setAnimationTime(Integer animationTime) {
      this.animationTime = animationTime;
      return getThis();
   }

   public SkillAttributesBuilder setEffects(List<StatEffectAttributes> effects) {
      this.effects = effects;
      return getThis();
   }
}
