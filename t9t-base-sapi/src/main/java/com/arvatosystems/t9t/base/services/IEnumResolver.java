package com.arvatosystems.t9t.base.services;

public interface IEnumResolver {
    /** Returns an integer for a NonTokenizableEnum, the token String for a TokenizableEnum (or null if the token was "").
     * Throws a RuntimeException if the enumPqon is not known or that enum does not have an instance of the given name.
     * @param enumPqon
     * @param instanceName
     * @return
     */
    Object getTokenByPqonAndInstance(String enumPqon, String instanceName);

    Object getTokenBySetPqonAndInstance(String enumsetPqon, String instanceName);

    String getTokenByXEnumPqonAndInstance(String xenumPqon, String instanceName);

    String getTokenByXEnumSetPqonAndInstance(String xenumsetPqon, String instanceName);
}
