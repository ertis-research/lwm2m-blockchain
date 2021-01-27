# Configuration and Genesis files

## Configuration file
As seen previously, there are many options to configure a Besu node. It means a really long command if the required configuration is complex. To prevent this problem it is possible to specify command line options in a file, using a __TOML configuration file__. When running a Besu node with a configuration file it is necessary to include `--config-file` option. The main advantage of using a configuration file is you can reuse it in different node startups.
```bash
besu --config-file=path/to/config_file.toml
```

To override an option specified in the configuration file, either specify the same option on the command line or as an environment variable. Options specified in more than one place take into account this order of precedence: 1) command line, 2) environment variables and 3) configuration file.

### TOML specification
A TOML file is composed of key/value pairs. Each key is the same as the corresponding command line option name without the leading dashes (`--`). Specific differences between the command line and the TOML file format are:
* Comma-separated list on the command line are string arrays in the TOML file.
* Enclose file paths, hexadecimal numbers, URLs, and \<host:port\> values in quotes.

For clarification of the TOML specfication, a valid TOML file is shown
```toml
# Valid TOML config file
data-path="~/besudata" # Path

# Network
bootnodes=["enode://001@123:4567", "enode://002@123:4567", "enode://003@123:4567"]

rpc-http-host="5.6.7.8"
rpc-http-port=5678

rpc-ws-host="9.10.11.12"
rpc-ws-port=9101

# Chain
genesis-file="~/genesis.json" # Path to the custom genesis file
```
## Genesis file
The genesis file defines the first block in the chain, which indicates the chain you want to join. For Ethereum Mainnet and testnets (like Ropsten or Rinkeby), its genesis files are already defined in Besu and used when specifying a public network using the `--network` command line option. Those genesis files are stored and updated by Hyperledger Besu team [here](https://github.com/hyperledger/besu/tree/master/config/src/main/resources)

For private networks, a new genesis file (__JSON format__) is required. In order to specify a genesis file, command line option `--genesis-file` must be included. This file specifies the __network-wide settings, so all nodes in a network must use the same genesis file. An example of genesis file is shown below.

```json
{
  "config": {
    "chainId": 2018,
    "muirglacierblock": 0,
    "ibft2": {
      "blockperiodseconds": 2,
      "epochlength": 30000,
      "requesttimeoutseconds": 4
    }
  },
  "nonce": "0x0",
  "timestamp": "0x58ee40ba",
  "extraData": "0xf83ea00000000000000000000000000000000000000000000000000000000000000000d5949811ebc35d7b06b3fa8dc5809a1f9c52751e1deb808400000000c0",
  "gasLimit": "0x1fffffffffffff",
  "difficulty": "0x1",
  "mixHash": "0x63746963616c2062797a616e74696e65206661756c7420746f6c6572616e6365",
  "coinbase": "0x0000000000000000000000000000000000000000",
  "alloc": {
    "9811ebc35d7b06b3fa8dc5809a1f9c52751e1deb": {
      "balance": "0xad78ebc5ac6200000"
    }
  }
}
```