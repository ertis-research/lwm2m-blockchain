# Create a private network using Ethash (PoW)
This guide shows how to create a Besu private network with 3 nodes using Ethash (Proof-of-Work)

### 1) Create directories
Each node requires a data directory to store blockchain data.
```bash
mkdir -p private_net/node-1/data private_net/node-2/data private_net/node-3/data
```

Using command `tree` we can verify our directory structure
```bash
sudo apt-get install tree # If not installed
tree private_net/
# Output should be this
private_net/
├── node-1
│   └── data
├── node-2
│   └── data
└── node-3
    └── data
```

### 2) Create genesis file
As explained [previously](./02_config_genesis_files.md), when running a private network it is necessary a genesis file. All nodes in the network must use the same genesis file. Ethash networks' config file requires a parameter named `fixeddifficulty` inside `ethash` object. This will keep the network's difficulty constant (enabling fast block mining) and override `difficulty` parameter from genesis file.

In our example, it will be named `ethashGenesis.json` and created in `private_net` directory.
```json
{
  "config": {
      "constantinoplefixblock": 0,
      "ethash": {
        "fixeddifficulty": 1000
      },
       "chainID": 2021
   },
  "nonce": "0x42",
  "gasLimit": "0x1000000",
  "difficulty": "0x10000",
  "alloc": {
    "fe3b557e8fb62b89f4916b721be55ceb828dbd73": {
      "privateKey": "8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63",
      "comment": "private key and this comment are ignored.  In a real chain, the private key should NOT be stored",
      "balance": "0xad78ebc5ac6200000"
    },
    "f17f52151EbEF6C7334FAD080c5704D77216b732": {
      "privateKey": "ae6ae8e5ccbfb04590405997ee2d52d2b330726137b875053c36d94e974d162f",
      "comment": "private key and this comment are ignored.  In a real chain, the private key should NOT be stored",
      "balance": "90000000000000000000000"
    }
  }
}
```

### 3) Start node-1 as a bootnode
Using bootnodes is a method for initially discovering peers. Bootnodes are regular nodes used to discover other nodes. For mainnet and testnets, Besu has an internal list of enode URLs and uses this list automatically when you specify `--network` option. However, for private networks it is necessary to specify at least one bootnode for development purposes. Production private networks required two or more bootnodes.
```bash
cd node-1/
besu --data-path=data --genesis-file=../ethashGenesis.json \
     --miner-enabled --miner-coinbase fe3b557e8fb62b89f4916b721be55ceb828dbd73 \
     --rpc-http-enabled --host-allowlist="*" --rpc-http-cors-origins="all"
```
Before moving on to the next step, we have to copy __node-1 enode URL__. It will be displayed just a few seconds after we execute the above command. The information we are looking for looks like this:
```bash
2021-01-29 15:47:33.420+01:00 | main | INFO  | DefaultP2PNetwork | Enode URL enode://d263e7eff58923bf622fc0e9634687db3f62ad0d6aeb6d431aa1b33636dc9aefdb1d50f468b08bde9e3a1cafa314df306dbefc70472a7e23a0f8b669c4a65b33@127.0.0.1:30303
```

### 4) Start node-2 and node-3
Once we have copied node-1 enode URL, we can run node-2 and node-3.
```bash
# Open a new terminal at 'private_net' folder
cd node-2/
besu --data-path=data --genesis-file=../ethashGenesis.json \
     --bootnodes=<node-1 enode URL> --p2p-port=30304
```

```bash
# Open another terminal at 'private_net' folder
cd node-3/
besu --data-path=data --genesis-file=../ethashGenesis.json \
     --bootnodes=<node-1 enode URL> --p2p-port=30305
```

### 5) Confirm the network is working
In other terminal, try to confirm the nodes are working as peers
```bash
curl -X POST --data '{"jsonrpc":"2.0","method":"net_peerCount","params":[],"id":1}' localhost:8545
```

### Useful links
[Create a private network using Ethash](https://besu.hyperledger.org/en/stable/Tutorials/Private-Network/Create-Private-Network/)

## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/