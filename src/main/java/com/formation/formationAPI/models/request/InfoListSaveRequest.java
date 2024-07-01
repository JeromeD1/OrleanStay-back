package com.formation.formationAPI.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InfoListSaveRequest {
    @NotNull
    private List<InfoSaveRequest> infos;
}
