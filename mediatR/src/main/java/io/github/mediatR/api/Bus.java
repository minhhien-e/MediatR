package io.github.mediatR.api;


public interface Bus {
    <R, T extends BusRequest> R send(T request);
}
