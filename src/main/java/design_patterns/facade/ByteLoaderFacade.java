package designpatterns.facade;

import java.util.Arrays;

public class ByteLoaderFacade {
	private Compressor compressor = new Compressor() {
		@Override
		public Byte[] compress(Byte[] bytes) {
			return toBytes(Arrays.asList(bytes).stream().distinct().toArray());
		}
	};

	private Filter<Byte[]> filter = new Filter<Byte[]>() {
		@Override
		public Byte[] filter(Byte[] items) {
			return toBytes(Arrays.asList(items).stream().filter(bytes -> bytes != null && bytes != 0).toArray());
		}
	};

	private Uploader uploader = new Uploader() {
		@Override
		public void upload(Byte[] bytes) {
			System.out.println(Arrays.asList(bytes));
		}
	};

	public void load(Byte[] bytes) {
		if (bytes == null) {
			System.out.println("Cannot load null bytes");
			return;
		} else if (bytes.length > 1024) {
			System.out.println("Too many bytes to load, must be less than or equal to 1024 bytes");
			return;
		}

		Byte[] filteredBytes = filter.filter(bytes);
		Byte[] compressedBytes = compressor.compress(filteredBytes);
		uploader.upload(compressedBytes);
		System.out.println("Bytes have been loaded");
	}

	private static Byte[] toBytes(Object[] bytes) {
		Byte[] realBytes = new Byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			realBytes[i] = (Byte) bytes[i];
		}
		return realBytes;
	}
}
