package kodilla.exchange.app;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.util.Scanner;

class Menu {

    private Scanner scanner;
    private ExchangeRateResponse exchangeRateResponse;

    public Menu() throws Exception{
        scanner = new Scanner(System.in);
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url("https://api.exchangeratesapi.io/latest")
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        exchangeRateResponse = gson.fromJson(responseBody.string(),ExchangeRateResponse.class);
        client.connectionPool().evictAll();
    }

    private int getChoiceInt() {
        return scanner.nextInt();
    }

    private String getChoiceString() {
        return scanner.next();
    }

    private boolean end;

    public void run() {
        end = false;
        while (!end) {
            exchange();
            print();
            int choice = getChoiceInt();
            if (choice==1) setBase();
            else if (choice==2) setAmount();
            else if (choice==3) setExchange();
            else if (choice==0) end();
        }
    }

    private void print() {
        System.out.println("| 1. Base Currency | 2. Amount | 3. Exchange Currency | 0. Exit |");
    }

    private void exchange() {
        if (amount!=0 && exchangeCurrency!=null)
            System.out.println(amount+" "+exchangeRateResponse.getBase()+" = "+exchangeRate()+" "+exchangeCurrency);
    }

    private double exchangeRate() {
        return (double) Math.round(amount * exchangeRateResponse.getRates().get(exchangeCurrency)*100)/100;
    }

    private void setBase() {
        list();
        System.out.println("Choose one currency from the list");
        String base = getChoiceString().toUpperCase();
        exchangeRateResponse.setBase(base);
    }

    private double amount;
    private void setAmount() {
        System.out.println("Type in amount you want to exchange");
        amount = getChoiceInt();
    }

    private String exchangeCurrency;
    private void setExchange() {
        list();
        System.out.println("Choose one currency from the list");
        exchangeCurrency = getChoiceString().toUpperCase();

    }

    private void end() {
        System.out.println("Closing app");
        end = true;
    }

    private void list() {
        exchangeRateResponse.viewCurrenciesList();
    }
}
