## Proxy Seller Assigment Application ##

### Requirements ###
- Java 11
- Mongo
- Docker (for testing)
- Netty (is embedded)

#### 1. Installation on docker env ####
For building and running the mongo-container in the local environment (with **docker-compose**):
docker-compose up

Proxy-Seller API

Authentication
All endpoints, except the authentication endpoints, require a valid JSON Web Token (JWT) to be included 
in the header of the request. To obtain a JWT, a user must first sign up for an account using the
POST /user/register endpoint. Then, the user can sign in using the POST /auth endpoint, 
which will return a JWT in the response.



Postman collection is also available with steps   proxy-seller.postman_collection.json


 
