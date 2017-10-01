# PostBox

#### An application for inspecting frontend request issuers, webhooks, etc.

 It will receive any request and display the the full data and metadata of the request in the browser logger.

## TODO:
- [] implement persistence of the complete incomingRequests (url, action, headers, cookies, body, attachments)
- [] add integration tests
- [] build frontend to view the persisted incomingRequests with an automatic refresher
- [] add tests to cover all contentTypes, bodies, methods, etc
- [] add authentication and authorization to differentiate incomingRequests by user
- [] define the complete incomingRequest domain
- [] implement websockets to send new incomingRequests to frontend
- [] move incomingRequests to NoSql
- [] separate props by env