package com.arvatosystems.t9t.uiprefsv3.services;

import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO;

public interface ILeanGridConfigRead {
    LeanGridConfigDTO readLeanGridConfig(String gridId, Integer variant, Long userRef);
}
