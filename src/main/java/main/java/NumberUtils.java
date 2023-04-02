package main.java;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;

public class NumberUtils {

    public static Double fractionToDouble(String fraction)
            throws ParseException {
        Double d = null;
        if (fraction != null) {
            if (fraction.contains("/")) {
                String[] numbers = fraction.split("/");
                if (numbers.length == 2) {
                    BigDecimal d1 = BigDecimal.valueOf(Double.valueOf(numbers[0]));
                    BigDecimal d2 = BigDecimal.valueOf(Double.valueOf(numbers[1]));
                    BigDecimal response = d1.divide(d2, MathContext.DECIMAL128);
                    d = response.doubleValue();
                }
            }
            else {
                d = Double.valueOf(fraction);
            }
        }
        if (d == null) {
            throw new ParseException(fraction, 0);
        }
        return d;
    }
}
