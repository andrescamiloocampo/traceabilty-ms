package com.pragma.powerup.infrastructure.out.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "plaza-ms",url = "${plaza.service.url}")
public interface PlazaFeignClient {
    @GetMapping("/restaurants/ownership")
    boolean getOwnership(@RequestParam int id, @RequestParam int ownerId);
}
