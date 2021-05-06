# Deploy a smart contract
This guide shows how to deploy a smart contract in a Besu network. All necessary files are stored under folder [deployment_files](./deployment_files).

### Prerequisites
- Complete guide [consensus_protocols/01_ethash.md](consensus_protocols/01_ethash.md) to start up a Besu private network using Ethash as consensus protocol.
- Create a new Node.js project (`npm init -y`).
- Install `web3` and `@truffle/hdwallet-provider@` dependencies. The version of the second dependency must be an earlier version than 1.3.0 due to a compatibility issue with Besu (`chainId`).
```bash
npm install web3 @truffle/hdwallet-provider@1.2.2
```
- Write a smart contract. In this guide, a [simple storage](deployment_files/Storage.sol) will be used.

### Compile the smart contract
There are several ways to compile a smart contract: using a compiler, using Truffle, using Remix, etc. But the easiest way is through Remix, which is an online IDE that eases smart contract development. In this [link](https://github.com/ertis-research/ethereum-fundamentals/blob/main/tutorials/remix.md#solidity-compiler) you will find how to compile smart contracts using this online IDE.

No matter which compiler you use, compilation generates two objects needed for deployment:
- **ABI (Application Binary Interface)**. This interface is the standard way to interact with smart contracts in the Ethereum ecosystem, where data is encoded according to its type.
- **Contract bytecode**. It is the code that will be stored on-chain and describes the smart contract.

### Deploy the contract
Once the smart contract is compiled, you have to create a JavaScript file to deploy that contract. But, before moving to write the deployment code, it is necessary to point that Hyperledger Besu has a small flaw: **it does not support key management inside the client**. That is why we installed `@truffle/hdwallet-provider` library, which allows to import Ethereum accounts from their private keys.

Previously, when creating the Besu network, its [genesis file](consensus_protocols/genesis_files/ethashGenesis.json) was written including two accounts. In order to use those accounts to deploy the desired smart contract, you have to import the using the above mentioned library. The code to achive this purpose is shown down below:
```js
const Web3 = require('web3');
const HDWalletProvider = require('@truffle/hdwallet-provider');

if(process.argv.length !== 3 || isNaN(process.argv[2])) return;

const initial_number = process.argv[2];

// Create the provider
const provider = new HDWalletProvider({
  privateKeys: [
    "<account1-key>",
    "<account2-key>",
  ],
  providerOrUrl: "<JSON-RPC-http-endpoint>",
});

const web3 = new Web3(provider);
const storageContract = new web3.eth.Contract($CONTRACT_ABI);
web3.eth.getAccounts().then(response => {
  const accounts = response;
  storageContract.deploy({
    data: '$CONTRACT_BYTECODE',
    arguments: [initial_number]
  }).send({
    from: accounts[0],
    gas: '4700000'
  }).then(response => {
    console.log(`Contract mined! address: ${response._address}`);
  });
})

provider.engine.stop();
```

Now, execute the deployment code. There are two options:
- `node deploy.js $INITIAL_NUMBER`
- `npm run deploy $INITIAL_NUMBER` (script added to **package.json**)

It is really important to write down, store, keep safe, etc. the contract address. Without this address will be impossible to interact with the newly deployed contract.

### Interact with the deployed contract
Once the contract has been deployed, it is possible to interact with it. The code is almost the same, but in this case it is mandatory to include the contract address.
```js
const storageContract = new web3.eth.Contract(
  $CONTRACT_ABI,
  $CONTRACT_ADDRESS
);
```

Then, calling contract methods is possible through field `methods` of the contract instance.
```js
// Using 'store' function
const new_value = 17;
storageContract.methods.store(new_value)
  .send({ from: accounts[0] })
  .then(response => {
    console.log('Value changed');
  });

// Or using 'getNumber' function
storage.methods.getNumber()
  .call({ from: accounts[1] })
  .then(response => {
    console.log(`Current value: ${response}`);
  });
```

Finally, execute interaction files. Again, there are two options:
- Function **getNumber**:
  - `node get.js`
  - `npm run get` (script added to **package.json**)
- Function **store**:
  - `node set.js $NEW_VALUE`
  - `npm run set $NEW_VALUE` (script added to **package.json**)


## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/