package io.simplersoftware.learning.auth.util;

import java.security.KeyPair;

public interface ServerKeyStore {
    KeyPair getKeyPairFromKeyStore();
}
