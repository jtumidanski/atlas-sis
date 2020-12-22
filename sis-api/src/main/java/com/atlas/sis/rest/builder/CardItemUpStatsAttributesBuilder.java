package com.atlas.sis.rest.builder;

import java.util.List;

import com.app.common.builder.RecordBuilder;
import com.atlas.sis.rest.attribute.AreaAttributes;
import com.atlas.sis.rest.attribute.CardItemUpStatsAttributes;

import builder.AttributeResultBuilder;

public class CardItemUpStatsAttributesBuilder extends RecordBuilder<CardItemUpStatsAttributes, CardItemUpStatsAttributesBuilder>
      implements AttributeResultBuilder {
   private Integer itemCode;

   private Integer probability;

   private List<AreaAttributes> areas;

   private Boolean inParty;

   @Override
   public CardItemUpStatsAttributes construct() {
      return new CardItemUpStatsAttributes(itemCode, probability, areas, inParty);
   }

   @Override
   public CardItemUpStatsAttributesBuilder getThis() {
      return this;
   }

   public CardItemUpStatsAttributesBuilder setItemCode(Integer itemCode) {
      this.itemCode = itemCode;
      return getThis();
   }

   public CardItemUpStatsAttributesBuilder setProbability(Integer probability) {
      this.probability = probability;
      return getThis();
   }

   public CardItemUpStatsAttributesBuilder setAreas(List<AreaAttributes> areas) {
      this.areas = areas;
      return getThis();
   }

   public CardItemUpStatsAttributesBuilder setInParty(Boolean inParty) {
      this.inParty = inParty;
      return getThis();
   }
}
