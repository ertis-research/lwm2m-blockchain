# Consensus Protocols
Besu implements several consensus protocols:
- **Ethash** (Proof of Work)
- **Clique** (Proof of Authority)
- **IBFT** (Proof of Authority)

The `config` property in the genesis files specifies the consensus protocol for a chain.
For instance, if the consensus protocol used is **Clique** the genesis file should look like this:

```json
{
  "config": {
    ...
    "clique": {
      ...
   }
  },
  ...
}
```

Folder `consensus_protocols` contains a guide on how to deploy a private blockchain network of each consensus protocol implemented in Besu.

## Comparing PoA Consensus Protocols
Proof of Authority consensus protocols can be used when participants know and trust each other (for instance, in a consortium network). They have two main advantages comparing to network using PoW:
* Faster block times
* Much greater transaction throughput (transactions committed by the chain in a time period)

In Clique and IBFT 2.0, a group of nodes in the network act as **signers** (Clique) or **validators** (IBFT 2.0). The existing nodes in the signer/validator pool vote to add nodes to or remove nodes from the pool.

Although both (Clique and IBFT 2.0) are PoA consensus protocols, there are some differences between them. The following table shows those differences:

|              Property             	|                                    Clique                                   	|                                 IBFT 2.0                                 	|
|:---------------------------------:	|:---------------------------------------------------------------------------:	|:------------------------------------------------------------------------:	|
| Inmediate finalty                 	| NO (it must be aware of forks and chain reorganizations).                   	| YES (there are no forks and all valid blocks get included in the chain). 	|
| Min. number of signers/validators 	| 1 signer, but offers no redundancy if the signer fails.                     	| 4 validators.                                                            	|
| Liveness                          	| It tolerates up to half of the signers failing.                             	| It requires greater or equal than 2/3 of validators working.             	|
| Speed                             	| Faster, but the probability of a fork increases with the number of signers. 	| Time to add new blocks increases as the number of validators grows.      	|

## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/