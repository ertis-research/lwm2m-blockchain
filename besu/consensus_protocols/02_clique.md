# Create a private network using Clique (PoA)
This guide shows how to create a Besu network with 3 nodes using Clique (Proof-of-Authority)

### 1) Create directories
Each node requires a data directory to store blockchain data.
```bash
mkdir -p clique_net/node-1/data clique_net/node-2/data clique_net/node-3/data
```

Using command `tree` we can verify our directory structure.
```bash
$ sudo apt-get install tree # If not installed
$ tree clique_net/
> clique_net/
> ├── node-1
> │   └── data
> ├── node-2
> │   └── data
> └── node-3
>     └── data
```

### 2) Get Node-1 address
In Clique networks, you must include the address of at least one initial signer in the genesis file. To get the address of a node use `public-key export-address` subcommand (address will be shown on terminal):
```bash
besu --data-path=clique_net/node-1/data public-key export-address
```

### 3) Create genesis file
The properties specific to Clique are:
- `blockperiodseconds`: the block time, in seconds.
- `epochlenght`: the number of blocks after which to reset all votes.
- `extraData`: it consists of:
  - **0x prefix**
  - **Vanity data** - 64 hex characters (all zeros)
  - **A concatenated list of initial signer addresses** - 40 hex characters each signer address
  - **Proposer signature**. In the genesis block there is no initial proposer so the proposer signature is all zeros - 130 hex characters

In our example, the genesis file will be named `cliqueGenesis.json` and created in `clique_net` directory.
```json
{
  "config": {
    ...
    "clique": {
      "blockperiodseconds": 15,
      "epochlength": 30000
   }
  },
  "extraData":"0x0000000000000000000000000000000000000000000000000000000000000000<addresses_list>0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
  ...
}
```

### 4) Start node-1 as a bootnode
```bash
cd node-1/
besu --data-path=data --genesis-file=../cliqueGenesis.json \
     --rpc-http-enabled --rpc-http-api=ETH,NET,WEB3,CLIQUE \
     --host-allowlist="*" --rpc-http-cors-origins="all"
```

This command enables:
- The HTTP JSON-RPC API using the `--rpc-http-enabled` option
- The `ETH`, `NET`, `WEB3` and `CLIQUE` APIs using the `--rpc-http-api` option. APIs enabled by default are `ETH`, `NET` and `WEB3`.
- All-host access to the HTTP JSON-RPC API using the `--host-allowlist` option
- All-domain access to the node through the HTTP JSON-RPC API using the `--rpc-http-cors-origins` option

### 5) Start node-2 and node-3
Once we have copied node-1 enode URL, we can run node-2 and node-3.
```bash
# Open a new terminal at 'clique_net' folder
cd node-2/
besu --data-path=data --genesis-file=../cliqueGenesis.json \
     --bootnodes=<node-1 enode URL> --p2p-port=30304
# Open another terminal at 'clique_net' folder
cd node-3/
besu --data-path=data --genesis-file=../cliqueGenesis.json --bootnodes=<node-1 enode URL> --p2p-port=30305
```

### 6) Confirm the network is working
In other terminal, try to confirm the nodes are working as peers.
```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"net_peerCount","params":[],"id":1}' localhost:8545
```

### 7) Adding and removing signers
To add or remove a signer (or list how many signers are in the network) it is necessary to use Clique API, which was enabled on step [4](#4-start-node-1-as-a-bootnode). A majority of existing signers must agree yo add or remove a signer, which means more than a 50% of signers must agree.

#### Adding a signer
To propose adding a signer, call `clique_propose` method, specifying the address of the proposed signer and `true`. The command to propose a new signer is as follows:
```bash
curl -X POST --data \
'{"jsonrpc":"2.0","method":"clique_propose","params":["0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73", true], "id":1}' \
localhost:8545
```

#### Removing a signer
This process is almost the same, but in this case the second parameter of method `clique_propose` is `false`.
```bash
curl -X POST --data \
'{"jsonrpc":"2.0","method":"clique_propose","params":["0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73", false], "id":1}' \
localhost:8545
```

#### Epoch transition
At each epoch transition, Clique discards all pending votes. Existing proposal remain in effect and signers re-add their votes the next time they create a block. The number of blocks between epoch transitions is defined in genesis file (parameter `epochlenght`).

### Useful links
[Create a private network using Clique](https://besu.hyperledger.org/en/stable/Tutorials/Private-Network/Create-Private-Clique-Network/)

[public-key command](https://besu.hyperledger.org/en/stable/Reference/CLI/CLI-Subcommands/#export-address)

[Clique - Hyperledger Besu](https://besu.hyperledger.org/en/stable/HowTo/Configure/Consensus-Protocols/Clique/)

## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/