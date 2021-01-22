/*
 *  Copyright 2021 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.curity.identityserver.plugin.dap.credential.pbkdf2;


import io.curity.identityserver.plugin.dap.credential.pbkdf2.Pbkdf2DapConfiguration;
import se.curity.identityserver.sdk.datasource.CredentialDataAccessProvider;
import se.curity.identityserver.sdk.plugin.descriptor.DataAccessProviderPluginDescriptor;

@SuppressWarnings("unused")
public class Pbkdf2DataAccessPluginDescriptor implements DataAccessProviderPluginDescriptor
{
    @Override
    public String getPluginImplementationType()
    {
        return "pbkdf2-credential";
    }

    @Override
    public Class getConfigurationType()
    {
        return Pbkdf2DapConfiguration.class;
    }

    @Override
    public Class<? extends CredentialDataAccessProvider> getCredentialDataAccessProvider()
    {
        return Pbkdf2CredentialDataAccessProvider.class;
    }


}