package com.arvatosystems.t9t.base.services;

public interface IRefGenerator {
    /**
     * Factor to multiply the value obtained from sequences with.
     */
    public static final long KEY_FACTOR = 10000L;

    /**
     * Offset to be added to scaled keys generated in the backup location.
     */
    public static final int OFFSET_BACKUP_LOCATION = 5000;

    /** Offsets of sequences which are unscaled (don't contain a RTTI). */
    public static final int OFFSET_UNSCALED_T9T           = 5000;
    public static final int OFFSET_UNSCALED_APPLICATION   = 6000;
    public static final int OFFSET_UNSCALED_CUSTOMIZATION = 7000;

    /**
     * Returns a valid technical Id which is guaranteed to be unique for all keys obtained through this method.
     *
     * @param rttiOffset
     *            An offset for run time type information
     * @return The new generated key value
     */
    public abstract long generateRef(int rttiOffset);

    /**
     * Retrieves the run time type information from a generated key.
     *
     * @param id
     *            The key to extract the RTTI from.
     * @return the RTTI
     */
    default public int getRtti(long id) {
        int rttiPlusLocation = (int) (id % KEY_FACTOR);
        return rttiPlusLocation >= OFFSET_BACKUP_LOCATION ? rttiPlusLocation - OFFSET_BACKUP_LOCATION : rttiPlusLocation;
    }
    /**
     * Returns a valid technical Id which is only scaled by the location offset and does not contain the RTTI. Therefore, Refs returned by this method will
     * overlap. These should be used if counters should be small. A uniform internal caching of 10 is used.
     *
     * The rttiOffset should be in range
     *
     * 500x for refs required by the fortytwo application server
     *
     * 600x for refs required by the main application
     *
     * 700x for refs required by customizations
     *
     * @param rttiOffset
     *            An offset for run time type information
     * @return The new generated key value
     */
    public abstract long generateUnscaledRef(int rttiOffset);

}
