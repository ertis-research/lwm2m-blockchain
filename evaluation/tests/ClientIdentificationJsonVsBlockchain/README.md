# Client identification In-memory Vs Blockchain
Testing the complete process of registering a client on an LwM2M server by obtaining the necessary credentials on the bootstrap server.

The times obtained using the native Leshan servers that query the information locally are compared with the servers developed in this project that query the information on the blockchain.

For this test, the Leshan demo client has been used in both scenarios. To do this, it is necessary to download the leshan repository and modify the leshan client, replacing the LeshanClientDemo.java file with the one provided here.
