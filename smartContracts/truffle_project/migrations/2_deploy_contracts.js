const ClientStore = artifacts.require("ClientStore");
const AnomalyStore = artifacts.require("AnomalyStore");
const UserStore = artifacts.require("UserStore");
const ACLStore = artifacts.require("AclStore");

const whitelist_addresses = [];

module.exports = function (deployer) {
  // https://www.trufflesuite.com/docs/truffle/getting-started/running-migrations#deployer
  deployer.deploy(ClientStore, whitelist_addresses);
  deployer.deploy(AnomalyStore, whitelist_addresses);
  deployer.deploy(UserStore, whitelist_addresses);
  deployer.deploy(ACLStore, whitelist_addresses);
}