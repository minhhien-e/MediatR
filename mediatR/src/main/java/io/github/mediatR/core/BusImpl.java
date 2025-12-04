package io.github.mediatR.core;

import io.github.mediatR.api.Bus;
import io.github.mediatR.api.BusRequest;
import io.github.mediatR.api.RequestHandler;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BusImpl implements Bus {
    private final Map<Class<?>, RequestHandler<?, ?>> requestHandlers = new HashMap<>();

    public BusImpl(Map<String, RequestHandler> handlerMap) {
        handlerMap.values().forEach(handler -> {
            Class<?> requestType = handler.getRequestClass();
            requestHandlers.put(requestType, handler);
        });
    }

    @Override
    public <R, T extends BusRequest> R send(T request) {
        BusRequestWrapper wrapper = (BusRequestWrapper) request;
        RequestHandler<BusRequest, R> handler =
                (RequestHandler<BusRequest, R>) requestHandlers.get(wrapper.wrapperClass());

        if (handler == null) {
            throw new IllegalStateException("No handler found for request: " + request.getClass());
        }

        return handler.execute(request);
    }
}