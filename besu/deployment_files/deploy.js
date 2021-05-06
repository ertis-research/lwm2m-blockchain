const Web3 = require('web3');
const HDWalletProvider = require('@truffle/hdwallet-provider');

if(process.argv.length !== 3 || isNaN(process.argv[2])) return;

const initial_number = process.argv[2];

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
  [ { "inputs": [ { "internalType": "uint256", "name": "num", "type": "uint256" } ], "stateMutability": "nonpayable", "type": "constructor" }, { "inputs": [], "name": "getNumber", "outputs": [ { "internalType": "uint256", "name": "", "type": "uint256" } ], "stateMutability": "view", "type": "function" }, { "inputs": [ { "internalType": "uint256", "name": "num", "type": "uint256" } ], "name": "store", "outputs": [], "stateMutability": "nonpayable", "type": "function" } ]
);
web3.eth.getAccounts().then(response => {
  const accounts = response;
  
  storageContract.deploy({
    data: '0x608060405234801561001057600080fd5b506040516101dc3803806101dc83398181016040528101906100329190610054565b806000819055505061009e565b60008151905061004e81610087565b92915050565b60006020828403121561006657600080fd5b60006100748482850161003f565b91505092915050565b6000819050919050565b6100908161007d565b811461009b57600080fd5b50565b61012f806100ad6000396000f3fe6080604052348015600f57600080fd5b506004361060325760003560e01c80636057361d146037578063f2c9ecd814604f575b600080fd5b604d600480360381019060499190608f565b6069565b005b60556073565b6040516060919060c2565b60405180910390f35b8060008190555050565b60008054905090565b60008135905060898160e5565b92915050565b60006020828403121560a057600080fd5b600060ac84828501607c565b91505092915050565b60bc8160db565b82525050565b600060208201905060d5600083018460b5565b92915050565b6000819050919050565b60ec8160db565b811460f657600080fd5b5056fea2646970667358221220945acbf64c4a45603083135d8ae8c6812e7415be272b66ca1ae10e280376b10364736f6c63430008010033',
    arguments: [initial_number]
  }).send({
    from: accounts[1],
    gas: '4700000',
  }).then(response => {
    console.log(`Contract mined! address: ${response._address}`);
  }).catch(error => {
    console.log(error);
  });
});

provider.engine.stop();