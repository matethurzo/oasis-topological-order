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

import com.oasis.datareciever.dto.CreateEdgeDTO;
import com.oasis.datareciever.dto.CreateGraphDTO;
import com.oasis.datareciever.dto.CreateVertexDTO;
import com.oasis.datareciever.service.Neo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.oasis.datareciever.dto.validation.Validations.graphIdTaken;
import static com.oasis.datareciever.dto.validation.Validations.invalidString;

/**
 * @author Mate Thurzo
 */
@Component
@Path("/graph")
public class GraphResource {

    @Autowired
    public GraphResource(Neo4jService neo4jService) {
        this.neo4jService = neo4jService;
    }

    @Path("/{graphId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response generateGraph(@PathParam("graphId") final String graphId, final CreateGraphDTO dto) {
        return isInvalidGraph(graphId, dto)
                .orElseGet(() -> {
                    neo4jService.createGraph(graphId, dto);

                    return Response.ok().build();
                });
    }

    @Path("/{graphId}/coffmanGraham")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response coffmanGraham(@PathParam("graphId") String graphId, CreateGraphDTO dto) {
        return isInvalidGraph(graphId, dto)
                .orElseGet(() -> {
                    return Response.ok().build();
                });
    }

    @Path("/helloGraphDTO")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public CreateGraphDTO helloGraphDTO() {
        CreateGraphDTO dto = new CreateGraphDTO();

        dto.setVerticies(getVerticies());

        dto.setEdges(getEdges());

        return dto;
    }

    private Optional<Response> isInvalidGraph(String graphId, CreateGraphDTO dto) {
        Optional<Response> validateResponse = null;

        if (invalidString.test(graphId)) {
            validateResponse = Optional.of(
                    Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity("Bad graph id: " + graphId)
                        .build());
        }

        if (graphIdTaken.test(graphId, this.neo4jService)) {
            validateResponse = Optional.of(
                    Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity("Graph id has been already taken: " + graphId)
                        .build());
        }

        if (validateResponse ==  null) {
            validateResponse = Optional.empty();
        }

        return validateResponse;
    }

    private List<CreateEdgeDTO> getEdges() {
        List<CreateEdgeDTO> edges = new ArrayList<>();

        CreateEdgeDTO dto = new CreateEdgeDTO();

        dto.setVertexId("1234");

        CreateVertexDTO vertexDTO = new CreateVertexDTO();

        vertexDTO.setVertexId("5678");
        vertexDTO.setVertexType("JournalArticle");

        dto.setOutboundVertexIds(Collections.singletonList(vertexDTO));

        edges.add(dto);

        return edges;
    }

    private List<CreateVertexDTO> getVerticies() {
        List<CreateVertexDTO> verticies = new ArrayList<>();

        CreateVertexDTO dto = new CreateVertexDTO();

        dto.setVertexId("1234");
        dto.setVertexType("JournalArticle");

        Map<String, String> map = new HashMap<>();

        map.put("testProp", "asd");
        map.put("testProp2", "35");

        dto.setVertexProperties(map);

        verticies.add(dto);

        dto = new CreateVertexDTO();

        dto.setVertexId("5678");
        dto.setVertexType("JournalArticle");

        map = new HashMap<>();

        map.put("testProp", "qwe");
        map.put("testProp2", "98");

        dto.setVertexProperties(map);

        verticies.add(dto);

        return verticies;
    }

    private Neo4jService neo4jService;

}
