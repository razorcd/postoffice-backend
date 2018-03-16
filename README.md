# PostBox

#### An application for inspecting frontend request issuers, webhooks, etc.

 It will receive any request and display the the full data and metadata of the request in the browser logger.

Includes:
 - JSON REST API
 - Secured endpoints
 - error handling with propper JSON response error messages
 - cookie based session with DB storage
 - validations
 - unit tests
 - integration tests
 

## TODO:
- [x] implement persistence of complete incomingRequests (url, action, headers, cookies, body)
- [x] add integration tests
- [x] build frontend to view the persisted incomingRequests with an automatic refresher
- [ ] add tests to cover all contentTypes, bodies, methods, etc (needs JUnit5)
- [x] add authentication and authorization to differentiate incomingRequests by User session
- [ ] define the complete incomingRequest domain
- [ ] implement websockets to send new incomingRequests to frontend
- [x] move incomingRequests to NoSql
- [ ] separate props by env
- [ ] add lombok
- [x] add java docs
- [ ] add tests coverage
- [ ] Swager to define API
