package com.arvatosystems.t9t.email.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.email.EmailDTO;
import com.arvatosystems.t9t.email.EmailRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IEmailResolver extends RefResolver<EmailRef, EmailDTO, FullTrackingWithVersion> {}
