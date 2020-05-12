pragma solidity >=0.4.22 <0.7.0;

contract AnomalyStore {

  struct AnomalyData {
    uint256 timestamp;
    bytes32 endpoint;
    uint256 emergency_level;
    uint256 encoded_data; //data received from nodes
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
    uint256 ts, bytes32 endpoint, uint256 emergency, uint256 encoded_data
  ) public onlyWhitelist {

    cont++;
    anomalies[cont].timestamp = ts;
    anomalies[cont].endpoint = endpoint;
    anomalies[cont].emergency_level = emergency;
    anomalies[cont].encoded_data = encoded_data; // Anomaly added
  }

  function getAnomaly(uint256 id) public onlyWhitelist view returns(
    uint256, bytes32, uint256, uint256
  ) {
    return(anomalies[id].timestamp, anomalies[id].endpoint,
      anomalies[id].emergency_level, anomalies[id].encoded_data);
  }

  function getAnomaliesByEndpoint(bytes32 endpoint) public onlyWhitelist view returns(
    uint256[] memory, uint256[] memory, uint256[] memory
  ) {
    uint256[] memory res_ts = new uint256[](cont);
    uint256[] memory res_emer = new uint256[](cont);
    uint256[] memory res_data = new uint256[](cont);
    uint256 res_cont = 0;

    for (uint256 i = 1; i <= cont; i++) {
      if(anomalies[i].endpoint == endpoint) {
        res_ts[res_cont] = anomalies[i].timestamp;
        res_emer[res_cont] = anomalies[i].emergency_level;
        res_data[res_cont] = anomalies[i].encoded_data;
        res_cont++;
      }
    }

    return (res_ts, res_emer, res_data);
  }

  function getAnomaliesBetweenDates(uint256 ts_start, uint256 ts_end) public onlyWhitelist view returns(
    uint256[] memory, bytes32[] memory, uint256[] memory, uint256[] memory
  ) {
    uint256[] memory res_ts = new uint256[](cont);
    bytes32[] memory res_end = new bytes32[](cont);
    uint256[] memory res_emer = new uint256[](cont);
    uint256[] memory res_data = new uint256[](cont);
    uint256 res_cont = 0;

    for (uint256 i = 1; i <= cont; i++) {
      if(anomalies[i].timestamp >= ts_start && anomalies[i].timestamp <= ts_end) {
        res_ts[res_cont] = anomalies[i].timestamp;
        res_end[res_cont] = anomalies[i].endpoint;
        res_emer[res_cont] = anomalies[i].emergency_level;
        res_data[res_cont] = anomalies[i].encoded_data;
        res_cont++;
      }
    }

    return (res_ts, res_end, res_emer, res_data);
  }

  function getAllAnomalies() public onlyWhitelist view returns(
    uint256[] memory, bytes32[] memory, uint256[] memory, uint256[] memory
  ) {
    uint256[] memory res_ts = new uint256[](cont);
    bytes32[] memory res_end = new bytes32[](cont);
    uint256[] memory res_emer = new uint256[](cont);
    uint256[] memory res_data = new uint256[](cont);

    for (uint256 i = 1; i <= cont; i++) {
      res_ts[i-1] = anomalies[i].timestamp;
      res_end[i-1] = anomalies[i].endpoint;
      res_emer[i-1] = anomalies[i].emergency_level;
      res_data[i-1] = anomalies[i].encoded_data;
    }

    return (res_ts, res_end, res_emer, res_data);
  }
}