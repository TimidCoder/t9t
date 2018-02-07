package com.arvatosystems.t9t.server.services;

import com.arvatosystems.t9t.base.moduleCfg.ModuleConfigDTO;

/** Implementations of this interface should be singletons to ensure one separate instance per module.
 * They are located in the persistence modules. */
public interface IModuleConfigResolver<T extends ModuleConfigDTO> {
    /** Read a module configuration from DB, and return, in the following preference:
     * <ul>
     * <li>the value for the specific tenant
     * <li>the value for the global tenant
     * <li>a hardcoded default, as provided by getDefaultModuleConfiguration
     * </ul>
     *
     * This method will never return null. The result will be cached for 60 seconds, limiting the number of required database lookups.
     * @return
     */
    T getModuleConfiguration();

    /** Returns a sensible default for the module configuration.
     * It should be possible to use a system without any configuration made, and it should fall back to meaningful defaults then,
     * which are provided by this method.
     * If a module chooses not to override this method, null will be returned by getModuleConfiguration() if no database configuration has been created.
     * @return
     */
    T getDefaultModuleConfiguration();

    /** Updates module configuration with a new one. Writes to the DB and updates the local cache.
     * @return
     */
    void updateModuleConfiguration(T newCfg);
}
