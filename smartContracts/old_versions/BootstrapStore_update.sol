pragma solidity >=0.4.22 <0.7.0;

contract BootstrapStore {

  // Client configuration requires a LWM2M Server config. and a LWM2M BS Server config.
  struct ClientConfig {
    bytes32 serverURL;
    bytes32 identity;
    bytes32 key;
    bytes32 bs_serverURL;
    bytes32 bs_identity;
    string bs_key;
  }

  // This declares a state variable that stores a 'ClientConfig' struct for each client endpoint
  mapping(bytes32 => ClientConfig) clients;
  // This declares a state variable that stores all client endpoints
  bytes32[] clientEndpoints;

  // CREATE
  function storeClientEndpoint(
    bytes32 clientEndpoint, // Client Endpoint
    bytes32 serverURL, bytes32 identity, bytes32 key, // LWM2M Server config.
    bytes32 bs_serverURL, bytes32 bs_identity, string memory bs_key // LWM2M BS Server config.
  ) public returns(int) {

    int exists = clientEndpointExists(clientEndpoint);
    if(exists != -1) {
      return -1; // Client already exists
    }

    // Why I used 'memory'?
    // https://medium.com/coinmonks/what-the-hack-is-memory-and-storage-in-solidity-6b9e62577305
    ClientConfig memory clientConfig = ClientConfig({
      serverURL: serverURL,
      identity: identity,
      key: key,
      bs_serverURL: bs_serverURL,
      bs_identity: bs_identity,
      bs_key: bs_key
    });

    clients[clientEndpoint] = clientConfig;
    clientEndpoints.push(clientEndpoint);

    return 0; // Client stored

  }

  // READ
  function getClientEndpoint(bytes32 clientEndpoint) public view returns(
  // Why I used 'view'?
  // https://ethereum.stackexchange.com/questions/39561/solidity-function-state-mutability-warning#answers
    bytes32, bytes32, bytes32, // LWM2M Server config.
    bytes32, bytes32, string memory // LWM2M BS Server config.
  ) {

    int exists = clientEndpointExists(clientEndpoint);

    if(exists == -1) {
      return("", "", "", "", "", ""); // Endpoint does not exist
    }

    (bytes32 serverURL, bytes32 identity, bytes32 key,
    bytes32 bs_serverURL, bytes32 bs_identity, string memory bs_key) = getClientConfig(clientEndpoint);

    return(serverURL, identity, key, bs_serverURL, bs_identity, bs_key);
  }

  // READ ALL
  function getAllEndpoints() public view returns(bytes32[] memory){
    return clientEndpoints;
  }

  // UPDATE
  function updateClientEndpoint(
    bytes32 clientEndpoint, bytes32 field, string memory newValue
  ) public returns(int) {

    int exists = clientEndpointExists(clientEndpoint);

    if(exists == -1) {
      return -1; // Endpoint does not exist
    }

    if(field == "url") {
      clients[clientEndpoint].serverURL = stringToBytes32(newValue);
      return 0; // Correct field update
    }

    if(field == "id") {
      clients[clientEndpoint].identity = stringToBytes32(newValue);
      return 0; // Correct field update
    }

    if(field == "key") {
      clients[clientEndpoint].key = stringToBytes32(newValue);
      return 0; // Correct field update
    }

    if(field == "bs_url") {
      clients[clientEndpoint].bs_serverURL = stringToBytes32(newValue);
      return 0; // Correct field update
    }

    if(field == "bs_id") {
      clients[clientEndpoint].bs_identity = stringToBytes32(newValue);
      return 0; // Correct field update
    }

    if(field == "bs_key") {
      clients[clientEndpoint].bs_key = newValue;
      return 0; // Correct field update
    }

    return -2; // Field does not exist

  }

  // DELETE
  function deleteClientEndpoint(
    bytes32 clientEndpoint
  ) public returns(int) {

    int exists = clientEndpointExists(clientEndpoint);

    if(exists == -1) {
      return -1; // Endpoint does not exist
    }

    for (uint i = 0; i < clientEndpoints.length; i++){
      if(clientEndpoint == clientEndpoints[i]){
        removeItem(i);
        break;
      }
    }
    delete clients[clientEndpoint];
    return 0; // Endpoint deleted successfully
  }

  // PRIVATE FUNCTIONS
  function clientEndpointExists(bytes32 clientEndpoint) private view returns(int) {
    for (uint i = 0; i < clientEndpoints.length; i++){
      if(clientEndpoint == clientEndpoints[i]){
        return int(i);
      }
    }
    return -1;
  }

  function getClientConfig(bytes32 clientEndpoint) private view returns(
    bytes32, bytes32, bytes32, // LWM2M Server config.
    bytes32, bytes32, string memory // LWM2M BS Server config.
  ) {

    ClientConfig memory clientConfig = clients[clientEndpoint];
    bytes32 serverURL = clientConfig.serverURL;
    bytes32 identity = clientConfig.identity;
    bytes32 key = clientConfig.key;
    bytes32 bs_serverURL = clientConfig.bs_serverURL;
    bytes32 bs_identity = clientConfig.bs_identity;
    string memory bs_key = clientConfig.bs_key;

    return(serverURL, identity, key, bs_serverURL, bs_identity, bs_key);
  }

  // https://ethereum.stackexchange.com/questions/9142/how-to-convert-a-string-to-bytes32
  function stringToBytes32(string memory source) private pure returns (bytes32 result) {
    bytes memory tempEmptyStringTest = bytes(source);
    if (tempEmptyStringTest.length == 0) {
        return 0x0;
    }

    /** Inline assembly is a way to access the Ethereum Virtual Machine at a low level.
    This bypasses several important safety features and checks of Solidity.
    You should only use it for tasks that need it, and only if you are confident with using it.
    */
    assembly {
        result := mload(add(source, 32))
    }
  }

  function removeItem(uint index) private {
    for(uint i = index; i < clientEndpoints.length - 1; i++){
      clientEndpoints[i] = clientEndpoints[i+1];
    }
    clientEndpoints.pop();
  }
}