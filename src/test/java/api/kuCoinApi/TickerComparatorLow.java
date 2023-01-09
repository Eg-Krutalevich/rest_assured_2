package api.kuCoinApi;

import java.util.Comparator;

public class TickerComparatorLow implements Comparator<TickerData> {

    //приведение Float к int всех полей, у которых есть запятая
    @Override
    public int compare(TickerData o1, TickerData o2) {
        int result = Float.compare(Float.parseFloat(o1.getChangeRate()), Float.parseFloat(o2.getChangeRate()));
        return result;
    }
}
