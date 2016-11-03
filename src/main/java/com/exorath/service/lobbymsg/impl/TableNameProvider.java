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

/**
 * Created by toonsev on 11/2/2016.
 */
public class TableNameProvider {
    public static final String TABLE_NAME_ENV = "DB_TABLE_NAME";
    public String getTableName(){
        String tableName = System.getenv(TABLE_NAME_ENV);
        if(tableName == null || tableName == "")
            throw new IllegalStateException("Unable to load tableName from environment variable " + TABLE_NAME_ENV);
        return tableName;
    }
}
