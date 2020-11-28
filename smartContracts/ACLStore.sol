pragma solidity >=0.4.22 <0.7.0;

contract AclStore {

  struct Entry {
    bytes32 client_name;
    uint256 object_id;
    uint256 resource_id;
    uint256 permission;
  }

  struct UserData {
    Entry[] entries;
    bool exists;
  }

  mapping (bytes32 => UserData) users;

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

  function getUserPermission(
    bytes32 username, bytes32 client_name,
    uint256 object_id, uint256 resource_id
  ) public onlyWhitelist view returns(uint256) {
    // User still does not exist on mapping
    if (!users[username].exists) {
      return 0;
    }

    int pos = existsEntry(username, client_name,
      object_id, resource_id);
    
    if (pos != -1) {
      return users[username].entries[uint(pos)].permission;
    }
    return 0;
  }

  function getUserEntries(bytes32 username) public onlyOwner view returns(
    bytes32[] memory, uint256[] memory, uint256[] memory, uint256[] memory
  ) {
    // User still does not exist on mapping
    if (!users[username].exists) {
      return (new bytes32[](0), new uint256[](0), new uint256[](0), new uint256[](0));
    }

    uint cont = users[username].entries.length;

    bytes32[] memory res_cli = new bytes32[](cont);
    uint256[] memory res_obj = new uint256[](cont);
    uint256[] memory res_res = new uint256[](cont);
    uint256[] memory res_per = new uint256[](cont);

    for (uint i = 0; i < cont; i++) {
      res_cli[i] = users[username].entries[i].client_name;
      res_obj[i] = users[username].entries[i].object_id;
      res_res[i] = users[username].entries[i].resource_id;
      res_per[i] = users[username].entries[i].permission;
    }

    return (res_cli, res_obj, res_res, res_per);
  }

  function getUserEntriesByClient(bytes32 username, bytes32 client_name) public onlyOwner view returns(
    bytes32[] memory, uint256[] memory, uint256[] memory, uint256[] memory
  ) {
    // User still does not exist on mapping
    if (!users[username].exists) {
      return (new bytes32[](0), new uint256[](0), new uint256[](0), new uint256[](0));
    }

    uint cont = users[username].entries.length;

    bytes32[] memory res_cli = new bytes32[](cont);
    uint256[] memory res_obj = new uint256[](cont);
    uint256[] memory res_res = new uint256[](cont);
    uint256[] memory res_per = new uint256[](cont);
    uint res_cont = 0;

    for (uint i = 0; i < cont; i++) {
      if(users[username].entries[i].client_name == client_name) {
        res_cli[res_cont] = users[username].entries[i].client_name;
        res_obj[res_cont] = users[username].entries[i].object_id;
        res_res[res_cont] = users[username].entries[i].resource_id;
        res_per[res_cont] = users[username].entries[i].permission;
        res_cont++;
      }
    }

    return (res_cli, res_obj, res_res, res_per);
  }

  function updateEntry(
    bytes32 username, bytes32 client_name, uint256 object_id,
    uint256 resource_id, uint256 permission
  ) public onlyOwner {

    int pos = existsEntry(username, client_name,
      object_id, resource_id);

    if (pos != -1) { //ACL entry exists
      users[username].entries[uint(pos)].permission = permission;
    } else { //Entry does not exists
      users[username].exists = true;
      users[username].entries.push(Entry({
        client_name: client_name,
        object_id: object_id,
        resource_id: resource_id,
        permission: permission
      }));
    }
  }

  function existsEntry(
    bytes32 username, bytes32 client_name,
    uint256 object_id, uint256 resource_id
  ) public onlyWhitelist view returns(int) {
    if (users[username].exists) {
      for (uint i = 0; i < users[username].entries.length; i++) {
        if (
          users[username].entries[i].client_name == client_name &&
          users[username].entries[i].object_id == object_id &&
          users[username].entries[i].resource_id == resource_id
        ) {
          return int(i);
        }
      }
    }
    return -1;
  }
}