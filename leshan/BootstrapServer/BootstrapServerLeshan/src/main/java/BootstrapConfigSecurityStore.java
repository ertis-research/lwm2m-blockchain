import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.SecurityMode;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerSecurity;
import org.eclipse.leshan.server.bootstrap.BootstrapConfigStore;
import org.eclipse.leshan.server.bootstrap.EditableBootstrapConfigStore;
import org.eclipse.leshan.server.security.BootstrapSecurityStore;
import org.eclipse.leshan.server.security.SecurityInfo;

/**
 * A {@link BootstrapSecurityStore} which uses a {@link BootstrapConfigStore} to device credentials.
 * <p>
 * Generally, a {@link BootstrapSecurityStore} contains information about how a device should connect to server (using
 * psk, rpk or x509 ). And a {@link BootstrapConfigStore} contains configuration which should be written on device
 * during bootstrap session.
 * <p>
 * This {@link BootstrapSecurityStore} will search in {@link BootstrapConfigStore} to find security info.
 * <p>
 * {@link #getByIdentity(String)} could have some performance issue and strange behavior if you have several config with
 * same identity. (which is possible if you are using same identity for several bootstrap server)
 * <p>
 * <strong>WARNING : This store is not production ready.</strong>
 */
public class BootstrapConfigSecurityStore implements BootstrapSecurityStore {

    private final EditableBootstrapConfigStore bootstrapConfigStore;

    /**
     * @param bootstrapConfigStore in which we search to find how device should authenticate itself.
     */
    public BootstrapConfigSecurityStore(EditableBootstrapConfigStore bootstrapConfigStore) {
        this.bootstrapConfigStore = bootstrapConfigStore;
    }

    @Override
    public SecurityInfo getByIdentity(String identity) {
        byte[] identityBytes = identity.getBytes(StandardCharsets.UTF_8);

        // Acceptable for a demo but iterate over all the store to get PSK is not really acceptable for a production
        // server.
        // This could behave strangely if there is several config using same identity but with different bootstrap
        // server.
        for (Map.Entry<String, BootstrapConfig> e : bootstrapConfigStore.getAll().entrySet()) {
            BootstrapConfig bsConfig = e.getValue();
            if (bsConfig.security != null) {
                for (Map.Entry<Integer, BootstrapConfig.ServerSecurity> ec : bsConfig.security.entrySet()) {
                    ServerSecurity serverSecurity = ec.getValue();
                    if (serverSecurity.bootstrapServer && serverSecurity.securityMode == SecurityMode.PSK
                            && Arrays.equals(serverSecurity.publicKeyOrId, identityBytes)) {
                        return SecurityInfo.newPreSharedKeyInfo(e.getKey(), identity, serverSecurity.secretKey);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<SecurityInfo> getAllByEndpoint(String endpoint) {

        BootstrapConfig bsConfig = bootstrapConfigStore.get(endpoint, null, null);

        if (bsConfig == null || bsConfig.security == null)
            return null;

        for (Map.Entry<Integer, BootstrapConfig.ServerSecurity> bsEntry : bsConfig.security.entrySet()) {
            ServerSecurity value = bsEntry.getValue();

            // Extract PSK security info
            if (value.bootstrapServer && value.securityMode == SecurityMode.PSK) {
                SecurityInfo securityInfo = SecurityInfo.newPreSharedKeyInfo(endpoint,
                        new String(value.publicKeyOrId, StandardCharsets.UTF_8), value.secretKey);
                return Arrays.asList(securityInfo);
            }
        }
        return null;

    }
}
