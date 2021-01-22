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

import se.curity.identityserver.sdk.config.Configuration;
import se.curity.identityserver.sdk.service.AttributeRepository;
import se.curity.identityserver.sdk.config.annotation.DefaultString;
import se.curity.identityserver.sdk.config.annotation.DefaultInteger;
import se.curity.identityserver.sdk.config.annotation.Description;


public interface Pbkdf2DapConfiguration extends Configuration
{
    @Description("Data source which provides the credential attributes")
    AttributeRepository getAttributes();

    @Description("Name of the attribute containing the password hash")
    @DefaultString("password")
    String getPasswordAttribute();

    @Description("Number of iterations")
    @DefaultInteger(10000)
    int getIterations();

    @Description("Key length in bits")
    @DefaultInteger(512)
    int getKeyLength();
}
