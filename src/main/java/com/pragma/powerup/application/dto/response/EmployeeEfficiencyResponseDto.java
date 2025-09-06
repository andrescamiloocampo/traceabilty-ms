package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class EmployeeEfficiencyResponseDto {
    private Long chefId;
    private String totalTime;
    private BigInteger totalMinutes;
}
