## nrg-bulletin-board - API based bulletin board software

### Getting started
To run an instance with default settings without any security do this:

```
docker run -p 50005:50005 -p 50006:50006 \
    --env SPRING_PROFILES_ACTIVE=insecure \
    registry.gitlab.com/encircle360-oss/nrg-bulletin-board:latest
```

## OAuth2 support
to run the software with an oauth2 issuer just add environment variable SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER=xxx (with correct url) and set no profile

```
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER-URI=https://localhost:1111/auth/realms/master
```

## Keycloak support
you can add keycloak support by activating spring profile "keycloak". But don't forget to set environment variables

```
RESOURCESERVER_RESOURCEID=nrg-bulletin-board
KEYCLOAK_ADMIN_CLIENT-ID=nrg-bulletin-board
KEYCLOAK_ADMIN_CLIENT-SECRET=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxx
KEYCLOAK_ADMIN_SERVER-URL=http://localhost:1111/auth
KEYCLOAK_ADMIN_REALM=nrg-bb
```

### About this software
This is open source software by [encircle360](https://encircle360.com).
Use on your own risk and for personal use. If you need support or consultancy just contact us.
Since this software was built with a high consumption of energy drinks feel free to donate more energy drinks to us.
Please keep in mind that we only accept sugar-free drinks.
Cheers. 

![](nrg-consumption-encircle360.jpg)
