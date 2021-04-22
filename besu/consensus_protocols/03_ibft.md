# Create a private network using IBFT 2.0 (PoA)
This guide shows how to create a Besu network with 4 nodes using IBFT (Proof-of AUthority). Unlike Ethash or Clique, IBFT 2.0 requires a minimum of 4 nodes

### 1) Create directories
Each node requires a data directory to store blockchain data.
```bash
mkdir -p ibft2_net/node-1/data ibft2_net/node-2/data \
ibft2_net/node-3/data ibft2_net/node-4/data
```

Using command `tree` we can verify our directory structure.
```bash
$ sudo apt-get install tree # If not installed
$ tree ibft2_net/
> ibft2_net/
> ├── node-1
> │   └── data
> ├── node-2
> │   └── data
> ├── node-3
> |   └── data
> └── node-4
>     └── data
```

### 2) Generate extraData property
The `extraData` is a property, which indicates validators addresses, existing on the genesis file. Two steps are needed to generate this property.

#### 2.1) Get nodes addresses
In IBFT 2.0 networks, you must include the addresses of at least four initial validators in the genesis file. To get the address of a node use `public-key export-address` subcommand (address will be displayed on terminal):

```bash
besu --data-path=ibft2_net/node-1/data public-key export-address
besu --data-path=ibft2_net/node-2/data public-key export-address
besu --data-path=ibft2_net/node-3/data public-key export-address
besu --data-path=ibft2_net/node-4/data public-key export-address
```

#### 2.2) Generate extraData RLP string
The `extraData` property is **RLP encoded**. RLP (Recursive Length Prefix) encoding is a space efficient object serialization scheme used in Ethereum. To generate the `extraData` RLP string for inclusion in the genesis file, the use of the `rlp encode` Besu subcommand is required.

```bash
besu rlp encode --from=toEncode.json
```

The `toEncode.json`file contains the list of the initial validators:

```json
[
  "<node-1 address>",
  "<node-2 address>",
  "<node-3 address>",
  "<node-4 address>"
]
```

### 3) Create genesis file
The properties specific to IBFT 2.0 are:
- `blockperiodseconds`: the block time, in seconds.
- `epochlenght`: the number of blocks after which to reset all votes.
- `requesttimeoutseconds`: the timeout for each consensus round before a round change, in seconds.
- `blockreward`: optional reward amount **in Wei**. Defaults to zero, can be specified as a hex or decimal string value.
- `miningbeneficiary`: optional beneficiary of the `blockreward`. Defaults to the validator that proposes the block.
- `extraData`: generated in the previous step.

The properties with specific values in IBFT 2.0 are:
- `nonce`: 0x0
- `difficulty`: 0x1
- `mixHash`: 0x63746963616c2062797a616e74696e65206661756c7420746f6c6572616e6365

In our example, the genesis file will be named `ibft2Genesis.json` and created in `ibft2_net` directory.
```json
{
  "config": {
    ...
    "ibft2": {
      "blockperiodseconds": 2,
      "epochlength": 30000,
      "requesttimeoutseconds": 4
    }
  },
  "extraData": "<generated with rlp>",
  "nonce": "0x0",
  "difficulty": "0x1",
  "mixHash": "0x63746963616c2062797a616e74696e65206661756c7420746f6c6572616e6365",
  ...
}
```

### 4) Start node-1 as a bootnode
```bash
cd node-1/
besu --data-path=data --genesis-file=../ibft2Genesis.json \
     --rpc-http-enabled --rpc-http-api=ETH,NET,WEB3,IBFT \
     --host-allowlist="*" --rpc-http-cors-origins="all"
```
This command enables:
- The HTTP JSON-RPC API using the `--rpc-http-enabled` option
- The `ETH`, `NET`, `WEB3` and `IBFT` APIs using the `--rpc-http-api` option. APIs enabled by default are `ETH`, `NET` and `WEB3`.
- All-host access to the HTTP JSON-RPC API using the `--host-allowlist` option
- All-domain access to the node through the HTTP JSON-RPC API using the `--rpc-http-cors-origins` option

