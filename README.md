# LwM2M-blockchain
In this respository, the [LWM2M](https://omaspecworks.org/what-is-oma-specworks/iot/lightweight-m2m-lwm2m/) protocol is integrated with blockchain to improve reliability and auditability. The Java implementation of LWM2M, [Leshan](https://github.com/eclipse/leshan), and Ethereum testnet [Ropsten](https://ropsten.etherscan.io/) have been be used for this purpose.

This repository is divided in six main folders:
* **anomalyDetectionApp** --> Auxilar application that requests data to LwM2M clients and checks if the received information is or not an anomaly.
* **docs** --> Project documentation.
* **evaluation** --> Java project to test system interaction with the blockchain network.
* **leshan** --> Java Implementation of LwM2M protocol modified to accomplish communication with Ropsten network.
* **mainApp** --> Frontend and backend of the Web management UI Application.
* **smartContracts** --> Smart Contracts are stored here.
