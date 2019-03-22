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

package com.oasis.datareciever.dto.validation;

import com.oasis.datareciever.service.Neo4jService;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @author Mate Thurzo
 */
public class Validations {

    public static Valid success() {
        return new Valid(true, null);
    }

    public static Valid failure(String reason) {
        return new Valid(false, reason);
    }

    public static final Predicate<String> invalidKey = (string) -> string.contains(" ");
    public static final Predicate<String> invalidString = (string) -> string == null || "".equals(string.trim());
    public static final Predicate<String> invalidVertexType = (vertexType) -> invalidString.and(invalidKey).test(vertexType);
    public static final Predicate<String> invalidVertexId = invalidString;
    public static final BiPredicate<String, Neo4jService> graphIdTaken = (string, neo4j) -> neo4j.hasGraphId(string);

    public static class Valid {

        private Valid(boolean valid, String reason) {
            this.valid = valid;
            this.reason = reason;
        }

        public boolean isValid() {
            return valid;
        }

        public String getReason() {
            return reason;
        }

        private boolean valid;
        private String reason;

    }

}
