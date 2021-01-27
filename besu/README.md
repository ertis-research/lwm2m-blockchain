# Hyperledger Besu

## System requirements
* At least 4GB memory for Java Virtual Machine (JVM)
* For Mainnet and testnets, Besu requires a minimum of 8GB of RAM
* Syncing to the Ethereum Mainnet needs 750 GB of disk space minimum (fast synchronization)
* Full synchronization requires approximately 3TB

## How to install it?
There are 3 options to install Hyperledger Besu:
* Docker image (The best one in my opinion)
* Binaries
* Build from source (I do not recommend this option unless you want the latest version)

### Docker image
This command runs a Besu node in a Docker container.
```bash
docker run hyperledger/besu:latest
```

### Binaries
Installing from packaged binaries requires having **Java JDK** on your machine. [Here](https://hyperledger-org.bintray.com/besu-repo/) it is possible to dowload those Besu packaged binaries. Then, unpack downloaded files and change into `besu-<version>` folder. To confirm installation try to display Besu CLI version.
```bash
# Replace 'YOUR_VERSION' with a valid version
wget https://bintray.com/api/ui/download/hyperledger-org/besu-repo/besu-YOUR_VERSION.tar.gz
tar -xvf ./besu-YOUR_VERSION.tar.gz && cd besu-YOUR_VERSION
./bin/besu --version # This command should display your Besu version
```

## Starting Besu
By default Besu will be **connected to the Ethereum Mainnet**. In order to connect the node to another network, it is necessary to include the flag `--network` or use environment variable `BESU_NETWORK`. For instance, if we would like to connect our Besu node to Ropsten testnet we should add `--network=ropsten` or `BESU_NETWORK=ropsten`. Network name can be written in upper or lower case.
```bash
besu --network=ropsten
```
All possible values of the `network` option are listed [here](https://besu.hyperledger.org/en/stable/Reference/CLI/CLI-Syntax/#network)

When connecting to a network other than the network previously connected to, you must either delete the local block data (which is located in the directory where Besu files were extracted) or use the flag `--data-path` to indicate a different data directory.
```bash
besu --data-path=path/to/database
```

When working with more than one network, it is highly recommended to give a meaningful name to each database directory. For instance, the directory for the mainnet should be named `database_mainnet`, while for the Ropsten testnet the folder could be `database_ropsten`
```bash
besu --network=mainnnet --data-path=path/to/database_mainnet #OR
besu --network=ropsten --data-path=path/to/database_ropsten
```

## Acknowledgment
This guide has been developed summing up Hyperledger Besu docs.
https://besu.hyperledger.org/