Testing the complete process of registering a client on an LwM2M server by obtaining the necessary credentials on the bootstrap server.

The times obtained using the native Leshan servers that query the information locally are compared with the servers developed in this project that query the information on the blockchain.


A common client has been used for both cases (leshan-client-demo-2.0.0-SNAPSHOT-jar-with-dependencies.rar).

```
java -jar leshan-client-demo-2.0.0-SNAPSHOT-jar-with-dependencies.jar -b -n clientEndpoint9 -u localhost:5684 -i clientbs9 -p 654321
```
