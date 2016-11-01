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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by toonsev on 11/1/2016.
 */
public class SimpleServiceWrapper extends SimpleService {
    private Map<String, Message> fetchMessagesByGameIdResult = new HashMap<>();
    private Success updateMessageResult = new Success(false);
    private List<UpdateMessageParameters> updateMessageCalls = new ArrayList<>();
    private int fetchMessagesByGameIdCount = 0;

    public SimpleServiceWrapper(int cacheSeconds){
        super(cacheSeconds);
    }
    @Override
    public Success updateMessage(String gameId, Message message) {
        updateMessageCalls.add(new UpdateMessageParameters(gameId, message));
        return updateMessageResult;
    }

    @Override
    public Map<String, Message> fetchMessagesByGameId() {
        fetchMessagesByGameIdCount++;
        return fetchMessagesByGameIdResult;
    }

    public List<UpdateMessageParameters> getUpdateMessageCalls() {
        return updateMessageCalls;
    }

    public int getFetchMessagesByGameIdCount() {
        return fetchMessagesByGameIdCount;
    }

    public SimpleServiceWrapper withUpdateMessageResult(Success result){
        updateMessageResult = result;
        return this;
    }
    public SimpleServiceWrapper withFetchMessagesByGameIdResult(Map<String, Message> result){
        fetchMessagesByGameIdResult = result;
        return this;
    }

    public static class UpdateMessageParameters{
        private String gameId;
        private Message message;
        public UpdateMessageParameters(String gameId, Message message){
            this.gameId = gameId;
            this.message = message;
        }

        public String getGameId() {
            return gameId;
        }

        public Message getMessage() {
            return message;
        }
    }
}
