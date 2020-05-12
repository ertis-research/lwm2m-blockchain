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
public class AnomalyStore extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610e41380380610e418339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b82525081516020918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040908152600280546001600160a01b031916339081179091556000908152600360205220805460ff191660011790555050825115915061015190505760005b815181101561014f5760016003600084848151811061011557fe5b6020908102919091018101516001600160a01b03168252810191909152604001600020805460ff19169115159190911790556001016100fa565b505b506000600155610cdb806101666000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80639a453f511161005b5780639a453f51146102205780639b19251a14610366578063e80b7383146103a0578063ec6263c0146103a857610088565b806322fa22e91461008d5780632574d801146100d057806336bb10f5146101cb5780638da5cb5b146101fc575b600080fd5b6100aa600480360360208110156100a357600080fd5b50356103d6565b604080519485526020850193909352838301919091526060830152519081900360800190f35b6100ed600480360360208110156100e657600080fd5b5035610456565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b8381101561013557818101518382015260200161011d565b50505050905001848103835286818151815260200191508051906020019060200280838360005b8381101561017457818101518382015260200161015c565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156101b357818101518382015260200161019b565b50505050905001965050505050505060405180910390f35b6101fa600480360360808110156101e157600080fd5b508035906020810135906040810135906060013561064b565b005b6102046106dd565b604080516001600160a01b039092168252519081900360200190f35b6102436004803603604081101561023657600080fd5b50803590602001356106ec565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019060200280838360005b8381101561028f578181015183820152602001610277565b50505050905001858103845288818151815260200191508051906020019060200280838360005b838110156102ce5781810151838201526020016102b6565b50505050905001858103835287818151815260200191508051906020019060200280838360005b8381101561030d5781810151838201526020016102f5565b50505050905001858103825286818151815260200191508051906020019060200280838360005b8381101561034c578181015183820152602001610334565b505050509050019850505050505050505060405180910390f35b61038c6004803603602081101561037c57600080fd5b50356001600160a01b0316610975565b604080519115158252519081900360200190f35b61024361098a565b6101fa600480360360408110156103be57600080fd5b506001600160a01b0381351690602001351515610be3565b3360009081526003602052604081205481908190819060ff1661042a5760405162461bcd60e51b815260040180806020018281038252602a815260200180610c58602a913960400191505060405180910390fd5b505050600091825250602081905260409020805460018201546002830154600390930154919390929190565b336000908152600360205260409020546060908190819060ff166104ab5760405162461bcd60e51b815260040180806020018281038252602a815260200180610c58602a913960400191505060405180910390fd5b606060015467ffffffffffffffff811180156104c657600080fd5b506040519080825280602002602001820160405280156104f0578160200160208202803683370190505b509050606060015467ffffffffffffffff8111801561050e57600080fd5b50604051908082528060200260200182016040528015610538578160200160208202803683370190505b509050606060015467ffffffffffffffff8111801561055657600080fd5b50604051908082528060200260200182016040528015610580578160200160208202803683370190505b509050600060015b600154811161063d576000818152602081905260409020600101548914156106355760008181526020819052604090205485518690849081106105c757fe5b602002602001018181525050600080828152602001908152602001600020600201548483815181106105f557fe5b6020026020010181815250506000808281526020019081526020016000206003015483838151811061062357fe5b60209081029190910101526001909101905b600101610588565b509297919650945092505050565b3360009081526003602052604090205460ff166106995760405162461bcd60e51b815260040180806020018281038252602a815260200180610c58602a913960400191505060405180910390fd5b600180548101808255600090815260208190526040808220969096558154815285812082019490945580548452848420600201929092559054825291902060030155565b6002546001600160a01b031681565b3360009081526003602052604090205460609081908190819060ff166107435760405162461bcd60e51b815260040180806020018281038252602a815260200180610c58602a913960400191505060405180910390fd5b606060015467ffffffffffffffff8111801561075e57600080fd5b50604051908082528060200260200182016040528015610788578160200160208202803683370190505b509050606060015467ffffffffffffffff811180156107a657600080fd5b506040519080825280602002602001820160405280156107d0578160200160208202803683370190505b509050606060015467ffffffffffffffff811180156107ee57600080fd5b50604051908082528060200260200182016040528015610818578160200160208202803683370190505b509050606060015467ffffffffffffffff8111801561083657600080fd5b50604051908082528060200260200182016040528015610860578160200160208202803683370190505b509050600060015b6001548111610963576000818152602081905260409020548c1180159061089d57506000818152602081905260409020548b10155b1561095b5760008181526020819052604090205486518790849081106108bf57fe5b602002602001018181525050600080828152602001908152602001600020600101548583815181106108ed57fe5b6020026020010181815250506000808281526020019081526020016000206002015484838151811061091b57fe5b6020026020010181815250506000808281526020019081526020016000206003015483838151811061094957fe5b60209081029190910101526001909101905b600101610868565b50939a92995090975095509350505050565b60036020526000908152604090205460ff1681565b3360009081526003602052604090205460609081908190819060ff166109e15760405162461bcd60e51b815260040180806020018281038252602a815260200180610c58602a913960400191505060405180910390fd5b606060015467ffffffffffffffff811180156109fc57600080fd5b50604051908082528060200260200182016040528015610a26578160200160208202803683370190505b509050606060015467ffffffffffffffff81118015610a4457600080fd5b50604051908082528060200260200182016040528015610a6e578160200160208202803683370190505b509050606060015467ffffffffffffffff81118015610a8c57600080fd5b50604051908082528060200260200182016040528015610ab6578160200160208202803683370190505b509050606060015467ffffffffffffffff81118015610ad457600080fd5b50604051908082528060200260200182016040528015610afe578160200160208202803683370190505b50905060015b6001548111610bd457600081815260208190526040902054855186906000198401908110610b2e57fe5b60200260200101818152505060008082815260200190815260200160002060010154846001830381518110610b5f57fe5b60200260200101818152505060008082815260200190815260200160002060020154836001830381518110610b9057fe5b60200260200101818152505060008082815260200190815260200160002060030154826001830381518110610bc157fe5b6020908102919091010152600101610b04565b50929791965094509092509050565b6002546001600160a01b03163314610c2c5760405162461bcd60e51b8152600401808060200182810382526024815260200180610c826024913960400191505060405180910390fd5b6001600160a01b03919091166000908152600360205260409020805460ff191691151591909117905556fe4f6e6c792077686974656c6973746564206163636f756e74732063616e206578656375746520746869734f6e6c7920636f6e7472616374206f776e65722063616e20657865637574652074686973a264697066735822122089ee5a51eaa4efa3a6df3e6909b005b3412fef520f8c186d1efc9c31167492cd64736f6c63430006070033";

    public static final String FUNC_ADDANOMALY = "addAnomaly";

    public static final String FUNC_GETALLANOMALIES = "getAllAnomalies";

    public static final String FUNC_GETANOMALIESBETWEENDATES = "getAnomaliesBetweenDates";

    public static final String FUNC_GETANOMALIESBYENDPOINT = "getAnomaliesByEndpoint";

    public static final String FUNC_GETANOMALY = "getAnomaly";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETPERMISSION = "setPermission";

    public static final String FUNC_WHITELIST = "whitelist";

    @Deprecated
    protected AnomalyStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AnomalyStore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AnomalyStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AnomalyStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addAnomaly(BigInteger ts, byte[] endpoint, BigInteger emergency, BigInteger encoded_data) {
        final Function function = new Function(
                FUNC_ADDANOMALY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ts), 
                new org.web3j.abi.datatypes.generated.Bytes32(endpoint), 
                new org.web3j.abi.datatypes.generated.Uint256(emergency), 
                new org.web3j.abi.datatypes.generated.Uint256(encoded_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>> getAllAnomalies() {
        final Function function = new Function(FUNC_GETALLANOMALIES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>> getAnomaliesBetweenDates(BigInteger ts_start, BigInteger ts_end) {
        final Function function = new Function(FUNC_GETANOMALIESBETWEENDATES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ts_start), 
                new org.web3j.abi.datatypes.generated.Uint256(ts_end)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()), 
                                convertToNative((List<Uint256>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>> getAnomaliesByEndpoint(byte[] endpoint) {
        final Function function = new Function(FUNC_GETANOMALIESBYENDPOINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(endpoint)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<BigInteger, byte[], BigInteger, BigInteger>> getAnomaly(BigInteger id) {
        final Function function = new Function(FUNC_GETANOMALY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, byte[], BigInteger, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, byte[], BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, byte[], BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, byte[], BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
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

    public RemoteFunctionCall<Boolean> whitelist(String param0) {
        final Function function = new Function(FUNC_WHITELIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static AnomalyStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AnomalyStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AnomalyStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AnomalyStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AnomalyStore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AnomalyStore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AnomalyStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AnomalyStore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AnomalyStore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AnomalyStore.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AnomalyStore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AnomalyStore.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AnomalyStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AnomalyStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AnomalyStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(AnomalyStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
