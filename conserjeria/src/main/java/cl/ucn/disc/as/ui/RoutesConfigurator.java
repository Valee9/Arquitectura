/*
 * Copyright (c) 2023. Software Architecture, DISC, UCN.
 */
package cl.ucn.disc.as.ui;

import io.javalin.Javalin;
/**
 * The Routes config.
 *
 * @author Software Architecture.
 */
public interface RoutesConfigurator {
    /**
     * Configure the routes
     *
     * @param javalin to use.
     */
    void configure(Javalin javalin);
}

