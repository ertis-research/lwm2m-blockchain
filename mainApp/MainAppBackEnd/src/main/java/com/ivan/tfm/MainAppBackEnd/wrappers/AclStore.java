package com.ivan.tfm.MainAppBackEnd.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class AclStore extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610da7380380610da78339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b82525081516020918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040908152600180546001600160a01b0319163390811782556000908152600260205291909120805460ff191690911790555050825115915061015390505760005b81518110156101515760016002600084848151811061011757fe5b6020908102919091018101516001600160a01b03168252810191909152604001600020805460ff19169115159190911790556001016100fc565b505b50610c44806101636000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063bfcd7f9d1161005b578063bfcd7f9d1461026c578063c73b1694146102a3578063d15d0fd4146102d2578063ec6263c0146102f557610088565b8063281c0ecc1461008d578063858d2c47146101cd5780638da5cb5b1461020e5780639b19251a14610232575b600080fd5b6100aa600480360360208110156100a357600080fd5b5035610323565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019060200280838360005b838110156100f65781810151838201526020016100de565b50505050905001858103845288818151815260200191508051906020019060200280838360005b8381101561013557818101518382015260200161011d565b50505050905001858103835287818151815260200191508051906020019060200280838360005b8381101561017457818101518382015260200161015c565b50505050905001858103825286818151815260200191508051906020019060200280838360005b838110156101b357818101518382015260200161019b565b505050509050019850505050505050505060405180910390f35b6101fc600480360360808110156101e357600080fd5b50803590602081013590604081013590606001356105d2565b60408051918252519081900360200190f35b61021661069c565b604080516001600160a01b039092168252519081900360200190f35b6102586004803603602081101561024857600080fd5b50356001600160a01b03166106ab565b604080519115158252519081900360200190f35b6102a1600480360360a081101561028257600080fd5b50803590602081013590604081013590606081013590608001356106c0565b005b6101fc600480360360808110156102b957600080fd5b50803590602081013590604081013590606001356107ce565b6100aa600480360360408110156102e857600080fd5b5080359060200135610911565b6102a16004803603604081101561030b57600080fd5b506001600160a01b0381351690602001351515610b4c565b6001546060908190819081906001600160a01b031633146103755760405162461bcd60e51b8152600401808060200182810382526024815260200180610beb6024913960400191505060405180910390fd5b60008581526020819052604090206001015460ff166103bf5750506040805160008082526020820181815282840182815260608401928352608084019094529194509092506105cb565b6000858152602081815260409182902054825181815281830281019092019092526060908280156103fa578160200160208202803683370190505b509050606082604051908082528060200260200182016040528015610429578160200160208202803683370190505b509050606083604051908082528060200260200182016040528015610458578160200160208202803683370190505b509050606084604051908082528060200260200182016040528015610487578160200160208202803683370190505b50905060005b858110156105be5760008b81526020819052604090208054829081106104af57fe5b9060005260206000209060040201600001548582815181106104cd57fe5b6020026020010181815250506000808c815260200190815260200160002060000181815481106104f957fe5b90600052602060002090600402016001015484828151811061051757fe5b6020026020010181815250506000808c8152602001908152602001600020600001818154811061054357fe5b90600052602060002090600402016002015483828151811061056157fe5b6020026020010181815250506000808c8152602001908152602001600020600001818154811061058d57fe5b9060005260206000209060040201600301548282815181106105ab57fe5b602090810291909101015260010161048d565b5092975090955093509150505b9193509193565b3360009081526002602052604081205460ff166106205760405162461bcd60e51b815260040180806020018281038252602a815260200180610bc1602a913960400191505060405180910390fd5b60008581526020819052604090206001015460ff1661064157506000610694565b600061064f868686866107ce565b9050806000191461068e57600086815260208190526040902080548290811061067457fe5b906000526020600020906004020160030154915050610694565b60009150505b949350505050565b6001546001600160a01b031681565b60026020526000908152604090205460ff1681565b6001546001600160a01b031633146107095760405162461bcd60e51b8152600401808060200182810382526024815260200180610beb6024913960400191505060405180910390fd5b6000610717868686866107ce565b9050806000191461075957600086815260208190526040902080548391908390811061073f57fe5b9060005260206000209060040201600301819055506107c6565b6000868152602081815260408083206001818101805460ff19168217905582516080810184528a81528085018a8152938101898152606082018981528454808501865594885295909620905160049093020191825591519181019190915591516002830155516003909101555b505050505050565b3360009081526002602052604081205460ff1661081c5760405162461bcd60e51b815260040180806020018281038252602a815260200180610bc1602a913960400191505060405180910390fd5b60008581526020819052604090206001015460ff16156109055760005b60008681526020819052604090205481101561090357600086815260208190526040902080548691908390811061086c57fe5b9060005260206000209060040201600001541480156108b7575060008681526020819052604090208054859190839081106108a357fe5b906000526020600020906004020160010154145b80156108ef575060008681526020819052604090208054849190839081106108db57fe5b906000526020600020906004020160020154145b156108fb579050610694565b600101610839565b505b50600019949350505050565b6001546060908190819081906001600160a01b031633146109635760405162461bcd60e51b8152600401808060200182810382526024815260200180610beb6024913960400191505060405180910390fd5b60008681526020819052604090206001015460ff166109ad575050604080516000808252602082018181528284018281526060840192835260808401909452919450909250610b43565b60008681526020819052604081205490606090819081908190805b86811015610b355760008d815260208190526040902080548d9190839081106109ed57fe5b9060005260206000209060040201600001541415610b2d5760008d8152602081905260409020805482908110610a1f57fe5b906000526020600020906004020160000154868381518110610a3d57fe5b6020026020010181815250506000808e81526020019081526020016000206000018181548110610a6957fe5b906000526020600020906004020160010154858381518110610a8757fe5b6020026020010181815250506000808e81526020019081526020016000206000018181548110610ab357fe5b906000526020600020906004020160020154848381518110610ad157fe5b6020026020010181815250506000808e81526020019081526020016000206000018181548110610afd57fe5b906000526020600020906004020160030154838381518110610b1b57fe5b60209081029190910101526001909101905b6001016109c8565b509398509196509450925050505b92959194509250565b6001546001600160a01b03163314610b955760405162461bcd60e51b8152600401808060200182810382526024815260200180610beb6024913960400191505060405180910390fd5b6001600160a01b03919091166000908152600260205260409020805460ff191691151591909117905556fe4f6e6c792077686974656c6973746564206163636f756e74732063616e206578656375746520746869734f6e6c7920636f6e7472616374206f776e65722063616e20657865637574652074686973a2646970667358221220d01285d85d120dedbaa0cb9ed8eaa14e5cd22ec741cdae6aed39514b77cd559264736f6c63430006040033";

    public static final String FUNC_EXISTSENTRY = "existsEntry";

    public static final String FUNC_GETUSERENTRIES = "getUserEntries";

    public static final String FUNC_GETUSERENTRIESBYCLIENT = "getUserEntriesByClient";

    public static final String FUNC_GETUSERPERMISSION = "getUserPermission";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETPERMISSION = "setPermission";

    public static final String FUNC_UPDATEENTRY = "updateEntry";

    public static final String FUNC_WHITELIST = "whitelist";

    @Deprecated
    protected AclStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AclStore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AclStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AclStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> existsEntry(byte[] username, byte[] client_name, BigInteger object_id, BigInteger resource_id) {
        final Function function = new Function(FUNC_EXISTSENTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username), 
                new org.web3j.abi.datatypes.generated.Bytes32(client_name), 
                new org.web3j.abi.datatypes.generated.Uint256(object_id), 
                new org.web3j.abi.datatypes.generated.Uint256(resource_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>> getUserEntries(byte[] username) {
        final Function function = new Function(FUNC_GETUSERENTRIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Bytes32>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>> getUserEntriesByClient(byte[] username, byte[] client_name) {
        final Function function = new Function(FUNC_GETUSERENTRIESBYCLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username), 
                new org.web3j.abi.datatypes.generated.Bytes32(client_name)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Bytes32>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getUserPermission(byte[] username, byte[] client_name, BigInteger object_id, BigInteger resource_id) {
        final Function function = new Function(FUNC_GETUSERPERMISSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username), 
                new org.web3j.abi.datatypes.generated.Bytes32(client_name), 
                new org.web3j.abi.datatypes.generated.Uint256(object_id), 
                new org.web3j.abi.datatypes.generated.Uint256(resource_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setPermission(String addr, Boolean permission) {
        final Function function = new Function(
                FUNC_SETPERMISSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr), 
                new org.web3j.abi.datatypes.Bool(permission)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateEntry(byte[] username, byte[] client_name, BigInteger object_id, BigInteger resource_id, BigInteger permission) {
        final Function function = new Function(
                FUNC_UPDATEENTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username), 
                new org.web3j.abi.datatypes.generated.Bytes32(client_name), 
                new org.web3j.abi.datatypes.generated.Uint256(object_id), 
                new org.web3j.abi.datatypes.generated.Uint256(resource_id), 
                new org.web3j.abi.datatypes.generated.Uint256(permission)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> whitelist(String param0) {
        final Function function = new Function(FUNC_WHITELIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static AclStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AclStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AclStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AclStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AclStore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AclStore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AclStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AclStore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AclStore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AclStore.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AclStore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AclStore.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AclStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AclStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AclStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AclStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
