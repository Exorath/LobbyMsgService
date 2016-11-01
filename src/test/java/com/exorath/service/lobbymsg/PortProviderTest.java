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

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.junit.Assert.assertEquals;

/**
 * Created by toonsev on 11/1/2016.
 */
public class PortProviderTest {
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void getPortReturnsDefaultPortByDefaultTest(){
        int actual = new PortProvider(1234).getPort();
        assertEquals(1234, actual);
    }
    @Test
    public void getPortReturnsEnvVarDefinedPortIfSetTest(){
        environmentVariables.set(PortProvider.PORT_ENV_VAR, "8080");
        int actual = new PortProvider(1234).getPort();
        assertEquals(8080, actual);
    }

    @Test
    public void getPortWithMissformedEnvVarFallsBackToDefaultTest(){
        environmentVariables.set(PortProvider.PORT_ENV_VAR, "abc");
        int actual = new PortProvider(1234).getPort();
        assertEquals(1234, actual);
    }
}
