<a name="top"/>

GraphAware Starter
==================

[![Build Status](https://travis-ci.org/graphaware/graphaware-starter.png)](https://travis-ci.org/graphaware/graphaware-starter)

This repository will get you started quickly with the [GraphAware Framework](https://github.com/graphaware/neo4j-framework).

### Build

In order to start building applications on top of Neo4j,
clone this repository and make sure you can build it using Maven `mvn clean package`.

### Explore and Code

Then explore the code and replace it with your own. The code is a simple "Friendship Strength Counter" which

* tracks the sum of all friendship strengths in the graph using a dedicated node and a Transaction-Driven Framework Module
* exposes is using a REST API built using the Framework

### Deploy & Run

In order to deploy and run the code, you need to have the GraphAware Framework installed in Neo4j (follow the instructions [here](https://github.com/graphaware/neo4j-framework#server-mode) and [here](https://github.com/graphaware/neo4j-framework/tree/master/runtime#using-graphaware-runtime-server-mode))
and then place the .jar file produced by running `mvn clean package` into the `plugins` directory of Neo4j as well. You
will find this file in the `target` folder. The one you want is the one prefixed with `graphaware-`, i.e., the one with
dependencies bundled in.

