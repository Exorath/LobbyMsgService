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
public class CacheSecondsProviderTest {
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();
    @Test
    public void getCacheSecondsReturnsDefaultParamByDefaultTest(){
        CacheSecondsProvider provider = new CacheSecondsProvider();
        int actual =  provider.getCacheSeconds(12);
        assertEquals(12, actual);
    }

    @Test
    public void getCacheSecondsReturnsEnvVarIfSetTest(){
        environmentVariables.set(CacheSecondsProvider.CACHE_SECONDS_ENV, "100");
        CacheSecondsProvider provider = new CacheSecondsProvider();
        int actual =  provider.getCacheSeconds(12);
        assertEquals(100, actual);
    }
}
