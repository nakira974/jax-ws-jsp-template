package fr.aura.markandweb.gena_server.models;

import java.util.Date;

public record NpcapPacketDto(Date timestamp, byte[] data) {

    public NpcapPacketDto(Date timestamp, byte[] data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public byte[] getData() {
        return data;
    }
}
