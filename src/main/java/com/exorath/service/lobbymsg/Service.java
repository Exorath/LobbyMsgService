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

/**
 * See https://github.com/Exorath/LobbyMsgService for documentation
 * Created by toonsev on 11/1/2016.
 */
public interface Service {
    /**
     * Gets the welcome message specific to the gameId. If there's no message assigned to the gameId, this will return the "default" message.
     * @param gameId the gameId to retrieve the welcome message of
     * @return The welcome message
     */
    Message getMessage(String gameId);

    /**
     * Updates the welcome message record of this gameId.
     * If the provided message is null, the record will be removed
     * @param gameId the gameId to update the welcome message of ("default" for default)
     * @param message the updated message
     * @return whether or not the update was successful
     */
    Success updateMessage(String gameId, Message message);
}
