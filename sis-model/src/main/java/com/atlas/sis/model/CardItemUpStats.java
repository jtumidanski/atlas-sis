package com.atlas.sis.model;

import java.util.List;

public record CardItemUpStats(Integer itemCode, Integer probability, List<Area> areas,
                              Boolean inParty) {
   public Boolean isInArea(Integer mapId) {
      if (areas == null) {
         return true;
      }
      return areas.stream().anyMatch(pair -> mapId >= pair.start() && mapId <= pair.end());
   }
}
