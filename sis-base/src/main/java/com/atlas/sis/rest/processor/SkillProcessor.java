package com.atlas.sis.rest.processor;

import com.app.rest.util.stream.Mappers;
import com.atlas.sis.SkillRegistry;

import builder.ResultBuilder;

public final class SkillProcessor {
   public SkillProcessor() {
   }

   public static ResultBuilder getSkill(int skillId) {
      return SkillRegistry.getInstance().getSkill(skillId)
            .map(ResultObjectFactory::create)
            .map(Mappers::singleOkResult)
            .orElseGet(ResultBuilder::notFound);
   }
}
