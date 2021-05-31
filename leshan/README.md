# Leshan
In this folder is everything related to Leshan. The content is divided into three subfolders: Bootstrap server, Client and Server.

## Bootstrap server
Bootstraps server manages the credentials and bootstrapping to the different LwM2M clients so that they can later register to the LwM2M servers.

A Maven project is created and Leshan and Web3j dependencies are added.
The Leshan dependency allows you to implement a bootstrap server with all the features and functionality that Leshan provides. On the other hand, Web3j allows interaction with the Ethereum blockchain network, in this case the testnet called Ropsten.

This bootstrap server retrieves the client's security information stored in a smart contract. It is necessary to implement the *EditableBootstrapConfigStore* interface, which is in charge of managing the storage of the bootstrap server. The interaction with the contract is done using a wrapper class created with the Web3j tool.

DTLS is configured on this server to allow secure connections, and all non-secure connections are disabled.


## Client
Clients are responsible for making measurements of physical parameters on the environment or infrastructures.

A Maven project is created and the Leshan dependency for clients is added. The client is configured with an endpoint name, an identifier, a password and the bootstrap server URL.

By default, Leshan clients are created with three mandatory objects: Security, Server, and Device. In this case, an OMA predefined temperature sensor object with identifier 3303 has also been added.

## Server
The objective of LwM2M servers is to allow the monitoring of the environment through the clients that are registered on them.  

This server has two clearly different parts. On the one hand, there is the COAP server with client-server interaction using the LwM2M standard. On the other hand, there is the development of the secure REST API with the aim that different authorized applications make use of it.

A Spring project is created and Leshan, Web3j and JSON Web Token dependencies are added.

This server verifies the authenticity of the LwM2M clients through queries to the blockchain network using a smart contract wrapper class. To do this, the *EditableSecurityStore* interface is implemented, which is where the storage of client credentials is managed.

The REST API enables authentication using JWT and the deployed smart contract where all registered users are stored.
