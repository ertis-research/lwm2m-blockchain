pragma solidity ^0.6.1;

contract BootstrapStore{    

    struct BootstrapConfig{
        bytes32 url_bs;
        bytes32 id_bs;
        bytes32 key_bs;
        bytes32 url_s;
        bytes32 id_s;
        string key_s;
    }

    mapping (bytes32 => BootstrapConfig) clients;
    bytes32[] endpoints;

    function Add(bytes32 endpoint, bytes32 url_bs, bytes32 id_bs, bytes32 key_bs, bytes32 url_s, bytes32 id_s, string memory key_s) public {
        clients[endpoint].url_bs = url_bs;
        clients[endpoint].id_bs = id_bs;
        clients[endpoint].key_bs = key_bs;
        clients[endpoint].url_s = url_s;
        clients[endpoint].id_s = id_s;
        clients[endpoint].key_s = key_s;
        bool exist = ExistEndpoint(endpoint);
        
        if(exist==false){
            endpoints.push(endpoint);
        }
    } 
    
    function Remove(bytes32 endpoint) public {
        bool exist = ExistEndpoint(endpoint);
        
        if(exist==true){
            uint pos=0;
            while(endpoint != endpoints[pos]){
                pos++;
            }
            
            for (uint i = pos; i<endpoints.length-1; i++){
                endpoints[i] = endpoints[i+1];
            }
            endpoints.pop();
            delete clients[endpoint];
        }
    }

    function Get(bytes32 endpoint) public view returns (bytes32, bytes32, bytes32, bytes32, bytes32, string memory){
        return (
            clients[endpoint].url_bs,
            clients[endpoint].id_bs,
            clients[endpoint].key_bs,
            clients[endpoint].url_s,
            clients[endpoint].id_s,
            clients[endpoint].key_s);
    }
    
    function GetEndpoints() public view returns(bytes32[] memory){
        return endpoints;
    }

    function RemoveAllEndpoints() public {
        delete endpoints;
    }

    function ExistEndpoint(bytes32 endpoint) private view returns (bool){
        bool exist = false;
        uint i = 0;
        while(exist==false && i<endpoints.length){
            if(endpoint == endpoints[i]){
                exist = true;
            }
            i++;
        }
        return exist;
    }
}