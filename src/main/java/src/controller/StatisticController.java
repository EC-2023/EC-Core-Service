package src.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import src.config.annotation.ApiPrefixController;
import src.service.Statistic.Dtos.PayLoadStatisticData;
import src.service.Statistic.Dtos.PayLoadTotalStatistic;
import src.service.Statistic.IStatisticService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
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
    public  CompletableFuture<List<PayLoadStatisticData>> getStaticRevenue(@RequestParam() int option,
                                                                           @RequestParam("date") long dateStr) {
        Instant instant = Instant.ofEpochMilli(dateStr);
        Date date = Date.from(instant);
        return statisticService.getStaticRevenue(option, date);

    }
//    @GetMapping("/get-static-revenue")
//    public  CompletableFuture<List<PayLoadStatisticData>> getStaticRevenue(@RequestParam() int option,
//                                                                           @RequestParam() Date date) {
//        return statisticService.getStaticRevenue(option, date);
//
//    }
}
