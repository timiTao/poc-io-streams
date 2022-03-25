# 2. provide-async-command-request

Date: 2022-03-25

## Status

Discussed

## Context

Current plan is to push any command with fully async manner. This requires initial accept all request with `202` HTTP code and later pass results of validating and executing action.
At same time build frontend flow that allow browser to know where search particular resource at end. 

Initial assumptions
* resource id will be assigned on client side, then we need to consider
  * validation of resource
    * structure
    * conflict with existing resource on id
    * business validation
  * landing resource URL should be generated on server side

## Options

For me, know strategy are:
* `pull` - client request particular endpoint to get know about his request/resource status
* `push` - client sigh for particular socket and is notified when change occurs

I would like to have clear HTTP status management. It is worth mentioning codes
* `202 Accepted` - response status code indicates that the request has been accepted for processing, but the processing has not been completed. 
  * Can contain URL to resource or request status 
* `301 Moved Permanently` - redirect status response code indicates that the requested resource has been definitively moved to the URL given by the Location headers.
  * helpful if request will be successful to redirect it to resource itself
* `409 Conflict` - The HTTP 409 Conflict response status code indicates a request conflict with the current state of the target resource.
  * This can be useful if client try assign existing key to new resource.
* `410 Gone` - client error response code indicates that access to the target resource is no longer available at the origin server and that this condition is likely to be permanent.
  * helpful if request will be held as separate resource for clients for resource itself.

## Decision

## Consequences

What becomes easier or more difficult to do and any risks introduced by the change that will need to be mitigated.