Before moving to the next step, we have to copy __node-1 enode URL__. It will be displayed just a few seconds after we execute the above command. The information we are looking for looks like this:
```bash
2021-01-29 15:47:33.420+01:00 | main | INFO  | DefaultP2PNetwork | Enode URL enode://d263e7eff58923bf622fc0e9634687db3f62ad0d6aeb6d431aa1b33636dc9aefdb1d50f468b08bde9e3a1cafa314df306dbefc70472a7e23a0f8b669c4a65b33@127.0.0.1:30303
```

### 5) Start the other nodes
Once we have copied node-1 enode URL, we can run node-2, node-3 and node-4.
```bash
# Open a new terminal at 'ibft2_net' folder
cd node-2/
besu --data-path=data --genesis-file=../ibft2Genesis.json \
     --bootnodes=<node-1 enode URL> --p2p-port=30304 \
     --rpc-http-enabled --rpc-http-api=ETH,NET,WEB3,IBFT \
     --host-allowlist="*" --rpc-http-cors-origins="all" \
     --rpc-http-port=8546
```

```bash
# Open a new terminal at 'ibft2_net' folder
cd node-3/
besu --data-path=data --genesis-file=../ibft2Genesis.json \
     --bootnodes=<node-1 enode URL> --p2p-port=30305 \
     --rpc-http-enabled --rpc-http-api=ETH,NET,WEB3,IBFT \
     --host-allowlist="*" --rpc-http-cors-origins="all" \
     --rpc-http-port=8547
```

```bash
# Open a new terminal at 'ibft2_net' folder
cd node-4/
besu --data-path=data --genesis-file=../ibft2Genesis.json \
     --bootnodes=<node-1 enode URL> --p2p-port=30306 \
     --rpc-http-enabled --rpc-http-api=ETH,NET,WEB3,IBFT \
     --host-allowlist="*" --rpc-http-cors-origins="all" \
     --rpc-http-port=8548
```

### 6) Confirm the network is working
In other terminal, try to confirm the network has four validators. To do that, use JSON-RPC API `ibft_getValidatorsByBlockNumber` method:
```bash
curl -X POST --data \
'{"jsonrpc":"2.0", "method":"ibft_getValidatorsByBlockNumber", "params":["latest"], "id":1}' \
localhost:8545 
```

### 7) Adding and removing validators
To add or remove validators (or list how many of them are in the network) it is necessary to use IBFT 2.0 API, which was enabled on steps [4](#4-start-node-1-as-a-bootnode) and [5](#5-start-the-other-nodes). A majority of existing validators must agree to add or remove a validator, which means more than a 50% of validators. For instance, if network has 4 validators, the vote must be submitted by 3 validators.

#### 7.1) Adding a validator
To propose adding a validator, call `ibft_proposeValidatorVote` method, specifying the address of the proposed validator and `true`. The command to propose a new validator is as follows:
```bash
curl -X POST --data \
'{"jsonrpc":"2.0", "method":"ibft_proposeValidatorVote", "params":["0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73", true], "id":1}' \
localhost:8545
```

#### 7.2) Removing a validator
This process is almost the same, but in this case the second parameter of method `ibft_proposeValidatorVote` is `false`.
```bash
curl -X POST --data \
'{"jsonrpc":"2.0", "method":"ibft_proposeValidatorVote", "params":["0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73", false], "id":1}' \
localhost:8545
```

#### 7.3) Discard proposal
To discard a proposal after confirming the addition (or the removal) of a validator, call `ibft_discardValidatorVote`, specifying the address of the proposed validator.
```bash
curl -X POST --data \
'{"jsonrpc":"2.0", "method":"ibft_discardValidatorVote", "params":["0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73"], "id":1}' \
localhost:8545
```

#### 7.4) Epoch transition
At each epoch transition, IBFT 2.0 discards all pending votes. Existing proposal remain in effect and validators re-add their votes the next time they create a block. The number of blocks between epoch transitions is defined in genesis file (parameter `epochlenght`).

### Useful links
[Create a private network using IBFT 2.0](https://besu.hyperledger.org/en/stable/Tutorials/Private-Network/Create-IBFT-Network/)

[IBFT 2.0 - Hyperledger Besu](https://besu.hyperledger.org/en/stable/HowTo/Configure/Consensus-Protocols/IBFT/)

[public-key export-address command](https://besu.hyperledger.org/en/stable/Reference/CLI/CLI-Subcommands/#export-address)

[rlp command](https://besu.hyperledger.org/en/stable/Reference/CLI/CLI-Subcommands/#rlp)

## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/