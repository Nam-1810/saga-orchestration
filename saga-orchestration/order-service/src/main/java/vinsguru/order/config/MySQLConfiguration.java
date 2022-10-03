package vinsguru.order.config;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class MySQLConfiguration extends AbstractR2dbcConfiguration {

	@Override
	protected List<Object> getCustomConverters() {
		return List.of(new UUIDToByteArrayConverter(), new ByteArrayToUUIDConverter());
	}

	@WritingConverter
	public class UUIDToByteArrayConverter implements Converter<UUID, byte[]> {
		@Override
		public byte[] convert(UUID source) {
			ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
			bb.putLong(source.getMostSignificantBits());
			bb.putLong(source.getLeastSignificantBits());
			return bb.array();
		}
	}

	@ReadingConverter
	public class ByteArrayToUUIDConverter implements Converter<byte[], UUID> {
		@Override
		public UUID convert(byte[] source) {
			ByteBuffer bb = ByteBuffer.wrap(source);
			long firstLong = bb.getLong();
			long secondLong = bb.getLong();
			return new UUID(firstLong, secondLong);
		}
	}

	@Override
	public ConnectionFactory connectionFactory() {
		return null;
	}

}
