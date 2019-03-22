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

package com.oasis.datareciever.service;

import com.oasis.datareciever.dto.CreateEdgeDTO;
import com.oasis.datareciever.dto.CreateGraphDTO;
import com.oasis.datareciever.dto.CreateVertexDTO;
import io.vavr.control.Try;
import org.neo4j.driver.v1.AccessMode;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mate Thurzo
 */
@Service
public class Neo4jService {

    @PostConstruct
    public void init() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "test"));
    }

    public void createGraph(final String graphId, CreateGraphDTO dto) {
        final Stream<String> vertexStream = dto.getVerticies().stream()
                .map(this::getCreateVertexCypher);

        run(() -> Stream.concat(Stream.of(getCreateGraphCypher(graphId)), vertexStream)
                .collect(Collectors.toList()));

        run(() -> dto.getEdges().stream().map(this::getCreateEdgeCypher).collect(Collectors.toList()));
    }

    public boolean hasGraphId(String graphId) {
        try (Session session = getSession()) {
            StatementResult result = session.run(HAS_GRAPH_ID_TPL.replace("[$GRAPH_ID$]", graphId));

            return result.stream()
                    .findFirst()
                    .map((record) -> record.get(0).asInt() > 0).get();
        }
    }

    public Session getSession() {
        return this.driver.session(AccessMode.READ);
    }

    private void run(Supplier<List<String>> supplier) {
        try (Session session = this.driver.session()) {
            Transaction transaction = session.beginTransaction();

            try {
                supplier.get().forEach(transaction::run);

                transaction.success();
            } catch (Exception e) {
                transaction.failure();
            } finally {
                transaction.close();
            }
        }
    }

    private String getCreateVertexCypher(CreateVertexDTO dto) {
        final StringBuilder sb = new StringBuilder();

        dto.getVertexProperties()
                .forEach((key, value) -> sb.append(", ").append(key).append(": '").append(value).append("'"));

        return CREATE_VERTEX_TPL
                .replace("[$VERTEX_TYPE$]", dto.getVertexType())
                .replace("[$VERTEX_ID$]", dto.getVertexId())
                .replace("[$VERTEX_PROPS$]", sb.toString());
    }

    private String getCreateEdgeCypher(CreateEdgeDTO dto) {
        final StringBuilder sb = new StringBuilder();

        Optional<List<CreateVertexDTO>> outboundVerticies = Optional.ofNullable(dto.getOutboundVertexIds());

        outboundVerticies.ifPresent(
                outboundVerticiesList -> outboundVerticiesList.forEach(outboundVertex -> sb.append(CREATE_EDGE_TPL
                            .replace("[$VERTEX_TYPE1$]", dto.getVertexType())
                            .replace("[$VERTEX_ID1$]", dto.getVertexId())
                            .replace("[$VERTEX_TYPE2$]", outboundVertex.getVertexType())
                            .replace("[$VERTEX_ID2$]", outboundVertex.getVertexId())
                            .replace("[$REL$]", "-[:REL]->"))));

        Optional<List<CreateVertexDTO>> inboundVerticies = Optional.ofNullable(dto.getInboundVertexIds());

        inboundVerticies.ifPresent(
                inboundVerticiesList -> inboundVerticiesList.forEach(outboundVertex -> sb.append(CREATE_EDGE_TPL
                        .replace("[$VERTEX_TYPE1$]", dto.getVertexType())
                        .replace("[$VERTEX_ID1$]", dto.getVertexId())
                        .replace("[$VERTEX_TYPE2$]", outboundVertex.getVertexType())
                        .replace("[$VERTEX_ID2$]", outboundVertex.getVertexId())
                        .replace("[$REL$]", "<-[:REL]-"))));

        return sb.toString();
    }

    private String getCreateGraphCypher(String graphId) {
        return CREATE_GRAPH_TPL.replace("[$GRAPH_ID$]", graphId);
    }

    private Driver driver;

    private static final String CREATE_GRAPH_TPL = "create (g:Graph {graphId: '[$GRAPH_ID$]'}) return g.graphId";
    private static final String CREATE_VERTEX_TPL = "merge (v:[$VERTEX_TYPE$] {vertexId: '[$VERTEX_ID$]' [$VERTEX_PROPS$]})";
    private static final String CREATE_EDGE_TPL = "match (v1:[$VERTEX_TYPE1$] {vertexId: '[$VERTEX_ID1$]'}), " +
            "(v2:[$VERTEX_TYPE2$] {vertexId: '[$VERTEX_ID2$]'}) merge (v1)[$REL$](v2);";
    private static final String HAS_GRAPH_ID_TPL = "match (g:Graph) where exists(g.graphId) and g.graphId = '[$GRAPH_ID$]' return count(g)";

    private static final Logger logger = LoggerFactory.getLogger(Neo4jService.class);

}
