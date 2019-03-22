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
import io.vavr.Tuple;

import java.util.List;

/**
 * @author Mate Thurzo
 */
@JsonRootName("edge")
public class CreateEdgeDTO {

    public String getVertexId() {
        return vertexId;
    }

    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    public String getVertexType() {
        return vertexType;
    }

    public void setVertexType(String vertexType) {
        this.vertexType = vertexType;
    }

    public List<CreateVertexDTO> getOutboundVertexIds() {
        return outboundVertexIds;
    }

    public void setOutboundVertexIds(List<CreateVertexDTO> outboundVertexIds) {
        this.outboundVertexIds = outboundVertexIds;
    }

    public List<CreateVertexDTO> getInboundVertexIds() {
        return inboundVertexIds;
    }

    public void setInboundVertexIds(List<CreateVertexDTO> inboundVertexIds) {
        this.inboundVertexIds = inboundVertexIds;
    }

    @JsonProperty
    private String vertexId;

    @JsonProperty
    private String vertexType;

    @JsonProperty
    private List<CreateVertexDTO> outboundVertexIds;

    @JsonProperty
    private List<CreateVertexDTO> inboundVertexIds;

}
