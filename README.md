# LobbyMsgService
HTTP REST service for providing boss bar messages to the Exorath lobbies (written in Go).

In v2 the lobby msg service will pick up join events send through our messaging channels, then it will send a display action to the appropriate server, but this is for the future.

##Endpoints
###/msgs/{gameId} [GET]:
####Gets the welcome message specific to the gameId
If there's no message assigned to the gameId, the default message is returned.

**Arguments**:
- gameId (string): The id of the game to retrieve a welcome message as

**Response**: {msg: "\<it i=10\>\<pl\>Welcome\</pl\>\<pl\>To\</pl\>\<pl\>Exorath\</pl\>\</it\>"}
- msg (string)[OPTIONAL]: The message, in ExoHUD XML notation. If no msg was returned it probably means an error occured and no message should be displayed to the player 
- format (string)[OPTIONAL]: The format the response is in, currently empty as ExoHUD XML notation is always used

###/msgs/{gameId} [PUT]:
####Updates the server record.
**Body**:
{"msg": "\<it i=10\>\<pl\>Welcome\</pl\>\<pl\>To\</pl>\<pl\>Exorath\</pl\>\</it\>"}

**Arguments**:
- gameId (string): The gameId of the server instance
- msg (string) [OPTIONAL]: The updated message, not sending this will clear the current message.

**Response**: {success: true}
- success (boolean): Whether or not the record was updated successfully 
- err (string)[OPTIONAL]: Error message only responded when the put was not successful.
