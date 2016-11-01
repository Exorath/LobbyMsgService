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

package com.exorath.service.lobbymsg.impl;

import com.exorath.service.lobbymsg.Message;
import com.exorath.service.lobbymsg.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by toonsev on 11/1/2016.
 */
public abstract class SimpleService implements Service {
    public static final String DEFAULT_GAME_ID = "default";
    private static final Logger LOG = LoggerFactory.getLogger(SimpleService.class);

    private Map<String, Message> messagesByGameId = new HashMap<>();
    private int cacheSeconds;
    private long lastCacheTimeInMillis = 0;

    public SimpleService(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
    }

    @Override
    public Message getMessage(String gameId) {
        tryUpdateCache();
        //Return the cached message by the provided gameId if it exists
        Message gameIdMessage = getCachedMessage(gameId);
        if (gameIdMessage != null)
            return gameIdMessage;
        //Return the default message if it exists
        Message defaultMessage = getCachedMessage(DEFAULT_GAME_ID);
        if (defaultMessage != null)
            return defaultMessage;
        //Return an empty message
        return new Message(null, null);
    }



    private synchronized void tryUpdateCache() {
        long currentTimeInMillis = new Date().getTime();
        if (shouldUpdateCache(currentTimeInMillis)) {
            lastCacheTimeInMillis = currentTimeInMillis;
            messagesByGameId.clear();
            Map<String, Message> newMessagesByGameId = fetchMessagesByGameId();
            if (newMessagesByGameId != null)
                messagesByGameId.putAll(newMessagesByGameId);
        }
    }

    private synchronized Message getCachedMessage(String gameId) {
        return messagesByGameId.get(gameId);
    }

    public abstract Map<String, Message> fetchMessagesByGameId();

    /**
     * Whether or not the cache time has expired
     *
     * @param currentTime the current time in millis
     * @return True if the cache time has expired
     */
    private synchronized boolean shouldUpdateCache(long currentTime) {
        return lastCacheTimeInMillis + TimeUnit.SECONDS.toMillis(cacheSeconds) <= currentTime;
    }
}
