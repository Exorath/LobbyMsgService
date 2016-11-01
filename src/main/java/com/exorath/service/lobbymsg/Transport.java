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
import spark.Route;

import static spark.Spark.*;

/**
 * Created by toonsev on 11/1/2016.
 */
public class Transport {
    private static final Gson GSON = new Gson();

    public static void setup(Service svc, PortProvider provider){
        port(provider.getPort());
        get("/msgs/:gameId", Transport.getGetMessageRouter(svc), GSON::toJson);
        put("/msgs/:gameId", Transport.getUpdateMessageRouter(svc),GSON::toJson);
    }

    public static Route getGetMessageRouter(Service svc) {
        return (req, res) -> {
            return svc.getMessage(req.params("gameId"));
        };
    }

    public static Route getUpdateMessageRouter(Service svc) {
        return (req, res) -> {
            Message message = GSON.fromJson(req.body(), Message.class);
            return svc.updateMessage(req.params("gameId"), message);
        };
    }
}
