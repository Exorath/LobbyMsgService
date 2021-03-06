/*
 * Copyright 2016 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.service.lobbymsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by toonsev on 11/1/2016.
 */
public class PortProvider {
    public static final String PORT_ENV_VAR = "PORT";
    private static final Logger LOG = LoggerFactory.getLogger(PortProvider.class);

    private int defaultPort;

    public PortProvider(int defaultPort){
        this.defaultPort = defaultPort;
    }

    public int getPort(){
        int port = defaultPort;
        try {
            if (System.getenv(PORT_ENV_VAR) != null)
                port = Integer.parseInt(System.getenv(PORT_ENV_VAR));
        } catch (NumberFormatException e) {
            LOG.warn("Using default port " + port, e);
        }
        return port;
    }
}
