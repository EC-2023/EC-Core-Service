package src.service.Statistic.Dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PayLoadStatisticData {
    int label;
    double value;
    Date date;

    public PayLoadStatisticData(int label, double value) {
        this.label = label;
        this.value = value;
    }

    public PayLoadStatisticData(Date date, double value) {
        this.date = date;
        this.value = value;
    }
}
