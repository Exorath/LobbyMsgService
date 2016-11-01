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

import com.google.gson.annotations.SerializedName;

/**
 * Created by toonsev on 11/1/2016.
 */
public class Message {
    @SerializedName("msg")
    private String msg;
    @SerializedName("format")
    private String format;

    public Message(String msg, String format){
        this.msg = msg;
        this.format = format;
    }

    /**
     * Gets the string message
     * @return the string message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Gets the format this message is in, null for now (different versions may be added in the future, for different ways of sending messages)
     * @return the format this message is in, null for now
     */
    public String getFormat() {
        return format;
    }
}
