package com.amsen.par.cewlrency.api.fixerio.response;

import android.util.JsonReader;

import com.amsen.par.cewlrency.api.fixerio.model.CurrencyRateRespModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyExchangeRatesResponse {
    public String base;
    public Date date;
    public List<CurrencyRateRespModel> rates;

    public CurrencyExchangeRatesResponse() {
        rates = new ArrayList<>();
    }

    public List<CurrencyRateRespModel> getRates() {
        return rates;
    }

    public void addRate(CurrencyRateRespModel rate) {
        this.rates.add(rate);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }


    public static CurrencyExchangeRatesResponse parse(InputStream stream) {
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        JsonReader jsonReader = new JsonReader(inputStreamReader);

        CurrencyExchangeRatesResponse response = new CurrencyExchangeRatesResponse();

        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();

                if (name.equals("base")) {
                    response.setBase(jsonReader.nextString());
                } else if (name.equals("date")) {
                    response.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.UK).parse(jsonReader.nextString()));
                } else if (name.equals("rates")) {
                    jsonReader.beginObject();

                    while (jsonReader.hasNext()) {
                        String id = jsonReader.nextName();

                        double rate = jsonReader.nextDouble();
                        response.addRate(new CurrencyRateRespModel(id, rate));
                    }

                    jsonReader.endObject();
                } else jsonReader.skipValue();
            }

            jsonReader.endObject();
            jsonReader.close(); //not in finally cuz we're don't in this test :)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jsonReader.close();
                inputStreamReader.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }
}
