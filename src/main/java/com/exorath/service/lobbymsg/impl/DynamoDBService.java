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
import com.exorath.service.lobbymsg.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by toonsev on 11/1/2016.
 */
public class DynamoDBService extends SimpleService{
    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBService.class);

    public DynamoDBService(int cacheSeconds) {
        super(cacheSeconds);
    }
    @Override
    public Map<String, Message> fetchMessagesByGameId() {
        //TODO: Dynamodb implementation
        return new HashMap<>();
    }

    @Override
    public Success updateMessage(String gameId, Message message) {
        //Todo: Dynamodb implementation
        return new Success(false);
    }
}
