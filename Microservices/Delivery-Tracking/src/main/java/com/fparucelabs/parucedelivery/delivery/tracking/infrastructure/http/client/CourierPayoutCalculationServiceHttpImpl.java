package com.fparucelabs.parucedelivery.delivery.tracking.infrastructure.http.client;

import com.fparucelabs.parucedelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import com.fparucelabs.parucedelivery.delivery.tracking.infrastructure.exception.BadGatewayException;
import com.fparucelabs.parucedelivery.delivery.tracking.infrastructure.exception.GatewayTimeoutException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        try{
           return courierAPIClient.payoutCalculation(
                    new CourierPayoutCalculationInput(distanceInKm)
            ).getPayoutFee();
        } catch (ResourceAccessException e) {
            throw new GatewayTimeoutException(e);
        } catch (HttpServerErrorException | IllegalArgumentException | CallNotPermittedException e) {
            throw new BadGatewayException(e);
        }
    }
}
