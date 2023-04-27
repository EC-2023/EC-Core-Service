package src.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.service.Statistic.Dtos.PayLoadStatisticData;
import src.service.Statistic.Dtos.PayLoadTotalStatistic;
import src.service.Statistic.Dtos.PayLoadTotalStore;
import src.service.Statistic.IStatisticService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/statistics")
public class StatisticController {
    private final IStatisticService statisticService;

    public StatisticController(IStatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/get-total")
    public CompletableFuture<PayLoadTotalStatistic> getTotalStatistic() {
        return statisticService.getTotalStatistic();
    }

    @GetMapping("/get-static-revenue")
    public CompletableFuture<List<PayLoadStatisticData>> getStaticRevenue(@RequestParam() int option,
                                                                          @RequestParam("date") long dateStr) {
        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticRevenue(option, date);

    }

    @Authenticate
    @GetMapping("/get-static-product")
    public CompletableFuture<List<PayLoadStatisticData>> getStaticProduct(@RequestParam() int option,
                                                                          @RequestParam("date") long dateStr) {
        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticRevenue(option, date);
    }
    @Authenticate
    @GetMapping("/get-static-order")
    public CompletableFuture<List<PayLoadStatisticData>> getStaticOrder(@RequestParam() int option,
                                                                          @RequestParam("date") long dateStr) {
        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticOrder(option, date);
    }

    @Authenticate
    @GetMapping("/get-total-statistic-store")
    public CompletableFuture<PayLoadTotalStore> getTotalProductAndRevenueStore() {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return statisticService.getTotalProductAndRevenueStore(userId);
    }

    @Authenticate
    @GetMapping("/get-statistic-order-store")
    public CompletableFuture<List<PayLoadStatisticData>> getStaticOrderByStore(@RequestParam() int option,
                                                                               @RequestParam("date") long dateStr) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));

        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticOrderByStore(option, date, userId);
    }


    @Authenticate
    @GetMapping("/get-statistic-product-store")
        public CompletableFuture<List<PayLoadStatisticData>> getStaticProductByStore(@RequestParam() int option,
                                                                                 @RequestParam("date") long dateStr) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticProductByStore(option, date, userId);
    }

    @Authenticate
    @GetMapping("/get-statistic-revenue-store")
    public CompletableFuture<List<PayLoadStatisticData>> getStaticRevenueByStore(@RequestParam() int option,
                                                                                 @RequestParam("date") long dateStr) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticRevenueByStore(option, date, userId);
    }

}
