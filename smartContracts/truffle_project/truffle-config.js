const HDWalletProvider = require('@truffle/hdwallet-provider');

// Ropsten config 
const ropsten_keys = []; // Put your Ropsten accounts private keys here
const infuraKey = "YOUR_INFURA_PROJECT_ID";

// Besu config
const besu_keys = []; // Put your Besu accounts private keys here
const besu_api_endpoint = "http://localhost:8545"; // Replace it if needed

module.exports = {
  contracts_directory: "../", // Will also compile smart contracts under folder 'contracts'
  networks: {
    ganache: { // If you modified Ganache default configuration, you have to make changes here
      host: "127.0.0.1",
      port: 7545,
      network_id: "5777",
    },
    ropsten: { // Using Infura
      provider: () => {
        return new HDWalletProvider({
          privateKeys: ropsten_keys,
          providerOrUrl: `https://ropsten.infura.io/v3/${infuraKey}`,
        });
      },
      network_id: 3,  // Ropsten network id
      gas: 5500000,   // Ropsten has a lower block limit than mainnet
    },
    besu: {
      provider: () => {
        return new HDWalletProvider({
          privateKeys: besu_keys,
          providerOrUrl: besu_api_endpoint,
        });
      },
      network_id: "2021", // Replace it if needed
    },
  },
};
