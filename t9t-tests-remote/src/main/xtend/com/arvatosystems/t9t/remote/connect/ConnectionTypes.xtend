package com.arvatosystems.t9t.remote.connect

/** Enumeration of possible connection types supported by this project.
 * Not all connections support all types.
 * The t9t core server supports JSON, BONAPARTE, COMPACT_BONAPARTE.
 * The REST connection supports XML, JSON.
 */
public enum ConnectionTypes {
    XML, JSON, BONAPARTE, COMPACT_BONAPARTE
}
