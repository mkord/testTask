## Getting Started
It is a SpringBoot application.

To change port for embedded Tomcat (default 9090) 
chang property 'server.port' in 'application.properties' 
located in './src/main/resources'
 
To build application use command
``` 
mvn package
```

To run application use commad 
```
java -jar ./target/twitt-0.0.1-SNAPSHOT.jar
```

## Usage
- to post new message call: http://localhost:{PORT}/post?user={userName}&msg={message} 
- to follow another user call: http://localhost:{PORT}/follow?userRequester={userName}&userToBeFollowed={anotherUserName} 
- to show the wall call: http://localhost:{PORT}/wall?user={userName} 
- to show timeline call: http://localhost:{PORT}/timeline?user={userName}