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
import com.graphaware.test.integration.DatabaseIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Test for {@link FriendshipStrengthCounter}.
 */
public class FriendshipStrengthModuleEmbeddedDeclarativeIntegrationTest extends DatabaseIntegrationTest {

    @Override
    protected String propertiesFile() {
        try {
            return new ClassPathResource("neo4j-friendship.properties").getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
