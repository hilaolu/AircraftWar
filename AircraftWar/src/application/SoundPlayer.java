package application;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.SourceDataLine;

public class SoundPlayer implements Runnable {

	// defining the byte buffer
	private static final int BUFFER_SIZE = 4096;

	private boolean exit = false;
	private boolean loop = false;
	private boolean play = false;
	Thread t;
	File soundFile;

	SoundPlayer(String fileName, boolean loop) {
		soundFile = new File(fileName);
		t = new Thread(this, fileName);
		System.out.println("New thread: " + t);
		exit = false;
		this.loop = loop;
		t.start();
	}

	SoundPlayer(String fileName) {
		this(fileName, false);
	}

	void exit() {
		exit = true;
	}

	void play() {
		play = true;
	}

	void stop() {
		play = false;
	}

	void play(String filePath) {
	}

	public void run() {

		// if (AudioSystem.getMixerInfo().len() == 0) {
		// return;
		// }

		try {
			// convering the audio file to a stream

			while (!exit) {

				AudioInputStream sampleStream = AudioSystem.getAudioInputStream(soundFile);

				AudioFormat formatAudio = sampleStream.getFormat();

				DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);

				SourceDataLine theAudioLine = (SourceDataLine) AudioSystem.getLine(info);

				theAudioLine.open(formatAudio);

				theAudioLine.start();

				System.out.println("Audio Player Started.");

				byte[] bufferBytes = new byte[BUFFER_SIZE];
				int readBytes = -1;

				while (((readBytes = sampleStream.read(bufferBytes)) != -1) && !exit) {
					while (!play | exit) {
					}
					theAudioLine.write(bufferBytes, 0, readBytes);
				}

				exit = exit & !loop;

				theAudioLine.drain();
				theAudioLine.close();
				sampleStream.close();

				System.out.println("Playback has been finished.");
			}

		} catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported file.");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("Line not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Experienced an error.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Experienced an error.");
		}
	}

}