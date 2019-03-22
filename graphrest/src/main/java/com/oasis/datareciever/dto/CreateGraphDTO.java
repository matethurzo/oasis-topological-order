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

package com.oasis.datareciever.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * @author Mate Thurzo
 */
@JsonRootName("graph")
public class CreateGraphDTO {

    public List<CreateVertexDTO> getVerticies() {
        return verticies;
    }

    public void setVerticies(List<CreateVertexDTO> verticies) {
        this.verticies = verticies;
    }

    public List<CreateEdgeDTO> getEdges() {
        return edges;
    }

    public void setEdges(List<CreateEdgeDTO> edges) {
        this.edges = edges;
    }

    @JsonProperty
    private List<CreateVertexDTO> verticies;

    @JsonProperty
    private List<CreateEdgeDTO> edges;

}
