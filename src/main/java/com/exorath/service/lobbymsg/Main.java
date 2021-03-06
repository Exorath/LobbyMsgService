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

import com.exorath.service.lobbymsg.impl.DynamoDBProvider;
import com.exorath.service.lobbymsg.impl.DynamoDBService;
import com.exorath.service.lobbymsg.impl.TableNameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by toonsev on 11/1/2016.
 */
public class Main {
    private Service svc;
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);


    private Main(){
        //Creates a new instance of the service, with the dynamodb implementation
        this.svc = new DynamoDBService(new CacheSecondsProvider().getCacheSeconds(120), new TableNameProvider().getTableName(), new DynamoDBProvider().getDB());
        LOG.info("Service " + this.svc.getClass() + " instantiated");
        //Sets up the http transport
        Transport.setup(svc, new PortProvider(80));
        LOG.info("HTTP transport setup");
    }


    public static void main(String[] args) {
        new Main();
    }
}
