## Smart Contracts
Smart Contracts used in this project are stored here. All of them are coded using [Solidity](https://solidity-es.readthedocs.io/es/latest/). In order to compile Smart Contracts and generate Java wrappers is neccesary to install the Solidity compiler (*solc*) and a Java library for working with Smart Contracts (*web3j*).

Following next steps you can generate Java wrapper code of a Smart Contract.

### Install web3j CLI
On windows (powershell)
```powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://raw.githubusercontent.com/web3j/web3j-installer/master/installer.ps1'))
```

On Linux or Mac
```bash
curl -L https://get.web3j.io | sh
```

After instalation we can verify everything went successfull with this command:
```bash
web3j version
```

### Install solc (Solidity compiler)
```bash
npm install -g solc
```
After installation verify using this command:
```bash
solcjs --version
```

### Compile smart contract
```bash
solcjs Contract.sol --bin --abi --optimize -o compiled/
```

### Generate wrapper code using web3j's CLI
```bash
web3j solidity generate -a Contract.abi -b Contract.bin -c Contract -o output/ -p ertis.uma
```