# Evaluation
This section exhibits a set of tests carried out on the three developed smart contracts. 
- **Client management:** It has been evaluated the average time to add, get and delete security information of LwM2M clients. It has also been evaluated the response time when connecting an LwM2M client to an LwM2M server through the Bootstarp server in our Blockchain-based system compared with the one offered by the LwM2M implementation of Eclipse Leshan.

- **Applications management:** Average time to add, validate and modify user credentials has been calculated.
 
- **Critical information management:** It has been evaluated the average time to  get all the records of stored critical information. It has also been evaluated how latency is influenced by the number of simultaneous applications requesting information from a unique and different LwM2M clients and subsequently storing it in the anomalies smart contract.

The evaluation has been performed with different replicas
of the management server whose requests to its service are
distributed by the load balancer provided by Google Kubernetes Engine (GKE). The chosen scenarios have been:
- Without using Kubernetes
- A replica in Kubernetes
- Two replicas in Kubernetes
- Four replicas in Kubernetes
