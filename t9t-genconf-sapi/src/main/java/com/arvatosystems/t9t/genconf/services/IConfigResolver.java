package com.arvatosystems.t9t.genconf.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.genconf.ConfigDTO;
import com.arvatosystems.t9t.genconf.ConfigRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IConfigResolver extends RefResolver<ConfigRef, ConfigDTO, FullTrackingWithVersion> {}
