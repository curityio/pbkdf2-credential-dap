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

import se.curity.identityserver.sdk.Nullable;
import se.curity.identityserver.sdk.ThreadSafe;
import se.curity.identityserver.sdk.attribute.AccountAttributes;
import se.curity.identityserver.sdk.attribute.AttributeTableView;
import se.curity.identityserver.sdk.attribute.AuthenticationAttributes;
import se.curity.identityserver.sdk.attribute.ContextAttributes;
import se.curity.identityserver.sdk.datasource.CredentialDataAccessProvider;
import se.curity.identityserver.sdk.service.AttributeRepository;
import se.curity.identityserver.sdk.attribute.Attributes;
import se.curity.identityserver.sdk.attribute.SubjectAttributes;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

import java.util.Map;

public class Pbkdf2CredentialDataAccessProvider implements CredentialDataAccessProvider, ThreadSafe {
    private static final Logger _logger = LoggerFactory.getLogger(Pbkdf2CredentialDataAccessProvider.class);
    private final AttributeRepository _attributeRepository;
    private final String _passwordAttribute;
    private final int _iterations;
    private final int _keyLength;

    public Pbkdf2CredentialDataAccessProvider(Pbkdf2DapConfiguration configuration) {
        _attributeRepository = configuration.getAttributes();
        _passwordAttribute = configuration.getPasswordAttribute();
        _iterations = configuration.getIterations();
        _keyLength = configuration.getKeyLength();
    }

    @Override
    public void updatePassword(AccountAttributes account) {
        throw new UnsupportedOperationException("Password update not supported");
    }

    @Override
    public @Nullable AuthenticationAttributes verifyPassword(String userName, String password) {
        AttributeTableView attributes = _attributeRepository.getAttributes(userName);
        if (attributes != null && attributes.getRow(0) != null) {
            Map<String, ?> row = attributes.getRow(0);
            @Nullable String storedHash = (String) row.get(_passwordAttribute);
            if (storedHash != null) {
                String[] result = storedHash.split(":");
                if (result.length != 2) {
                    throw new IllegalArgumentException("Stored hash not in correct format");
                }
                String salt = result[0];
                String pass = result[1];
                String hash = hashPassword(password, Base64.getDecoder().decode(salt), _iterations, _keyLength);
                if (hash.equalsIgnoreCase(pass)) {
                    row.remove(_passwordAttribute);
                    Attributes accountAttributes = Attributes.fromMap(row);
                    return AuthenticationAttributes.of(SubjectAttributes.of(userName, accountAttributes),
                            ContextAttributes.empty());
                }
            } else {
                _logger.info("No stored hash found for " + userName);
            }
        } else {
            _logger.info("No attributes found for " + userName);
        }

        return null;
    }

    @Override
    public boolean customQueryVerifiesPassword() {
        return true;
    }

    private static String hashPassword(final String password, final byte[] saltBytes, final int iterations, final int keyLength) {

        char[] passwordChars = password.toCharArray();

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return Base64.getEncoder().encodeToString(res);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
