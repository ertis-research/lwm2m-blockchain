# Smart Contracts
Smart Contracts used in this project are stored here. All of them are coded using [Solidity](https://solidity-es.readthedocs.io/es/latest/). In order to compile Smart Contracts and generate Java wrappers is neccesary to install the Solidity compiler (*solc*) and a Java library for working with Smart Contracts (*web3j*).

## Generating Java wrappers
Following next steps you can generate Java wrapper code of a Smart Contract.

### Install web3j CLI
On windows (powershell)
```powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://raw.githubusercontent.com/web3j/web3j-installer/master/installer.ps1'))
```

On Linux or Mac
```bash
curl -L https://get.web3j.io | sh
```

After instalation we can verify everything went successfull with this command:
```bash
web3j version
```

### Install solc (Solidity compiler)
```bash
npm install -g solc
```
After installation verify using this command:
```bash
solcjs --version
```

### Compile smart contract
```bash
solcjs Contract.sol --bin --abi --optimize -o compiled/
```

### Generate wrapper code using web3j's CLI
```bash
web3j solidity generate -a Contract.abi -b Contract.bin -c Contract -o output/ -p ertis.uma
```

## Deploying contracts with Truffle
A [Truffle](https://www.trufflesuite.com/truffle) project has been created to ease the smart contracts deployment. Follow the steps described below to deploy the developed contracts. If you have any doubts check Truffle docs or [this tutorial](https://github.com/ertis-research/ethereum-fundamentals/blob/main/tutorials/truffle.md).

### Install wallet provider
In order to deploy on Besu or Ropsten networks, it is necessary to use a wallet provider.
```bash
cd truffle_project/
npm install @truffle/hdwallet-provider
```

### Compile smart contracts
Option `-all` recompiles all contracts, even when no changes have been applied.
```bash
truffle compile
```

### Deploy smart contracts
The **truffle-config.js** file allows to configure the communication with multiple networks. In this case, 3 networks have been set up: Ganache (local, for development purposes), Ropsten and Besu. The latter two require to modify some variables existing on **truffle-config.js**:
- Ropsten: 
  - Accounts private keys: `ropsten_keys`
  - Infura project ID: `infuraKey`
- Besu:
  - Accounts private keys: `besu_keys`
  - JSON-RPC API endpoint: `besu_api_endpoint`

After the changes, deploy the smart contracts:
```bash
truffle deploy --network ganache
# OR
truffle deploy --network ropsten
# OR
truffle deploy --network besu
```