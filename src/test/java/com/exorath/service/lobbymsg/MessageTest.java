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

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by toonsev on 11/1/2016.
 */
public class MessageTest {
    @Test
    public void testGetMsgEqualsConstructorMsg(){
        String eMsg = "<iterate><p>te</p><p>st</p></iterate>";
        Message message = new Message(eMsg, null);
        assertEquals(eMsg, message.getMsg());
    }

    @Test
    public void testGetFormatEqualsConstructorFormat(){
        String eFormat = "1";
        Message message = new Message("<iterate><p>te</p><p>st</p></iterate>", eFormat);
        assertEquals(eFormat, message.getFormat());
    }
}
