package gusev.max.tinkoff_homework.third;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by v on 07/11/2017.
 */

public class BigDecimalDeserializer implements JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        BigDecimal number = null;

        String moneyAmount = json.getAsJsonObject().get("money_amount").getAsString();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setParseBigDecimal(true);
        decimalFormat.setDecimalFormatSymbols(symbols);

        try {
            number = (BigDecimal) decimalFormat.parse(moneyAmount);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return number;
    }
}
