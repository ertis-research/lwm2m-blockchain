pragma solidity >=0.4.22 <0.7.0;

contract UserStore {

  struct UserData {
    bytes32 email;
    bytes32 password;
    uint256 role; //1 - admin, 2 - advanced, 3 - basic
  }

  mapping (bytes32 => UserData) users; //username is the key
  bytes32[] usernames;

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

  function addUser(
    bytes32 username, bytes32 email, bytes32 password, uint256 role
  ) public onlyOwner {

    int exists = existsUser(username);
    require(exists == -1, "User already exists");

    users[username].email = email;
    users[username].password = password;
    users[username].role = role;
    usernames.push(username); // New user registered
  }

  function getAllUsers() public onlyOwner view returns(
    bytes32[] memory, bytes32[] memory, bytes32[] memory, uint256[] memory
  ) {
    bytes32[] memory res_username = new bytes32[](usernames.length);
    bytes32[] memory res_email = new bytes32[](usernames.length);
    bytes32[] memory res_password = new bytes32[](usernames.length);
    uint256[] memory res_role = new uint256[](usernames.length);

    for (uint i = 0; i < usernames.length; i++) {
      res_username[i] = usernames[i];
      res_email[i] = users[usernames[i]].email;
      res_password[i] = users[usernames[i]].password;
      res_role[i] = users[usernames[i]].role;
    }

    return(res_username, res_email, res_password, res_role);
  }

  function updateUser(
    bytes32 username, bytes32 email, bytes32 password, uint256 role
  ) public onlyOwner {

    int exists = existsUser(username);
    require(exists != -1, "User does not exist");

    users[username].email = email;
    users[username].password = password;
    users[username].role = role;
  }

  //wildcard field can be email or username. The response is the user's profile (username, email, role)
  function validateLogin(
    bytes32 wildcard, bytes32 password
  ) public onlyWhitelist view returns(bytes32, bytes32, uint256) {

    int pos = existsUser(wildcard);

    if(pos != -1 && users[usernames[uint(pos)]].password == password) {
      return (
        usernames[uint(pos)],
        users[usernames[uint(pos)]].email,
        users[usernames[uint(pos)]].role
      );
    }
    return ("", "", 0);
  }

  function existsUser(bytes32 wildcard) public onlyOwner view returns(int) {
    for(uint i = 0; i < usernames.length; i++) {
      if(usernames[i] == wildcard || users[usernames[i]].email == wildcard) {
        return int(i);
      }
    }
    return -1;
  }
}