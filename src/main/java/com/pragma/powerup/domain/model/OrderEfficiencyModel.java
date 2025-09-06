package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEfficiencyModel {
    private Long id;
    private Long customerId;
    private Long chefId;
    private String totalTime;
    private BigInteger totalMinutes;
}
