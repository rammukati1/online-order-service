package com.online.order.scheduler;

import com.online.order.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PendingOrderPromoter {
    private final OrderService orderService;


    public PendingOrderPromoter(OrderService orderService) {
        this.orderService = orderService;
    }


    // run every 5 minutes (cron could be used or fixedDelay as below). We'll use fixedRateString to read from properties.
    @Scheduled(fixedRateString = "${app.scheduler.pending-to-processing-interval-seconds:300}000")
    @Transactional
    public void promotePending() {
        int changed = orderService.bulkPromotePendingToProcessing();
// a real system would log; keep simple here
        System.out.println("Promoted " + changed + " orders from PENDING -> PROCESSING");
    }
}