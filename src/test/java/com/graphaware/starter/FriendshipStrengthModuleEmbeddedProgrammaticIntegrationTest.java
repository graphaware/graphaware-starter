/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.starter;

import com.graphaware.starter.module.FriendshipStrengthCounter;
import com.graphaware.starter.module.FriendshipStrengthModule;
import com.graphaware.runtime.GraphAwareRuntime;
import com.graphaware.runtime.GraphAwareRuntimeFactory;
import com.graphaware.test.integration.DatabaseIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.*;

import static org.junit.Assert.assertEquals;


/**
 * Test for {@link FriendshipStrengthCounter}.
 */
public class FriendshipStrengthModuleEmbeddedProgrammaticIntegrationTest extends DatabaseIntegrationTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();

        GraphAwareRuntime runtime = GraphAwareRuntimeFactory.createRuntime(getDatabase());
        runtime.registerModule(new FriendshipStrengthModule("FSM", getDatabase()));
        runtime.start();
    }

    @Test
    public void totalFriendshipStrengthOnEmptyDatabaseShouldBeZero() {
        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(0, new FriendshipStrengthCounter(getDatabase()).getTotalFriendshipStrength());
            tx.success();
        }
    }

    @Test
    public void totalFriendshipStrengthShouldBeCorrectlyCalculated() {
        getDatabase().execute("CREATE " +
                "(p1:Person)-[:FRIEND_OF {strength:2}]->(p2:Person)," +
                "(p1)-[:FRIEND_OF {strength:1}]->(p3:Person)");

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(3, new FriendshipStrengthCounter(getDatabase()).getTotalFriendshipStrength());
            tx.success();
        }
    }

    @Test
    public void totalFriendshipStrengthShouldBeCorrectlyCalculated2() {
        try (Transaction tx = getDatabase().beginTx()) {
            Node p1 = getDatabase().createNode(DynamicLabel.label("Person"));
            Node p2 = getDatabase().createNode(DynamicLabel.label("Person"));
            Node p3 = getDatabase().createNode(DynamicLabel.label("Person"));
            p1.createRelationshipTo(p2, DynamicRelationshipType.withName("FRIEND_OF")).setProperty("strength", 1L);
            p1.createRelationshipTo(p3, DynamicRelationshipType.withName("FRIEND_OF")).setProperty("strength", 2L);
            tx.success();
        }

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(3, new FriendshipStrengthCounter(getDatabase()).getTotalFriendshipStrength());
            tx.success();
        }
    }
}
