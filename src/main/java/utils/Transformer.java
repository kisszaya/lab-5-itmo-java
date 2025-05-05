package utils;

import validation.IsNullHelper;

import java.time.LocalDateTime;

public class Transformer {
    public static Double toDouble(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, Double::parseDouble);
    }

    public static <T extends Enum<T>> T toEnum(Class<T> enumClass, String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, val -> Enum.valueOf(enumClass, val));
    }

    public static Integer toInteger(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, Integer::parseInt);
    }

    public static Long toLong(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, Long::parseLong);
    }

    public static String toString(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, String::toString);
    }

    public static LocalDateTime toLocalDateTime(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, LocalDateTime::parse);
    }

    @FunctionalInterface
    interface ParseWrapperLambda<T> {
        T execute(String field);
    }

    public static <T> T parseWrapper(String value, ParseWrapperLambda<T> lambda) {
        if (IsNullHelper.isNull(value)) {
            return null;
        }
        return lambda.execute(value);
    }
}
