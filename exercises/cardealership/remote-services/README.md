# remote services application

This spring-boot application provides services which can be called from a process application.

The services are:

**Search for a delivery service**:

Provided at `http://localhost:8084/delivery-service` and returns a json array with delivery services.

Example response to a GET request:

```json
[
  {
  "id": 1,
  "name": "Transport Trödel"
  },
  {
  "id": 2,
  "name": "Lightning Transports"
  }
]
```

**Assign delivery order**:

Asynchronous service to assign a delivery to a service. 
Provided at `/delivery-service/{deliveryServiceId}/order` (**Put** request).

The request returns an HTTP 202 (Accepted) and
the delivery confirmation is send after a fixed delay of one second to the endpoint specified with the `respondTo`query parameter.

Example call:

```
/delivery-service/1/order?processInstanceId=45435435-asdfdsf-234234-sdfsd&respondTo=http://localhost:8080/deliveryServiceResponse
```

Then after on second a **PUT** request to `http://localhost:8084/deliveryServiceResponse` is executed with this payload:

````json
{
  "status": "accepted",
  "processInstanceId": "45435435-asdfdsf-234234-sdfsd",
  "deliveryPrice": 325324
}
````
