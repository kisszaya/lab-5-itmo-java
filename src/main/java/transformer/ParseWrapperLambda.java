package transformer;

@FunctionalInterface
interface ParseWrapperLambda<T> {
    T execute(String field);
}
