package fr.aura.markandweb.gena_server.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.List;

import fr.aura.markandweb.gena_server.models.NpcapPacketDto;
import fr.aura.markandweb.gena_server.models.PcapHeaderDto;
import org.jetbrains.annotations.NotNull;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JMemoryPacket;
import org.jnetpcap.packet.PcapPacket;

public class PcapSerializer {

    private final Path filePath;

    public PcapSerializer(Path filePath) {
        this.filePath = filePath;
    }

    public void serialize(@NotNull List<NpcapPacketDto> packets, @NotNull PcapIf device) throws Exception {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            ByteBuffer headerBuffer = ByteBuffer.allocate(PcapHeaderDto.SIZE_BYTES);
            headerBuffer.order(ByteOrder.nativeOrder());

            // Write global header
            PcapHeaderDto pcapGlobalHeader = new PcapHeaderDto(fileChannel, device);
            pcapGlobalHeader.writeTo(headerBuffer);
            headerBuffer.flip();
            fileChannel.write(headerBuffer);

            // Write packets
            PcapDumper dumper = new PcapDumper();
            for (NpcapPacketDto packetDto : packets) {
                JMemoryPacket packet = new JMemoryPacket(packetDto.getData());
                packet.getCaptureHeader().wirelen(packetDto.getData().length);
                packet.getCaptureHeader().seconds(packetDto.getTimestamp().getTime() / 1000L);
                dumper.dump(new PcapPacket(packet));
            }
        }
    }

}