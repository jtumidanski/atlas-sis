package com.atlas.sis.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlas.sis.rest.processor.SkillProcessor;

@Path("skills")
public class SkillResource {
   @GET
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response getSkills(@PathParam("id") Integer skillId) {
      return SkillProcessor.getSkill(skillId).build();
   }
}
