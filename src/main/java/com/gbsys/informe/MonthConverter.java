package com.gbsys.informe;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.util.StringConverter;

/**
 *
 * @author herman
 */
public class MonthConverter extends StringConverter<Month> {

    private static final Map<String, Month> FROM_STRING = new HashMap<>();
    private static final Map<Month, String> TO_STRING = new HashMap<>();

    public MonthConverter() {
        for (Month month : Month.values()) {
            String name = month.getDisplayName(TextStyle.FULL, Locale.getDefault());
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            FROM_STRING.put(name, month);
            TO_STRING.put(month, name);
        }
    }

    @Override
    public String toString(Month value) {
        if (null == value) {
            return null;
        }
        return TO_STRING.get(value);
    }

    @Override
    public Month fromString(String value) {
        if (null == value) {
            return null;
        }
        return FROM_STRING.get(value);
    }

}
