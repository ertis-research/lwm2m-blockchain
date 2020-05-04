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
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
public class BootstrapStore extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610a8d380380610a8d8339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b82525081516020918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040908152600280546001600160a01b031916339081179091556000908152600360205220805460ff191660011790555050825115915061015190505760005b815181101561014f5760016003600084848151811061011557fe5b6020908102919091018101516001600160a01b03168252810191909152604001600020805460ff19169115159190911790556001016100fa565b505b5061092c806101616000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80638da5cb5b116100665780638da5cb5b146101b55780639b19251a146101d9578063e4d7a3d414610213578063ec6263c01461021b578063f754ab761461024957610093565b806326845bfa14610098578063292b5ffa146100b757806333efbd50146100f857806368dde26e14610165575b600080fd5b6100b5600480360360208110156100ae57600080fd5b5035610278565b005b6100b5600480360360e08110156100cd57600080fd5b5080359060208101359060408101359060608101359060808101359060a08101359060c001356103ce565b6101156004803603602081101561010e57600080fd5b50356104e6565b60408051602080825283518183015283519192839290830191858101910280838360005b83811015610151578181015183820152602001610139565b505050509050019250505060405180910390f35b6101826004803603602081101561017b57600080fd5b503561060b565b604080519687526020870195909552858501939093526060850191909152608084015260a0830152519081900360c00190f35b6101bd6106d6565b604080516001600160a01b039092168252519081900360200190f35b6101ff600480360360208110156101ef57600080fd5b50356001600160a01b03166106e5565b604080519115158252519081900360200190f35b6101156106fa565b6100b56004803603604081101561023157600080fd5b506001600160a01b03813516906020013515156107a1565b6102666004803603602081101561025f57600080fd5b5035610815565b60408051918252519081900360200190f35b3360009081526003602052604090205460ff166102c65760405162461bcd60e51b815260040180806020018281038252602a8152602001806108a9602a913960400191505060405180910390fd5b60006102d182610815565b905080600019141561032a576040805162461bcd60e51b815260206004820152601760248201527f456e64706f696e7420646f6573206e6f74206578697374000000000000000000604482015290519081900360640190fd5b805b60015460001901811015610376576001816001018154811061034a57fe5b90600052602060002001546001828154811061036257fe5b60009182526020909120015560010161032c565b50600180548061038257fe5b6000828152602080822083016000199081018390559092019092559281529182905250604081208181556001810182905560028101829055600381018290556004810182905560050155565b3360009081526003602052604090205460ff1661041c5760405162461bcd60e51b815260040180806020018281038252602a8152602001806108a9602a913960400191505060405180910390fd5b600061042788610815565b9050806000191461047f576040805162461bcd60e51b815260206004820152601e60248201527f436c69656e7420456e64706f696e7420616c7265616479206578697374730000604482015290519081900360640190fd5b5060008781526020819052604081209687556001808801969096556002870194909455600386019290925560048501556005909301929092558054808201825591527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60155565b3360009081526003602052604090205460609060ff166105375760405162461bcd60e51b815260040180806020018281038252602a8152602001806108a9602a913960400191505060405180910390fd5b60015460609067ffffffffffffffff8111801561055357600080fd5b5060405190808252806020026020018201604052801561057d578160200160208202803683370190505b5090506000805b60015481101561060057846000806001848154811061059f57fe5b906000526020600020015481526020019081526020016000206003015414156105f857600181815481106105cf57fe5b90600052602060002001548383815181106105e657fe5b60209081029190910101526001909101905b600101610584565b50909150505b919050565b336000908152600360205260408120548190819081908190819060ff166106635760405162461bcd60e51b815260040180806020018281038252602a8152602001806108a9602a913960400191505060405180910390fd5b600061066e88610815565b905080600019141561069557600080600080600080965096509650965096509650506106cd565b505050600085815260208190526040902080546001820154600283015460038401546004850154600590950154939850919650945092505b91939550919395565b6002546001600160a01b031681565b60036020526000908152604090205460ff1681565b3360009081526003602052604090205460609060ff1661074b5760405162461bcd60e51b815260040180806020018281038252602a8152602001806108a9602a913960400191505060405180910390fd5b600180548060200260200160405190810160405280929190818152602001828054801561079757602002820191906000526020600020905b815481526020019060010190808311610783575b5050505050905090565b6002546001600160a01b031633146107ea5760405162461bcd60e51b81526004018080602001828103825260248152602001806108d36024913960400191505060405180910390fd5b6001600160a01b03919091166000908152600360205260409020805460ff1916911515919091179055565b3360009081526003602052604081205460ff166108635760405162461bcd60e51b815260040180806020018281038252602a8152602001806108a9602a913960400191505060405180910390fd5b60005b60015481101561089e576001818154811061087d57fe5b9060005260206000200154831415610896579050610606565b600101610866565b506000199291505056fe4f6e6c792077686974656c6973746564206163636f756e74732063616e206578656375746520746869734f6e6c7920636f6e7472616374206f776e65722063616e20657865637574652074686973a2646970667358221220c313beb4403c54415faef1e79e2395d5cff20d5ddc660eadd0683b9e74b5497664736f6c63430006060033";

    public static final String FUNC_ADDCLIENT = "addClient";

    public static final String FUNC_EXISTSCLIENT = "existsClient";

    public static final String FUNC_GETALLCLIENTS = "getAllClients";

    public static final String FUNC_GETCLIENT = "getClient";

    public static final String FUNC_GETCLIENTSBYSERVER = "getClientsByServer";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVECLIENT = "removeClient";

    public static final String FUNC_SETPERMISSION = "setPermission";

    public static final String FUNC_WHITELIST = "whitelist";

    @Deprecated
    protected BootstrapStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BootstrapStore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BootstrapStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BootstrapStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<List> getAllClients() {
        final Function function = new Function(FUNC_GETALLCLIENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
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

    public RemoteFunctionCall<List> getClientsByServer(byte[] url_s) {
        final Function function = new Function(FUNC_GETCLIENTSBYSERVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(url_s)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
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
    public static BootstrapStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BootstrapStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BootstrapStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BootstrapStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BootstrapStore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BootstrapStore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BootstrapStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BootstrapStore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BootstrapStore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(BootstrapStore.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<BootstrapStore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(BootstrapStore.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BootstrapStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(BootstrapStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BootstrapStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> addresses) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(addresses, org.web3j.abi.datatypes.Address.class))));
        return deployRemoteCall(BootstrapStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
