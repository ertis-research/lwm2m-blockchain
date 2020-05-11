# Bootstrap server
The aim of this project is to develop a bootstrap server using leshan where client's security information is stored in a smart contract deployed on an Ethereum testnet called Ropsten.

This project can be divided into three parts:
- Leshan bootstrap server
- Web interface

## Leshan bootstrap server
A Maven project called BootrapServerLeshan is created and the necessary dependencies for our project are added (see pom.xml).

We use the file called BootstrapConfigSecurityStore.java from the project leshan-bsserver-demo (available in the [official leshan repository](https://github.com/eclipse/leshan)). We also add the wrapper class generated above to our project.

A file called BlockchainBootstrapStore.java is created, which will be an implementation of the EditableBootstrapConfigStore class. This is where the web3j wrapper is used to interact with the smart contract. Four functions (add, remove, getAll, get) of the EditableBootstrapConfigStore interface are implemented in this class.

Finally, the Main class of the project called BootstrapServerLeshan.java is created. This class does the following:
- Prepare and start bootstrap server
- Create and set DTLS Config

## Test
To see that everything works, a little test is done adding the client's information on the bootstrap server and on the server.

- Endpoint: ivanEndpoint
- Bootstrap server connection
	- Url_bs: coaps://localhost:5684
	- Id_bs: ivan
	- Key_bs: 123456
- Server connection
	- Url_s: coaps://localhost:5784
	- Id_s: ivan
	- Key_s: 123456


The client and demo server provided by leshan will be used for this test (available in the [leshan official repository](https://github.com/eclipse/leshan)). To do this, download the repository
```
git clone https://github.com/eclipse/leshan.git
```
Compile it, by running in leshan root folder :
```
mvn clean install
```

Run demo server:
```
java -jar leshan-server-demo/target/leshan-server-demo-*-SNAPSHOT-jar-with-dependencies.jar -lp 5783 -slp 5784 -wp 8081
```

Para lanzar un cliente se lanza el siguiente comando.
```
java -jar leshan-client-demo/target/leshan-client-demo-*-SNAPSHOT-jar-with-dependencies.jar -b -n ivanEndpoint  -i ivan -p 123456
```

## URLs
- https://leshan.eclipseprojects.io/bs/
- https://leshan.eclipseprojects.io/#/clients
- https://github.com/eclipse/leshan
- https://hmkcode.com/java-servlet-send-receive-json-using-jquery-ajax/
- https://elbauldelprogramador.com/como-mapear-json-a-objetos-java-con-jackson-objectmapper/
- https://github.com/ivaann2706/Leshan-CuartaPrueba
- https://github.com/ivaann2706/Ethereum-ContratoInteligente_ServidorBootstrap
- https://ethereum.stackexchange.com/questions/23549/convert-string-to-bytes32-in-web3j
- https://github.com/ivaann2706/Ethereum-UsandoJava_Web3j
- https://web3j.readthedocs.io/en/latest/getting_started.html
- https://kauri.io/interacting-with-an-ethereum-smart-contract-in-java/14dc434d11ef4ee18bf7d57f079e246e/a