package com.vianexo.roadwatch.incidentmitigation.domain.services;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CompleteMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.RegisterMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;

public interface MitigationActionCommandService {

    MitigationAction handle(RegisterMitigationActionCommand command);

    MitigationAction handle(CompleteMitigationActionCommand command);
}
