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
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mate Thurzo
 */
@Path("/graph/edge")
@Component
public class EdgeResource {

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response create(CreateEdgeDTO dto) {
        return Response.status(Response.Status.OK).build();
    }

    @Path("/helloRelationshipDTO")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public CreateEdgeDTO helloRelationshipDTO() {
        CreateEdgeDTO dto = new CreateEdgeDTO();

        dto.setVertexId("articleId");

        List<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");

        //dto.setInboundVertexIds(list);
        //dto.setOutboundVertexIds(list);

        return dto;
    }

}
