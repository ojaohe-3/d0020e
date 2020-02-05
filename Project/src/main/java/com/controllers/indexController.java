package com.controllers;

import SpecificInterfaces.UserInterface;
import neo4j_JVM_API.Neo4JAPI;

public class indexController extends UserInterface {
    /**
     * Constructor
     *
     * @param neoapi
     */
    public indexController(Neo4JAPI neoapi) {
        super(neoapi);
    }
}
