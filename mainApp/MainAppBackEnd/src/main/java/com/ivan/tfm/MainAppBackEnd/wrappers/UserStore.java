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
import org.web3j.tuples.generated.Tuple3;
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
public class UserStore extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b7f380380610b7f8339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b82525081516020918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040908152600280546001600160a01b031916339081179091556000908152600360205220805460ff191660011790555050825115915061015190505760005b815181101561014f5760016003600084848151811061011557fe5b6020908102919091018101516001600160a01b03168252810191909152604001600020805460ff19169115159190911790556001016100fa565b505b50610a1e806101616000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80639b19251a1161005b5780639b19251a14610152578063e2842d791461018c578063ec6263c0146102b7578063ee8cc162146102e557610088565b8063620ab5dd1461008d5780636521b7cb146100ce57806377ad84b1146100fd5780638da5cb5b1461012e575b600080fd5b6100b0600480360360408110156100a357600080fd5b5080359060200135610314565b60408051938452602084019290925282820152519081900360600190f35b6100eb600480360360208110156100e457600080fd5b5035610446565b60408051918252519081900360200190f35b61012c6004803603608081101561011357600080fd5b508035906020810135906040810135906060013561050f565b005b610136610600565b604080516001600160a01b039092168252519081900360200190f35b6101786004803603602081101561016857600080fd5b50356001600160a01b031661060f565b604080519115158252519081900360200190f35b610194610624565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019060200280838360005b838110156101e05781810151838201526020016101c8565b50505050905001858103845288818151815260200191508051906020019060200280838360005b8381101561021f578181015183820152602001610207565b50505050905001858103835287818151815260200191508051906020019060200280838360005b8381101561025e578181015183820152602001610246565b50505050905001858103825286818151815260200191508051906020019060200280838360005b8381101561029d578181015183820152602001610285565b505050509050019850505050505050505060405180910390f35b61012c600480360360408110156102cd57600080fd5b506001600160a01b0381351690602001351515610865565b61012c600480360360808110156102fb57600080fd5b50803590602081013590604081013590606001356108d9565b336000908152600360205260408120548190819060ff166103665760405162461bcd60e51b815260040180806020018281038252602a81526020018061099b602a913960400191505060405180910390fd5b600061037186610446565b905080600019141580156103b05750846000806001848154811061039157fe5b9060005260206000200154815260200190815260200160002060010154145b1561043357600181815481106103c257fe5b9060005260206000200154600080600184815481106103dd57fe5b90600052602060002001548152602001908152602001600020600001546000806001858154811061040a57fe5b90600052602060002001548152602001908152602001600020600201549350935093505061043f565b50600092508291508190505b9250925092565b6002546000906001600160a01b031633146104925760405162461bcd60e51b81526004018080602001828103825260248152602001806109c56024913960400191505060405180910390fd5b60005b6001548110156105035782600182815481106104ad57fe5b906000526020600020015414806104ef575082600080600184815481106104d057fe5b9060005260206000200154815260200190815260200160002060000154145b156104fb57905061050a565b600101610495565b5060001990505b919050565b6002546001600160a01b031633146105585760405162461bcd60e51b81526004018080602001828103825260248152602001806109c56024913960400191505060405180910390fd5b600061056385610446565b905080600019146105b1576040805162461bcd60e51b81526020600482015260136024820152725573657220616c72656164792065786973747360681b604482015290519081900360640190fd5b5060008481526020819052604081209384556001808501939093556002909301558054808201825591527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60155565b6002546001600160a01b031681565b60036020526000908152604090205460ff1681565b6002546060908190819081906001600160a01b031633146106765760405162461bcd60e51b81526004018080602001828103825260248152602001806109c56024913960400191505060405180910390fd5b60015460408051828152602080840282010190915260609180156106a4578160200160208202803683370190505b50905060606001805490506040519080825280602002602001820160405280156106d8578160200160208202803683370190505b509050606060018054905060405190808252806020026020018201604052801561070c578160200160208202803683370190505b5090506060600180549050604051908082528060200260200182016040528015610740578160200160208202803683370190505b50905060005b600154811015610856576001818154811061075d57fe5b906000526020600020015485828151811061077457fe5b6020026020010181815250506000806001838154811061079057fe5b90600052602060002001548152602001908152602001600020600001548482815181106107b957fe5b602002602001018181525050600080600183815481106107d557fe5b90600052602060002001548152602001908152602001600020600101548382815181106107fe57fe5b6020026020010181815250506000806001838154811061081a57fe5b906000526020600020015481526020019081526020016000206002015482828151811061084357fe5b6020908102919091010152600101610746565b50929791965094509092509050565b6002546001600160a01b031633146108ae5760405162461bcd60e51b81526004018080602001828103825260248152602001806109c56024913960400191505060405180910390fd5b6001600160a01b03919091166000908152600360205260409020805460ff1916911515919091179055565b6002546001600160a01b031633146109225760405162461bcd60e51b81526004018080602001828103825260248152602001806109c56024913960400191505060405180910390fd5b600061092d85610446565b905080600019141561097c576040805162461bcd60e51b8152602060048201526013602482015272155cd95c88191bd95cc81b9bdd08195e1a5cdd606a1b604482015290519081900360640190fd5b5060009384526020849052604090932091825560018201556002015556fe4f6e6c792077686974656c6973746564206163636f756e74732063616e206578656375746520746869734f6e6c7920636f6e7472616374206f776e65722063616e20657865637574652074686973a26469706673582212209895a9d0b66c0641ccafd4c9ed98edea28737891cdff11faf20cf3ff6a9d687964736f6c63430006040033";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_EXISTSUSER = "existsUser";

    public static final String FUNC_GETALLUSERS = "getAllUsers";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETPERMISSION = "setPermission";

    public static final String FUNC_UPDATEUSER = "updateUser";

    public static final String FUNC_VALIDATELOGIN = "validateLogin";

    public static final String FUNC_WHITELIST = "whitelist";

    @Deprecated
    protected UserStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserStore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addUser(byte[] username, byte[] email, byte[] password, BigInteger role) {
        final Function function = new Function(
                FUNC_ADDUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username), 
                new org.web3j.abi.datatypes.generated.Bytes32(email), 
                new org.web3j.abi.datatypes.generated.Bytes32(password), 
                new org.web3j.abi.datatypes.generated.Uint256(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> existsUser(byte[] wildcard) {
        final Function function = new Function(FUNC_EXISTSUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(wildcard)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>>> getAllUsers() {
        final Function function = new Function(FUNC_GETALLUSERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>>>(function,
                new Callable<Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>>>() {
                    @Override
                    public Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>>(
                                convertToNative((List<Bytes32>) results.get(0).getValue()), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Bytes32>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()));
                    }
                });
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

    public RemoteFunctionCall<TransactionReceipt> updateUser(byte[] username, byte[] email, byte[] password, BigInteger role) {
        final Function function = new Function(
                FUNC_UPDATEUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(username), 
                new org.web3j.abi.datatypes.generated.Bytes32(email), 
                new org.web3j.abi.datatypes.generated.Bytes32(password), 
                new org.web3j.abi.datatypes.generated.Uint256(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<byte[], byte[], BigInteger>> validateLogin(byte[] wildcard, byte[] password) {
        final Function function = new Function(FUNC_VALIDATELOGIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(wildcard), 
                new org.web3j.abi.datatypes.generated.Bytes32(password)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<byte[], byte[], BigInteger>>(function,
                new Callable<Tuple3<byte[], byte[], BigInteger>>() {
                    @Override
                    public Tuple3<byte[], byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<byte[], byte[], BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> whitelist(String param0) {
        final Function function = new Function(FUNC_WHITELIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static UserStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserStore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UserStore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UserStore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserStore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(UserStore.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<UserStore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(UserStore.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<UserStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(UserStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<UserStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(UserStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
