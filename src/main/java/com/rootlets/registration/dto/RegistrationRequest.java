package com.rootlets.registration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrationRequest {

    private List<Long> studentIds;
    private List<Long> courseIds;
}
