package kodilla.exchange.app;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ExchangeRateResponse {
    private String base;
    private String date;
    private Map<String, Double> rates;
    private List<String> listOfCurrencies;

    public ExchangeRateResponse() {
        listOfCurrencies = new ArrayList<>();
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        try {
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();

            Request request = new Request.Builder()
                    .url("https://api.exchangeratesapi.io/latest?base=" + base)
                    .build();
            ResponseBody responseBody = client.newCall(request).execute().body();
            ExchangeRateResponse exchangeRateResponse = gson.fromJson(responseBody.string(), ExchangeRateResponse.class);
            setRates(exchangeRateResponse.getRates());
            client.connectionPool().evictAll();
            this.base = base;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public void viewCurrenciesList() {
        if (listOfCurrencies.isEmpty()) {
                System.out.println("Creating new list");
                for (Map.Entry<String, Double> entry : rates.entrySet()) {
                    listOfCurrencies.add(entry.getKey());
                }
                listOfCurrencies.add(getBase());
        }
        System.out.println("Reading from list");
        for (String currency : listOfCurrencies)
            System.out.println(currency);
    }
}
