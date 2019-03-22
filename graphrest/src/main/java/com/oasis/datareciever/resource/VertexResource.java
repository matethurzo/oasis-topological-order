/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.oasis.datareciever.resource;

import static com.oasis.datareciever.dto.validation.Validations.invalidString;
import static com.oasis.datareciever.dto.validation.Validations.invalidVertexType;

import com.oasis.datareciever.dto.CreateVertexDTO;
import com.oasis.datareciever.dto.validation.Validations;
import com.oasis.datareciever.service.Neo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
@Component
@Path("/graph/vertex")
public class VertexResource {

    @Autowired
    public VertexResource(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response create(CreateVertexDTO dto) {
        Validations.Valid valid = validateVertex(dto);

        if (!valid.isValid()) {
           return Response
                   .status(Response.Status.BAD_REQUEST)
                   .entity(valid.getReason())
                   .build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @Path("/helloVertexDTO")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public CreateVertexDTO helloVertexDTO() {
        CreateVertexDTO dto = new CreateVertexDTO();

        dto.setVertexId("1234");
        dto.setVertexType("JournalArticle");

        Map<String, String> map = new HashMap<>();

        map.put("test Prop", "asd");
        map.put("test Prop2", "35");

        dto.setVertexProperties(map);

        return dto;
    }

    private Validations.Valid validateVertex(CreateVertexDTO dto) {
        if (invalidString.test(dto.getVertexId())) {
            return Validations.failure("Invalid vertex id. Vertex id should not be empty or null");
        }

        if (invalidString.and(invalidVertexType).test(dto.getVertexType())) {
            return Validations.failure("Invalid vertex type. Vertex type should not be empty or null and should not contain space.");
        }

        if (dto.getVertexProperties() == null || dto.getVertexProperties().size() == 0) {
            return Validations.success();
        }

        boolean isInvalid = dto.getVertexProperties()
                .entrySet()
                .stream()
                .anyMatch(entry -> invalidString.test(entry.getKey()) || invalidString.test(entry.getValue()));

        if (isInvalid) {
            return Validations.failure("Invalid properties for vertex");
        } else {
            return Validations.success();
        }
    }

    private final Neo4jService neo4jService;

}
