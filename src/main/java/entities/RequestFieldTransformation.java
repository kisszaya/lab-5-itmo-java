package entities;

@FunctionalInterface
interface RequestFieldTransformation<T> {
    T transform(String value);
}
