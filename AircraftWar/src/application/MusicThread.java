package application;

import javax.sound.sampled.*;

import java.io.*;

public class MusicThread extends Thread {
	private final String filename;
	private AudioFormat audioFormat;
	private byte[] samples;
	private long timeMs = 0;

	public MusicThread(String filename) {
		// 初始化 filename
		this.filename = filename;
		reverseMusic();
	}

	public void reverseMusic() {
		try {
			// 定义一个 AudioInputStream 用于接收输入的音频数据，使用 AudioSystem 来获取音频的音频输入流
			String path = "AircraftWar/resources/sounds/";

			AudioInputStream stream2 = AudioSystem.getAudioInputStream(new File(path + filename));

			AudioInputStream audioInputStream = stream2;
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info lineInfo = new DataLine.Info(Clip.class, format);

			Mixer.Info selectedMixer = null;

			for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
				Mixer mixer = AudioSystem.getMixer(mixerInfo);
				if (mixer.isLineSupported(lineInfo)) {
					System.out.print(mixerInfo);
					selectedMixer = mixerInfo;
					break;
				}
			}

			if (selectedMixer == null) {
				return;
			}

			Clip clip = AudioSystem.getClip(selectedMixer);
			clip.open(stream2);
			stream2.close();
			timeMs = clip.getMicrosecondLength();
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(path + filename));
			// 用 AudioFormat 来获取 AudioInputStream 的格式
			audioFormat = stream.getFormat();
			samples = getSamples(stream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public byte[] getSamples(AudioInputStream stream) {
		int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
		byte[] samples = new byte[size];
		DataInputStream dataInputStream = new DataInputStream(stream);
		try {
			dataInputStream.readFully(samples);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return samples;
	}

	protected void writeDataToAudioBlock(InputStream source, SourceDataLine dataLine, int bufferSize)
			throws InterruptedException {
		byte[] buffer = new byte[bufferSize];
		try {
			int numBytesRead;
			do {
				Thread.sleep(100);
				// if (Thread.currentThread().isInterrupted()) {
				// throw new InterruptedException();
				// } else {
				// System.out.println("writeDataToAudioBlock inner running at " +
				// Thread.currentThread());
				// }
				// 从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
				numBytesRead = source.read(buffer, 0, buffer.length);
				// 通过此源数据行将数据写入混频器
				if (numBytesRead != -1) {
					dataLine.write(buffer, 0, numBytesRead);
				}
			} while (numBytesRead != -1);
			// Thread.sleep(getTimeMs() / 1000);
			// if (Thread.currentThread().isInterrupted()) {
			// throw new InterruptedException();
			// } else {
			// System.out.println("writeDataToAudioBlock done");
			// }
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void play(InputStream source) {
		int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
		// 源数据行 SourceDataLine 是可以写入数据的数据行
		// 获取受数据行支持的音频格式 DataLine.info
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine dataLine;
		try {
			dataLine = (SourceDataLine) AudioSystem.getLine(info);
			dataLine.open(audioFormat, size);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}
		dataLine.start();

		try {
			writeDataToAudioBlock(source, dataLine, size);
		} catch (InterruptedException ignored) {
		}

		dataLine.drain();
		dataLine.close();

	}

	protected byte[] getSamples() {
		return samples;
	}

	public long getTimeMs() {
		return timeMs;
	}

	@Override
	public void run() {
		InputStream stream = new ByteArrayInputStream(samples);
		play(stream);
	}
}