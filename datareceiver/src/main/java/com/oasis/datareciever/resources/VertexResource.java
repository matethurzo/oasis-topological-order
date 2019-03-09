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

package com.oasis.datareciever.resources;

import com.oasis.datareciever.dto.CreateVertexDTO;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
@Path("/graph/vertex")
@Component
public class VertexResource {

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void create(CreateVertexDTO dto) {
        System.out.println(dto.getVertexId());
    }

    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String helloVertex() {
        return "Hello Vertex";
    }

    @Path("/helloDTO")
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

}
