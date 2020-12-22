package com.atlas.sis.builder;

import java.util.ArrayList;
import java.util.List;

import com.app.common.builder.RecordBuilder;
import com.atlas.sis.model.Element;
import com.atlas.sis.model.Skill;
import com.atlas.sis.model.StatEffect;

public class SkillBuilder extends RecordBuilder<Skill, SkillBuilder> {
   private final Integer id;

   private final List<StatEffect> effects;

   private Element element;

   private Boolean action = false;

   private Integer animationTime;

   public SkillBuilder(Integer id) {
      this.id = id;
      this.effects = new ArrayList<>();
   }

   public SkillBuilder(String id) {
      this(Integer.parseInt(id));
   }

   @Override
   public SkillBuilder getThis() {
      return this;
   }

   @Override
   public Skill construct() {
      return new Skill(id, element, action, animationTime, effects);
   }

   public SkillBuilder setElement(Element element) {
      this.element = element;
      return this;
   }

   public SkillBuilder setAction(Boolean action) {
      this.action = action;
      return this;
   }

   public SkillBuilder setAnimationTime(int time) {
      animationTime = time;
      return this;
   }

   public SkillBuilder addLevelEffect(StatEffect statEffect) {
      effects.add(statEffect);
      return this;
   }
}
