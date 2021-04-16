# Interacting with a Besu node

### HTTP JSON-RPC
The most common way to comunicate with a Besu node it is through JSON-RPC over HTTP. This protocol is the same which Web3j (the Ethereum library we utilise at the backend) uses. In order to enable the HTTP JSON-RPC service in a Besu node, it is mandatory to include the flag `--rpc-http-enabled` when running a Besu node.
```bash
besu --rpc-http-enabled
```

By default, the HTTP JSON-RPC endpoint is `http://127.0.0.1:8545`. Nevertheless, using the options `--rpc-http-port` or `--rpc-http-host` endpoint can be updated. For example, if we need the HTTP JSON-RPC listening at `172.1.0.10:8555` the syntax should be at follows:
```bash
besu --rpc-http-enabled --rpc-http-port=8555 --rpc-http-host=172.1.0.10
```

Hyperledger Besu has an API with lots of methods, implemented to allow a proper communication between users or network administrators with Besu nodes. More information about these methods is detailed [here](https://besu.hyperledger.org/en/stable/Reference/API-Methods/). From a terminal, a HTTP JSON-RPC call is made as follows:
```bash
# Assuming the default endpoint (127.0.0.1:8545) and calling 'net_version' method
curl -X POST --data '{"jsonrpc":"2.0","method":"net_version","params":[],"id":1}' 127.0.0.1:8545
``` 

### WebSockets JSON-RPC
It is also possible to communicate with a Besu node using WebSockets. Similarly to HTTP, to enable WebSockets flag `--rpc-ws-enable` is mandatory. By default, the WebSockets enpoint is `ws://127.0.0.1:8546`. Options `rpc-ws-host` and `rpc-ws-port` allow to update the Websockets endpoint.
```bash
besu --rpc-ws-enabled --rpc-ws-host=172.1.0.11 --rpc-ws-port=8556
```

### TLS
Work in progress

## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/