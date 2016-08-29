package com.amsen.par.cewrncyconv.api;

import android.util.JsonReader;
import android.util.Pair;

import com.amsen.par.cewrncyconv.base.util.CurrencyUtil;
import com.amsen.par.cewrncyconv.base.util.Http;
import com.amsen.par.cewrncyconv.model.Currency;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * API mapping for Fixer.io service
 *
 * @author Pär Amsen 2016
 */
public class CurrencyApi {
    public List<Currency> getCurrencies() {
        return parse(Http.get("http://api.fixer.io/latest?base=AUD"));
    }

    private List<Currency> parse(Pair<Reader, HttpURLConnection> in) {
        List<Currency> currencies = new ArrayList<>();

        try {
            JsonReader jsonReader = new JsonReader(in.first);
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();

                if (name.equals("rates")) {
                    jsonReader.beginObject();

                    while (jsonReader.hasNext()) {
                        String id = jsonReader.nextName();

                        if (id.matches("CAD|EUR|GBP|JPY|USD")) {
                            double rate = jsonReader.nextDouble();
                            currencies.add(new Currency(id, rate, CurrencyUtil.getLocale(id)));
                        } else jsonReader.skipValue();
                    }

                    jsonReader.endObject();
                } else jsonReader.skipValue();
            }

            jsonReader.endObject();
            jsonReader.close(); //not in finally cuz we're don't in this test :)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.second.disconnect();
        }

        return currencies;
    }
}
