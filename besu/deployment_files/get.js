const Web3 = require('web3');
const HDWalletProvider = require('@truffle/hdwallet-provider');

if(process.argv.length !== 2) return;

// Create the provider
const provider = new HDWalletProvider({
  privateKeys:[
    "8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63",
    "ae6ae8e5ccbfb04590405997ee2d52d2b330726137b875053c36d94e974d162f",
  ],
  providerOrUrl: "http://localhost:8545",
});

const web3 = new Web3(provider);
const storageContract = new web3.eth.Contract(
  [ { "inputs": [ { "internalType": "uint256", "name": "num", "type": "uint256" } ], "stateMutability": "nonpayable", "type": "constructor" }, { "inputs": [], "name": "getNumber", "outputs": [ { "internalType": "uint256", "name": "", "type": "uint256" } ], "stateMutability": "view", "type": "function" }, { "inputs": [ { "internalType": "uint256", "name": "num", "type": "uint256" } ], "name": "store", "outputs": [], "stateMutability": "nonpayable", "type": "function" } ],
  "0xb9A219631Aed55eBC3D998f17C3840B7eC39C0cc"
);
web3.eth.getAccounts().then(response => {
  const accounts = response;
  
  storageContract.methods.getNumber()
    .call({ from: accounts[1] })
    .then(response => {
      console.log(`Current value: ${response}`);
    });
});
  
provider.engine.stop();