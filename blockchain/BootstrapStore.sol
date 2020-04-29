pragma solidity >=0.4.22 <0.7.0;

contract BootstrapStore {

    // Client configuration requires a LWM2M BS Server config. and a LWM2M Server config.
    struct ClientConfig {
        bytes32 url_bs;
        bytes32 id_bs;
        bytes32 key_bs;
        bytes32 url_s;
        bytes32 id_s;
        bytes32 key_s;
    }

    // This declares a state variable that stores a 'ClientConfig' struct for each client endpoint
    mapping (bytes32 => ClientConfig) clients;
    // This declares a state variable that stores all client endpoints
    bytes32[] endpoints;

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

    function addClient(
        bytes32 endpoint, // Client Endpoint
        bytes32 url_bs, bytes32 id_bs, bytes32 key_bs, // LWM2M Server config.
        bytes32 url_s, bytes32 id_s, bytes32 key_s // LWM2M BS Server config.
    ) public onlyWhitelist {
        
        int exists = existsClient(endpoint);
        // require triggers a revert() with the message specified if condition is false
        // https://solidity.readthedocs.io/en/v0.4.24/control-structures.html#error-handling-assert-require-revert-and-exceptions
        require(exists == -1, "Client Endpoint already exists");

        clients[endpoint].url_bs = url_bs;
        clients[endpoint].id_bs = id_bs;
        clients[endpoint].key_bs = key_bs;
        clients[endpoint].url_s = url_s;
        clients[endpoint].id_s = id_s;
        clients[endpoint].key_s = key_s;
        endpoints.push(endpoint); // Client added
    }

    function getClient(bytes32 endpoint) public onlyWhitelist view returns (
        // Why I used 'view'?
        // https://ethereum.stackexchange.com/questions/39561/solidity-function-state-mutability-warning#answers
        bytes32, bytes32, bytes32, // LWM2M BS Server config
        bytes32, bytes32, bytes32 // LWM2M Server config
    ) {
        int exists = existsClient(endpoint);
        if(exists == -1) {
            return("", "", "", "", "", ""); // Endpoint does not exist
        }
        
        return (
            clients[endpoint].url_bs, clients[endpoint].id_bs, clients[endpoint].key_bs, // BS Server config
            clients[endpoint].url_s, clients[endpoint].id_s, clients[endpoint].key_s); // Server config
    }

    function getAllClients() public onlyWhitelist view returns(bytes32[] memory){
        return endpoints;
    }

    function getClientsByServer(bytes32 url_s) public onlyWhitelist view returns(bytes32[] memory){
        bytes32[] memory serverEndpoints = new bytes32[](endpoints.length);
        uint cont = 0;

        for (uint i = 0; i < endpoints.length; i++) {
            if(clients[endpoints[i]].url_s == url_s) {
                serverEndpoints[cont] = endpoints[i];
                cont++;
            }
        }

        return serverEndpoints;
    }

    function removeClient(bytes32 endpoint) public onlyWhitelist {
        int pos = existsClient(endpoint);
        // require triggers a revert() with the message specified if condition is false
        require(pos != -1, "Endpoint does not exist");
        
        for (uint i = uint(pos); i<endpoints.length-1; i++){
            endpoints[i] = endpoints[i+1];
        }

        endpoints.pop();
        delete clients[endpoint];
        // Endpoint deleted successfully
    }

    function existsClient(bytes32 endpoint) public onlyWhitelist view returns(int) {
        for (uint i = 0; i < endpoints.length; i++) {
            if(endpoint == endpoints[i]){
                return int(i);
            }
        }
        return -1;
    }
}