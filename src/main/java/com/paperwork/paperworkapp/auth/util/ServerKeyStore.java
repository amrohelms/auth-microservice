package com.paperwork.paperworkapp.auth.util;

import java.security.KeyPair;

public interface ServerKeyStore {
    KeyPair getKeyPairFromKeyStore();
}
