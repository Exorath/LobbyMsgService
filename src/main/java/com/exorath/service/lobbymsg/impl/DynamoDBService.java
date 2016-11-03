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

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.exorath.service.lobbymsg.Message;
import com.exorath.service.lobbymsg.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by toonsev on 11/1/2016.
 */
public class DynamoDBService extends SimpleService {
    private static final String ID_FIELD = "r";
    private static final String ID_VALUE = "prod";
    private static final String MSGS_FIELD = "msgs";
    private static final String MSG_FIELD = "msg";
    private static final String FORMAT_FIELD = "format";

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBService.class);
    private String tableName;
    private DynamoDB db;

    public DynamoDBService(int cacheSeconds, String tableName, DynamoDB db) {
        super(cacheSeconds);
        this.tableName = tableName;
        this.db = db;
        try {
            setupTable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setupTable() throws InterruptedException {
        try {
            Table table = db.createTable(new CreateTableRequest()
                    .withTableName(tableName)
                    .withKeySchema(new KeySchemaElement(ID_FIELD, KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition(ID_FIELD, ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput(1l, 1l))
            );
            LOG.info("Created dynamodb table " + tableName + " with 1r/1w provisioning. Waiting for it to activate");
            waitForTableToActivate();
        } catch (ResourceInUseException e) {//table exists, let's make sure it's active
            waitForTableToActivate();
        }
    }

    private void waitForTableToActivate() throws InterruptedException {
        db.getTable(tableName).waitForActive();
    }

    @Override
    public Map<String, Message> fetchMessagesByGameId() throws Exception {
        Item item = db.getTable(tableName).getItem(getMapMessagesSpec());
        if (item == null || !item.hasAttribute(MSGS_FIELD))
            return new HashMap<>();
        Map<String, Map<String, Object>> messages = item.getMap(MSGS_FIELD);
        return mapMessages(messages);
    }

    public static GetItemSpec getMapMessagesSpec() {
        return new GetItemSpec().withPrimaryKey(getPrimaryKey());
    }

    private Map<String, Message> mapMessages(Map<String, Map<String, Object>> messages) {
        Map<String, Message> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> msgEntry : messages.entrySet()) {
            String msg = msgEntry.getValue().containsKey(MSG_FIELD) ? (String) msgEntry.getValue().get(MSG_FIELD) : null;
            String format = msgEntry.getValue().containsKey(FORMAT_FIELD) ? (String) msgEntry.getValue().get(FORMAT_FIELD) : null;
            result.put(msgEntry.getKey(), new Message(msg, format));
        }
        return result;
    }


    @Override
    public Success updateMessage(String gameId, Message message) {
        if (gameId == null || message == null)
            return new Success(false);
        try {
            db.getTable(tableName).updateItem(getCheckMsgsMapExistsSpec());
            db.getTable(tableName).updateItem(getCheckMsgMapInMsgsMapExistsSpec(gameId));
            db.getTable(tableName).updateItem(getUpdateItemSpec(gameId, message));
        } catch (Exception e) {
            e.printStackTrace();
            return new Success(false);
        }
        return new Success(true);
    }

    public static UpdateItemSpec getCheckMsgsMapExistsSpec() {
        return new UpdateItemSpec().withPrimaryKey(getPrimaryKey())
                .withUpdateExpression("SET " + MSGS_FIELD + " = if_not_exists(" + MSGS_FIELD + ", :empty)")
                .withValueMap(new ValueMap().withMap(":empty", new HashMap<>()));
    }

    public static UpdateItemSpec getCheckMsgMapInMsgsMapExistsSpec(String gameId) {
        return new UpdateItemSpec().withPrimaryKey(getPrimaryKey())
                .withUpdateExpression("SET " + MSGS_FIELD + "." + gameId + " = if_not_exists(" + MSGS_FIELD + "." + gameId + ", :empty)")
                .withValueMap(new ValueMap().withMap(":empty", new HashMap<>()));
    }

    public static UpdateItemSpec getUpdateItemSpec(String gameId, Message message) {
        String updateExpression;
        ValueMap valueMap = new ValueMap();
        if (message.getMsg() == null)
            updateExpression = "REMOVE " + MSGS_FIELD + "." + gameId;
        else {
            updateExpression = "SET " + MSGS_FIELD + "." + gameId + "=:msgMap";
            ValueMap msgMap = new ValueMap().withString(MSG_FIELD, message.getMsg());
            if (message.getFormat() != null)
                msgMap.withString(FORMAT_FIELD, message.getFormat());
            valueMap.withMap(":msgMap", msgMap);
        }

        if (valueMap.isEmpty())
            valueMap = null;
        System.out.println(updateExpression);
        return new UpdateItemSpec()
                .withPrimaryKey(getPrimaryKey())
                .withUpdateExpression(updateExpression)
                .withValueMap(valueMap);
    }

    public static KeyAttribute getPrimaryKey() {
        return new KeyAttribute(ID_FIELD, ID_VALUE);
    }
}
