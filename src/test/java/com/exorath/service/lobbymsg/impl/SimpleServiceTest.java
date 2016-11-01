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
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;


/**
 * Created by toonsev on 11/1/2016.
 */
public class SimpleServiceTest {

    @Test
    public void testNullFetchMessagesByGameIdResultDoesNotThrowErrorOnGetMessage(){
        SimpleServiceWrapper service = new SimpleServiceWrapper(2).withFetchMessagesByGameIdResult(null);
        service.getMessage("test");
    }

    @Test
    public void getMessageCallsFetchMessagesByGameIdOnceTest(){
        SimpleServiceWrapper service = new SimpleServiceWrapper(2).withFetchMessagesByGameIdResult(null);
        service.getMessage("test");
        assertEquals(1,service.getFetchMessagesByGameIdCount());
    }

    @Test
    public void FiveGetMessagesCallFetchMessagesByGameIdOnceTest(){
        SimpleServiceWrapper service = new SimpleServiceWrapper(2).withFetchMessagesByGameIdResult(null);
        service.getMessage("test");
        service.getMessage("test");
        service.getMessage("test");
        service.getMessage("test");
        service.getMessage("test");
        service.getMessage("test");
        assertEquals(1,service.getFetchMessagesByGameIdCount());
    }

    @Test
    public void TwoGetMessageCallsWithOneSecondIntervalCallFetchMessagesByGameIdTwiceWhenCacheSecondsetToOneTest() throws Exception{
        SimpleServiceWrapper service = new SimpleServiceWrapper(1).withFetchMessagesByGameIdResult(null);
        service.getMessage("test");
        Thread.sleep(1000);
        service.getMessage("test");
        assertEquals(2,service.getFetchMessagesByGameIdCount());
    }

    @Test
    public void ThreeGetMessageCallsThenOneSecondIntervalThenThreeGetMessageCallsCallFetchMessagesByGameIdTwiceWhenCacheSecondsetToOneTest() throws Exception{
        SimpleServiceWrapper service = new SimpleServiceWrapper(1).withFetchMessagesByGameIdResult(null);
        service.getMessage("test");
        service.getMessage("test");
        service.getMessage("test");
        Thread.sleep(1000);
        service.getMessage("test");
        service.getMessage("test");
        service.getMessage("test");
        assertEquals(2,service.getFetchMessagesByGameIdCount());
    }

    @Test
    public void getMessageReturnsDefaultMessageWhenGameIdDoesNotExistTest(){
        Message expectedDefault = new Message("Hi there", "sampleformat");
        HashMap<String, Message> results = new HashMap<>();
        results.put(SimpleService.DEFAULT_GAME_ID, expectedDefault);
        SimpleServiceWrapper service = new SimpleServiceWrapper(1).withFetchMessagesByGameIdResult(results);

        assertEquals(expectedDefault,service.getMessage("unexistingGameId"));
    }

    @Test
    public void getMessageReturnsRegisteredMessageWhenDefaultAndGameIdMessageAreRegisteredTest(){
        Message expectedResult = new Message("Howdy", "sampleformat2");
        HashMap<String, Message> results = new HashMap<>();
        results.put(SimpleService.DEFAULT_GAME_ID, new Message("Hi there", "sampleformat"));
        results.put("registeredGameId", expectedResult);
        SimpleServiceWrapper service = new SimpleServiceWrapper(1).withFetchMessagesByGameIdResult(results);

        assertEquals(expectedResult,service.getMessage("registeredGameId"));
    }

    @Test
    public void getMessageReturnsRegisteredMessageWhenGameIdMessageIsRegisteredTest(){
        Message expectedResult = new Message("Howdy", "sampleformat2");
        HashMap<String, Message> results = new HashMap<>();
        results.put("registeredGameId", expectedResult);
        SimpleServiceWrapper service = new SimpleServiceWrapper(1).withFetchMessagesByGameIdResult(results);

        assertEquals(expectedResult,service.getMessage("registeredGameId"));
    }

    @Test
    public void whenFetchMessagesByGameIdResultIsCalledAfterOneSecondTheResultsAreUsedForSubsequentMessagesTest() throws Exception{
        Message expectedResult = new Message("Howdy", "sampleformat2");
        HashMap<String, Message> results = new HashMap<>();
        SimpleServiceWrapper service = new SimpleServiceWrapper(1).withFetchMessagesByGameIdResult(results);
        service.getMessage("blah2");
        Thread.sleep(1000);
        HashMap<String, Message> newResults = new HashMap<>(results);
        newResults.put("blah2", expectedResult);
        service.withFetchMessagesByGameIdResult(newResults);
        assertEquals(expectedResult, service.getMessage("blah2"));
    }
}
