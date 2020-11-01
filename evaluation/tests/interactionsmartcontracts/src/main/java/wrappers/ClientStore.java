package wrappers;

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
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
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
public class ClientStore extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610e98380380610e988339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b82525081516020918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040908152600280546001600160a01b031916339081179091556000908152600360205220805460ff191660011790555050825115915061015190505760005b815181101561014f5760016003600084848151811061011557fe5b6020908102919091018101516001600160a01b03168252810191909152604001600020805460ff19169115159190911790556001016100fa565b505b50610d37806101616000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80639b19251a1161005b5780639b19251a14610161578063e4d7a3d41461019b578063ec6263c014610395578063f754ab76146103c357610088565b806326845bfa1461008d578063292b5ffa146100ac57806368dde26e146100ed5780638da5cb5b1461013d575b600080fd5b6100aa600480360360208110156100a357600080fd5b50356103f2565b005b6100aa600480360360e08110156100c257600080fd5b5080359060208101359060408101359060608101359060808101359060a08101359060c00135610548565b61010a6004803603602081101561010357600080fd5b5035610660565b604080519687526020870195909552858501939093526060850191909152608084015260a0830152519081900360c00190f35b61014561072b565b604080516001600160a01b039092168252519081900360200190f35b6101876004803603602081101561017757600080fd5b50356001600160a01b031661073a565b604080519115158252519081900360200190f35b6101a361074f565b604051808060200180602001806020018060200180602001806020018060200188810388528f818151815260200191508051906020019060200280838360005b838110156101fb5781810151838201526020016101e3565b5050505090500188810387528e818151815260200191508051906020019060200280838360005b8381101561023a578181015183820152602001610222565b5050505090500188810386528d818151815260200191508051906020019060200280838360005b83811015610279578181015183820152602001610261565b5050505090500188810385528c818151815260200191508051906020019060200280838360005b838110156102b85781810151838201526020016102a0565b5050505090500188810384528b818151815260200191508051906020019060200280838360005b838110156102f75781810151838201526020016102df565b5050505090500188810383528a818151815260200191508051906020019060200280838360005b8381101561033657818101518382015260200161031e565b50505050905001888103825289818151815260200191508051906020019060200280838360005b8381101561037557818101518382015260200161035d565b505050509050019e50505050505050505050505050505060405180910390f35b6100aa600480360360408110156103ab57600080fd5b506001600160a01b0381351690602001351515610baa565b6103e0600480360360208110156103d957600080fd5b5035610c1e565b60408051918252519081900360200190f35b3360009081526003602052604090205460ff166104405760405162461bcd60e51b815260040180806020018281038252602a815260200180610cb4602a913960400191505060405180910390fd5b600061044b82610c1e565b90508060001914156104a4576040805162461bcd60e51b815260206004820152601760248201527f456e64706f696e7420646f6573206e6f74206578697374000000000000000000604482015290519081900360640190fd5b805b600154600019018110156104f057600181600101815481106104c457fe5b9060005260206000200154600182815481106104dc57fe5b6000918252602090912001556001016104a6565b5060018054806104fc57fe5b6000828152602080822083016000199081018390559092019092559281529182905250604081208181556001810182905560028101829055600381018290556004810182905560050155565b3360009081526003602052604090205460ff166105965760405162461bcd60e51b815260040180806020018281038252602a815260200180610cb4602a913960400191505060405180910390fd5b60006105a188610c1e565b905080600019146105f9576040805162461bcd60e51b815260206004820152601e60248201527f436c69656e7420456e64706f696e7420616c7265616479206578697374730000604482015290519081900360640190fd5b5060008781526020819052604081209687556001808801969096556002870194909455600386019290925560048501556005909301929092558054808201825591527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60155565b336000908152600360205260408120548190819081908190819060ff166106b85760405162461bcd60e51b815260040180806020018281038252602a815260200180610cb4602a913960400191505060405180910390fd5b60006106c388610c1e565b90508060001914156106ea5760008060008060008096509650965096509650965050610722565b505050600085815260208190526040902080546001820154600283015460038401546004850154600590950154939850919650945092505b91939550919395565b6002546001600160a01b031681565b60036020526000908152604090205460ff1681565b3360009081526003602052604090205460609081908190819081908190819060ff166107ac5760405162461bcd60e51b815260040180806020018281038252602a815260200180610cb4602a913960400191505060405180910390fd5b60015460609067ffffffffffffffff811180156107c857600080fd5b506040519080825280602002602001820160405280156107f2578160200160208202803683370190505b5060015490915060609067ffffffffffffffff8111801561081257600080fd5b5060405190808252806020026020018201604052801561083c578160200160208202803683370190505b5060015490915060609067ffffffffffffffff8111801561085c57600080fd5b50604051908082528060200260200182016040528015610886578160200160208202803683370190505b5060015490915060609067ffffffffffffffff811180156108a657600080fd5b506040519080825280602002602001820160405280156108d0578160200160208202803683370190505b5060015490915060609067ffffffffffffffff811180156108f057600080fd5b5060405190808252806020026020018201604052801561091a578160200160208202803683370190505b5060015490915060609067ffffffffffffffff8111801561093a57600080fd5b50604051908082528060200260200182016040528015610964578160200160208202803683370190505b5060015490915060609067ffffffffffffffff8111801561098457600080fd5b506040519080825280602002602001820160405280156109ae578160200160208202803683370190505b50905060005b600154811015610b9357600181815481106109cb57fe5b90600052602060002001548882815181106109e257fe5b602002602001018181525050600080600183815481106109fe57fe5b9060005260206000200154815260200190815260200160002060000154878281518110610a2757fe5b60200260200101818152505060008060018381548110610a4357fe5b9060005260206000200154815260200190815260200160002060010154868281518110610a6c57fe5b60200260200101818152505060008060018381548110610a8857fe5b9060005260206000200154815260200190815260200160002060020154858281518110610ab157fe5b60200260200101818152505060008060018381548110610acd57fe5b9060005260206000200154815260200190815260200160002060030154848281518110610af657fe5b60200260200101818152505060008060018381548110610b1257fe5b9060005260206000200154815260200190815260200160002060040154838281518110610b3b57fe5b60200260200101818152505060008060018381548110610b5757fe5b9060005260206000200154815260200190815260200160002060050154828281518110610b8057fe5b60209081029190910101526001016109b4565b50959d949c50929a50909850965094509092509050565b6002546001600160a01b03163314610bf35760405162461bcd60e51b8152600401808060200182810382526024815260200180610cde6024913960400191505060405180910390fd5b6001600160a01b03919091166000908152600360205260409020805460ff1916911515919091179055565b3360009081526003602052604081205460ff16610c6c5760405162461bcd60e51b815260040180806020018281038252602a815260200180610cb4602a913960400191505060405180910390fd5b60005b600154811015610ca75760018181548110610c8657fe5b9060005260206000200154831415610c9f579050610cae565b600101610c6f565b5060001990505b91905056fe4f6e6c792077686974656c6973746564206163636f756e74732063616e206578656375746520746869734f6e6c7920636f6e7472616374206f776e65722063616e20657865637574652074686973a2646970667358221220e2c5cb85be70d6331912800e02e2cd6c562e91a0290c9013ccb317ba74b1b68364736f6c634300060a0033";

    public static final String FUNC_ADDCLIENT = "addClient";

    public static final String FUNC_EXISTSCLIENT = "existsClient";

    public static final String FUNC_GETALLCLIENTS = "getAllClients";

    public static final String FUNC_GETCLIENT = "getClient";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVECLIENT = "removeClient";

    public static final String FUNC_SETPERMISSION = "setPermission";

    public static final String FUNC_WHITELIST = "whitelist";

    @Deprecated
    protected ClientStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ClientStore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ClientStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ClientStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addClient(byte[] endpoint, byte[] url_bs, byte[] id_bs, byte[] key_bs, byte[] url_s, byte[] id_s, byte[] key_s) {
        final Function function = new Function(
                FUNC_ADDCLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(endpoint), 
                new org.web3j.abi.datatypes.generated.Bytes32(url_bs), 
                new org.web3j.abi.datatypes.generated.Bytes32(id_bs), 
                new org.web3j.abi.datatypes.generated.Bytes32(key_bs), 
                new org.web3j.abi.datatypes.generated.Bytes32(url_s), 
                new org.web3j.abi.datatypes.generated.Bytes32(id_s), 
                new org.web3j.abi.datatypes.generated.Bytes32(key_s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> existsClient(byte[] endpoint) {
        final Function function = new Function(FUNC_EXISTSCLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(endpoint)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>>> getAllClients() {
        final Function function = new Function(FUNC_GETALLCLIENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>>>(function,
                new Callable<Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>>>() {
                    @Override
                    public Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>>(
                                convertToNative((List<Bytes32>) results.get(0).getValue()), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Bytes32>) results.get(2).getValue()), 
                                convertToNative((List<Bytes32>) results.get(3).getValue()), 
                                convertToNative((List<Bytes32>) results.get(4).getValue()), 
                                convertToNative((List<Bytes32>) results.get(5).getValue()), 
                                convertToNative((List<Bytes32>) results.get(6).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]>> getClient(byte[] endpoint) {
        final Function function = new Function(FUNC_GETCLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(endpoint)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]>>(function,
                new Callable<Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]>>() {
                    @Override
                    public Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (byte[]) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue(), 
                                (byte[]) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeClient(byte[] endpoint) {
        final Function function = new Function(
                FUNC_REMOVECLIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(endpoint)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPermission(String addr, Boolean permission) {
        final Function function = new Function(
                FUNC_SETPERMISSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr), 
                new org.web3j.abi.datatypes.Bool(permission)), 
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
    public static ClientStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ClientStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ClientStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ClientStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ClientStore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ClientStore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ClientStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ClientStore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ClientStore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(ClientStore.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ClientStore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(ClientStore.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ClientStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(ClientStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ClientStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(ClientStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
