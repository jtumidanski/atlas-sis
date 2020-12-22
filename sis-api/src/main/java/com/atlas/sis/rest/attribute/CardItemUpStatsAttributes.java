package com.atlas.sis.rest.attribute;

import java.util.List;

import rest.AttributeResult;

public record CardItemUpStatsAttributes(Integer itemCode, Integer probability, List<AreaAttributes> areas, Boolean inParty)
      implements AttributeResult {
}
