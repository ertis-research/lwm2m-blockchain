pragma solidity >=0.4.22 <0.7.0;

contract AnomalyStore {

  struct AnomalyData {
    uint256 timestamp;
    bytes32 endpoint;
    uint256 emergency_level;
    /**
    data received from nodes: temp while testing
     */
    bytes32 temperature;
  }

  mapping (uint256 => AnomalyData) anomalies;
  uint256 cont;

  address public owner; // Contract owner
  mapping(address => bool) public whitelist; // White list of addresses that can execute transactions

  // Can add some addresses to white list during contract deployment
  constructor(address[] memory addresses) public {
    owner = msg.sender;
    whitelist[msg.sender] = true;
    if(addresses.length > 0) {
      for(uint i = 0; i < addresses.length; i++){
        whitelist[addresses[i]] = true;
      }
    }
    cont = 0;
  }

  modifier onlyOwner {
    require(msg.sender == owner, "Only contract owner can execute this");
    _;
  }

  modifier onlyWhitelist {
    require(whitelist[msg.sender], "Only whitelisted accounts can execute this");
    _;
  }

  function setPermission(address addr, bool permission) public onlyOwner {
    whitelist[addr] = permission;
  }

  function addAnomaly(
    uint256 ts, bytes32 endpoint, uint256 emergency, bytes32 temp
  ) public onlyWhitelist {

    cont++;
    anomalies[cont].timestamp = ts;
    anomalies[cont].endpoint = endpoint;
    anomalies[cont].emergency_level = emergency;
    anomalies[cont].temperature = temp; // Anomaly added
  }

  function getAnomaly(uint256 id) public onlyWhitelist view returns(
    uint256, bytes32, uint256, bytes32
  ) {
    return(anomalies[id].timestamp, anomalies[id].endpoint,
      anomalies[id].emergency_level, anomalies[id].temperature);
  }

  function getAnomaliesByEndpoint(bytes32 endpoint) public onlyWhitelist view returns(
    uint256[] memory, uint256[] memory, bytes32[] memory
  ) {
    uint256[] memory res_ts = new uint256[](cont);
    uint256[] memory res_emer = new uint256[](cont);
    bytes32[] memory res_temp = new bytes32[](cont);
    uint256 res_cont = 0;

    for (uint256 i = 1; i <= cont; i++) {
      if(anomalies[i].endpoint == endpoint) {
        res_ts[res_cont] = anomalies[i].timestamp;
        res_emer[res_cont] = anomalies[i].emergency_level;
        res_temp[res_cont] = anomalies[i].temperature;
        res_cont++;
      }
    }

    return (res_ts, res_emer, res_temp);
  }

  // getAnomaliesBetweenDates
}