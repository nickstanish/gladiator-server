gladiator-server
================

_by Nick Stanish and Joey Imburgia_

Server for a multiplayer game created in 24 hours for the Purdue/ExactTarget hackathon. See [client](https://github.com/nickstanish/gladiator-client)

Turn-based fighting game where users are paired into an arena and with a fighting style resembling pokemon

Features:
* Unique user login with text validation
* Persistent socket communication with a protocol resembling http with json content
* Custom routing of actions using a map of endpoints to lamdba handlers

Uses only Java 8  and Gson library

## Getting started
Install the dependencies
```
mvn install
```

Run the server
```
mvn exec:java
```

Specify a port
```
mvn exec:java -Dexec.args="3000"
```
