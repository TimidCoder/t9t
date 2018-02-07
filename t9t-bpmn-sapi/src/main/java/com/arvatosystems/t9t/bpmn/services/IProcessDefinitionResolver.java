package com.arvatosystems.t9t.bpmn.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IProcessDefinitionResolver extends RefResolver<ProcessDefinitionRef, ProcessDefinitionDTO, FullTrackingWithVersion> {}
