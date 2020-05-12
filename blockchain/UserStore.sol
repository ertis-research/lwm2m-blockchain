pragma solidity >=0.4.22 <0.7.0;

contract UserStore {

  struct UserData {
    bytes32 username;
    bytes32 email;
    bytes32 password;
    uint256 role;
  }

  mapping (uint256 => UserData) users;
  uint256 cont;
  uint256 n_users; //number of registered users

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
    n_users = 0;
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

  function addUser(
    bytes32 username, bytes32 email, bytes32 password, uint256 role
  ) public onlyOwner returns(uint256) {

    cont++;
    users[cont].username = username;
    users[cont].email = email;
    users[cont].password = password;
    users[cont].role = role;
    n_users++; // New user registered

    return(cont); // Returns new user id
  }

  function getAllUsers() public onlyOwner view returns(
    bytes32[] memory, bytes32[] memory, bytes32[] memory, uint256[] memory
  ) {
    bytes32[] memory res_email = new bytes32[](cont);
    bytes32[] memory res_username = new bytes32[](cont);
    bytes32[] memory res_password = new bytes32[](cont);
    uint256[] memory res_role = new uint256[](cont);

    for (uint256 i = 1; i <= cont; i++) {
      res_email[i-1] = users[i].email;
      res_username[i-1] = users[i].username;
      res_password[i-1] = users[i].password;
      res_role[i-1] = users[i].role;
    }

    return(res_email, res_username, res_password, res_role);
  }

  function updateUser(uint256 id) public onlyOwner {
    //TO-DO
  }

  function deleteUser(uint256 id) public onlyOwner {
    //TO-DO
  }

  function validateLogin(
    bytes32 wildcard, bytes32 password //wildcard field can be email or username
  ) public onlyWhitelist view returns(bool) { 
    //TO-DO

    return false;
  }
}