package io.github.mediatR.api;

public interface RequestHandler<Q extends BusRequest, R> {
    R execute(Q request);
    Class<?> getRequestClass();
}
