package io.curity.identityserver.plugin.dap.credential.pbkdf2;

import se.curity.identityserver.sdk.datasource.CredentialDataAccessProviderFactory;
import se.curity.identityserver.sdk.datasource.CredentialManagementDataAccessProvider;

public class Pbkdf2CredentialDataAccessProviderFactory implements CredentialDataAccessProviderFactory {
    private final Pbkdf2DapConfiguration configuration;

    public Pbkdf2CredentialDataAccessProviderFactory(Pbkdf2DapConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public CredentialManagementDataAccessProvider create() {
        return new Pbkdf2CredentialDataAccessProvider(configuration);
    }
}
