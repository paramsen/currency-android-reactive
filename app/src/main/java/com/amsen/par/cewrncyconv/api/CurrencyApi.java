package com.amsen.par.cewrncyconv.api;

import android.util.JsonReader;

import com.amsen.par.cewrncyconv.base.util.Http;
import com.amsen.par.cewrncyconv.model.Currency;

import java.io.IOException;
import java.io.Reader;
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

    private List<Currency> parse(Reader in) {
        List<Currency> currencies = new ArrayList<>();
        JsonReader jsonReader = new JsonReader(in);

        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();

                if (name.equals("rates")) {
                    jsonReader.beginObject();

                    while (jsonReader.hasNext()) {
                        String id = jsonReader.nextName();

                        if(id.matches("CAD|EUR|GBP|JPY|USD")) {
                            double rate = jsonReader.nextDouble();
                            currencies.add(new Currency(id, rate));
                        } else jsonReader.skipValue();
                    }

                    jsonReader.endObject();
                } else jsonReader.skipValue();
            }

            jsonReader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return currencies;
    }
}
