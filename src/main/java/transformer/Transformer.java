package transformer;

import validation.IsNullHelper;

import java.time.LocalDateTime;

public class Transformer {
    static public Double toDouble(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, Double::parseDouble);
    }

    static public <T extends Enum<T>> T toEnum(Class<T> enumClass, String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, val -> Enum.valueOf(enumClass, val));
    }

    static public Integer toInteger(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, Integer::parseInt);
    }

    static public Long toLong(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, Long::parseLong);
    }

    static public String toString(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, String::toString);
    }

    static public LocalDateTime toLocalDateTime(String value) throws NumberFormatException, NullPointerException {
        return parseWrapper(value, LocalDateTime::parse);
    }


    private static <T> T parseWrapper(String value, ParseWrapperLambda<T> lambda) {
        if (IsNullHelper.isNull(value)) {
            return null;
        }
        return lambda.execute(value);
    }
}
