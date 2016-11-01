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
public class CacheSecondsProvider {
    public static final String CACHE_SECONDS_ENV = "EXO_CACHE_SECS";
    private static final Logger LOG = LoggerFactory.getLogger(CacheSecondsProvider.class);

    public int getCacheSeconds(int defCacheSeconds) {
        if (System.getenv(CACHE_SECONDS_ENV) != null) {
            try {
                return Integer.valueOf(System.getenv(CACHE_SECONDS_ENV));
            } catch (NumberFormatException e) {
                LOG.warn("Using default cache time: " + defCacheSeconds + "seconds", e);
            }
        }
        return defCacheSeconds;
    }
}
