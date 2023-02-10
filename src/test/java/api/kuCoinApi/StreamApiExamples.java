package api.kuCoinApi;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class StreamApiExamples {

    public List<TickerData> getTickers() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.kucoin.com/api/v1/market/allTickers")
                .then().log().body()
                .extract().jsonPath().getList("data.ticker", TickerData.class);
    }

    @Test
    public void checkCrypto() {
        List<TickerData> usdTickers = getTickers().stream().filter(x -> x.getSymbol().endsWith("USDT")).toList();
        Assertions.assertTrue(usdTickers.stream().allMatch(x -> x.getSymbol().endsWith("USDT")));
    }

    @Test
    public void sortHightToLow() {
        List<TickerData> highToLow = getTickers().stream().filter(x -> x.getSymbol().endsWith("USDT")).sorted((o1, o2) -> o2.getChangeRate().compareTo(o1.getChangeRate())).toList();
        List<TickerData> top10 = highToLow.stream().limit(10).toList();
        Assertions.assertEquals(top10.get(0).getSymbol(), "GALAX3L-USDT");
    }

    @Test
    public void sortLowToHigh() {
        List<TickerData> lowToHight = getTickers().stream().filter(x -> x.getSymbol().endsWith("USDT"))
                .sorted(new TickerComparatorLow()).limit(10).toList();

    }

    @Test
    public void firstMap() {
        Map<String, Float> usd = new HashMap<>();
        List<String> lowerCases = getTickers().stream().map(x -> x.getSymbol().toLowerCase()).toList();
        getTickers().forEach(x -> usd.put(x.getSymbol(), Float.parseFloat(x.getChangeRate())));
    }

    @Test
    public void secondMap() {
        List<TickerShort> shortList = new ArrayList<>();
        getTickers().forEach(x -> shortList.add(new TickerShort(x.getSymbol(), Float.parseFloat(x.getChangeRate()))));
    }
}
