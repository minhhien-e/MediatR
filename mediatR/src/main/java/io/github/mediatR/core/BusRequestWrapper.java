package io.github.mediatR.core;

import io.github.mediatR.api.BusRequest;

public record BusRequestWrapper<Q>(Q request, Class<?> wrapperClass) implements BusRequest {
    public static <Q> BusRequestWrapper<Q> of(Q request) {
        return new BusRequestWrapper<>(request, request.getClass());
    }
}
