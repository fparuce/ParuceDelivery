package com.fparucelabs.parucedelivery.delivery.tracking.domain.service;

import com.fparucelabs.parucedelivery.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {
    DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
