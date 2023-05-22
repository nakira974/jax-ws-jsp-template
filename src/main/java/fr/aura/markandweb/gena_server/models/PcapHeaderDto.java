package fr.aura.markandweb.gena_server.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jnetpcap.PcapIf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class PcapHeaderDto {

    public static final int SIZE_BYTES = 24;

    private final short majorVersion;
    private final short minorVersion;
    private final int timezoneOffsetSeconds;
    private final int timezoneOffsetMicros;
    private final int snapshotLength;
    private final int linkType;

    public PcapHeaderDto(FileChannel channel, PcapIf device) throws Exception {
        this.majorVersion = 2; // PCAP version 2.x
        this.minorVersion = 4; // PCAP version 2.4
        this.timezoneOffsetSeconds = 0; // GMT
        this.timezoneOffsetMicros = 0; // GMT
        this.snapshotLength = computeSnapshotLength(device);
        this.linkType = computeLinkType(channel);
    }

    public void writeTo(@NotNull ByteBuffer buffer) {
        buffer.putShort(majorVersion);
        buffer.putShort(minorVersion);
        buffer.putInt(timezoneOffsetSeconds);
        buffer.putInt(timezoneOffsetMicros);
        buffer.putInt(snapshotLength);
        buffer.putInt(linkType);
    }

    @Contract("_ -> new")
    private static @NotNull Integer computeSnapshotLength(@NotNull PcapIf device) throws Exception {
        // Compute the snapshot length as the minimum of the maximum packet size and 65535 (the maximum allowed by libpcap)
        return Math.min(device.getHardwareAddress().length + 14, 65535);
    }

    @Contract("_ -> new")
    private static @NotNull Integer computeLinkType(@NotNull FileChannel channel) throws Exception {
        ByteBuffer headerBuffer = ByteBuffer.allocateDirect(SIZE_BYTES).order(
                ByteOrder.nativeOrder());
        channel.read(headerBuffer);
        headerBuffer.flip();
        return (int) headerBuffer.getShort(20);
    }

}