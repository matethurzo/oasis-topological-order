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
@JsonRootName("relationship")
public class CreateRelationshipDTO {

    public String getVertexId() {
        return vertexId;
    }

    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    public List<String> getOutboundVertexIds() {
        return outboundVertexIds;
    }

    public void setOutboundVertexIds(List<String> outboundVertexIds) {
        this.outboundVertexIds = outboundVertexIds;
    }

    public List<String> getInboundVertexIds() {
        return inboundVertexIds;
    }

    public void setInboundVertexIds(List<String> inboundVertexIds) {
        this.inboundVertexIds = inboundVertexIds;
    }

    @JsonProperty
    private String vertexId;

    @JsonProperty
    private List<String> outboundVertexIds;

    @JsonProperty
    private List<String> inboundVertexIds;

}
