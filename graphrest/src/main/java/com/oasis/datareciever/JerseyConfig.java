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

package com.oasis.datareciever;

import com.oasis.datareciever.resource.EdgeResource;
import com.oasis.datareciever.resource.GraphResource;
import com.oasis.datareciever.resource.VertexResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * @author Mate Thurzo
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(GraphResource.class);
        register(EdgeResource.class);
        register(VertexResource.class);
    }

}
