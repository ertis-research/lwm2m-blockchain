# blockchain-iot
In this project we will integrate the protocol [LWM2M](https://omaspecworks.org/what-is-oma-specworks/iot/lightweight-m2m-lwm2m/) with blockchain technology. A Java implementation of LWM2M protocol called [Leshan](https://github.com/eclipse/leshan) and Ethereum testnet [Ropsten](https://ropsten.etherscan.io/) will be use for this purpose.

This repository is divided in six main folders:
* **anomalyDetectionApp** --> Auxilar application that requests data to LwM2M clients and checks if received information is or not an anomaly.
* **docs** --> Project documentation.
* **evaluation** --> Java project to test system interaction with the blockchain network.
* **leshan** --> Java Implementation of LWM2M protocol modified to accomplish communication with Ropsten network.
* **mainApp** --> Frontend and backend of the Main Server Application.
* **smartContracts** --> Smart Contracts are stored here.