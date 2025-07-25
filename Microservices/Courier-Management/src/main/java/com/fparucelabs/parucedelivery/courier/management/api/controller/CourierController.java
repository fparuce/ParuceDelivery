package com.fparucelabs.parucedelivery.courier.management.api.controller;

import com.fparucelabs.parucedelivery.courier.management.api.model.CourierInput;
import com.fparucelabs.parucedelivery.courier.management.api.model.CourierPayoutCalculationInput;
import com.fparucelabs.parucedelivery.courier.management.api.model.CourierPayoutResultModel;
import com.fparucelabs.parucedelivery.courier.management.domain.model.Courier;
import com.fparucelabs.parucedelivery.courier.management.domain.repository.CourierRepository;
import com.fparucelabs.parucedelivery.courier.management.domain.service.CourierPayoutService;
import com.fparucelabs.parucedelivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
@Slf4j
public class CourierController {
    private final CourierRegistrationService courierRegistrationService;
    private final CourierPayoutService courierPayoutService;
    private final CourierRepository courierRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput input ){
        return courierRegistrationService.create(input);
    }

    @PutMapping("/{courierId}")
    public Courier update(@PathVariable UUID courierId,
                          @Valid @RequestBody CourierInput input) {
        return courierRegistrationService.update(courierId, input);
    }

    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @SneakyThrows
    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input) {
        log.info("Calculating");
        if (Math.random() < 0.1) {
            throw new RuntimeException();
        }

        int millis = new Random().nextInt(250);
        Thread.sleep(millis);

        BigDecimal payoutFee = courierPayoutService.calculate(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }
}
