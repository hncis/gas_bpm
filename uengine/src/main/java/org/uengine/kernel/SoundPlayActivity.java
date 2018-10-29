package org.uengine.kernel;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import org.uengine.contexts.FileContext;
 
public class SoundPlayActivity extends DefaultActivity {
 
 	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;

	private Position curPosition= Position.NORMAL;
 
    private final static int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
 	
    enum Position {
        LEFT, RIGHT, NORMAL
    };
 
    public SoundPlayActivity() {
    	super("Play Sound");
    }
 
	public void executeActivity(ProcessInstance instance) throws Exception {
		playSound(instance);
		
		fireComplete(instance);
	
    }
	
	public void playSound(ProcessInstance instance) throws Exception {
		AudioInputStream audioInputStream = null;
		SourceDataLine auline = null;

		try {
			try {
				File soundFile = this.getSoundFile().getFile();
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (Exception e) {
				URL url = new URL(evaluateContent(instance, this.getSoundUrl()).toString());
				audioInputStream = AudioSystem.getAudioInputStream(url);
			}

			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);

			if (auline.isControlSupported(FloatControl.Type.PAN)) {
				FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);

				if (curPosition == Position.RIGHT) {
					pan.setValue(1.0f);
				} else if (curPosition == Position.LEFT) {
					pan.setValue(-1.0f);
				}
			}

			auline.start();

			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					auline.write(abData, 0, nBytesRead);
				}
			}
			
			auline.drain();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (auline != null) try { auline.close(); } catch (Exception e1) { }
			if (audioInputStream != null) try { audioInputStream.close(); } catch (Exception e1) { }
		}
 

    }
	
	FileContext soundFile;		
		public FileContext getSoundFile() {
			return soundFile;
		}
	
		public void setSoundFile(FileContext soundFile) {
			this.soundFile = soundFile;
		}

	String soundUrl;
	
		public String getSoundUrl() {
			return soundUrl;
		}
	
		public void setSoundUrl(String soundUrl) {
			this.soundUrl = soundUrl;
		}

		
		
		public static void main(String[] args) throws Exception{
			SoundPlayActivity spa = new SoundPlayActivity();
			
			spa.setSoundUrl("https://tts.neospeech.com/audio/a.php/646241/5fb0c7818120/result_1.wav");
			
			spa.executeActivity(null);
		}
}
 