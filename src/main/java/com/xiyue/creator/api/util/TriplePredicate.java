package com.xiyue.creator.api.util;

import java.util.Objects;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface TriplePredicate<T, U, K> {
    boolean test(T t, U u, K k);

    default TriplePredicate<T, U, K> and(TriplePredicate<? super T, ? super U, ? super K> other) {
        Objects.requireNonNull(other);
        return (T t, U u, K k) -> test(t, u, k) && other.test(t, u, k);
    }

    default TriplePredicate<T, U, K> negate() {
        return (T t, U u, K k) -> !test(t, u, k);
    }

    default TriplePredicate<T, U, K> or(TriplePredicate<? super T, ? super U, ? super K> other) {
        Objects.requireNonNull(other);
        return (T t, U u, K k) -> test(t, u, k) || other.test(t, u, k);
    }
}
