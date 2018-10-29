package org.uengine.kernel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javazoom.jl.player.Player;

import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.contexts.FileContext;
import org.uengine.contexts.KeyValueContext;
import org.uengine.contexts.SoundUrlContext;
import org.uengine.util.UEngineUtil;


public class ExtSoundPlayActivity extends DefaultActivity {
	
	private static final long serialVersionUID = GlobalContext.SERIALIZATION_UID;
	
	public ExtSoundPlayActivity() {
		super("Extended SoundPlay");
		SoundUrlContext suc = new SoundUrlContext();
		suc.setCharset(GlobalContext.ENCODING);
		suc.setPost(true);
		setSoundUrl(suc);
		setMode("url");
	}
	
	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd = type.getFieldDescriptor("Mode");
		fd.setInputter(new RadioInput(new String[]{"Url", "File"}, new String[]{"url", "file"}));
		
//		fd.getInputter().setValue("file");
//		
//		EnablingDependancy enableIfFileFile = new EnablingDependancy("Mode"){
//			public boolean enableIf(Object dependencyFieldValue) {
//				return dependencyFieldValue != null && dependencyFieldValue.equals("file");
//			}
//		};
//		
//		EnablingDependancy enableIfUrlFile = new EnablingDependancy("Mode"){
//			public boolean enableIf(Object dependencyFieldValue) {
//				return dependencyFieldValue != null && dependencyFieldValue.equals("url");
//			}
//		};
//		
//		fd = type.getFieldDescriptor("SoundUrl");		
//		fd.setAttribute("dependancy", enableIfUrlFile);
//		
//		fd = type.getFieldDescriptor("File");		
//		fd.setAttribute("dependancy", enableIfFileFile);
	}
	
	private String mode;
	
	private SoundUrlContext soundUrl;
	
	private FileContext soundFile;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public SoundUrlContext getSoundUrl() {
		return soundUrl;
	}

	public void setSoundUrl(SoundUrlContext soundUrl) {
		this.soundUrl = soundUrl;
	}

	public FileContext getSoundFile() {
		return soundFile;
	}

	public void setSoundFile(FileContext soundFile) {
		this.soundFile = soundFile;
	}
	
	public void play(ProcessInstance instance) throws Exception {
		InputStream is = null;
		OutputStreamWriter osw = null;
		
		try {
			try {
				File soundFile = this.getSoundFile().getFile();
				is = new FileInputStream(soundFile);
			} catch (Exception e) {
				
				SoundUrlContext soundUrl = this.getSoundUrl();
				KeyValueContext[] parameters = soundUrl.getParameters();
				String strUrl = this.getSoundUrl().getUrl();
				StringBuffer param = new StringBuffer(); 
				String charset = UEngineUtil.isNotEmpty(soundUrl.getCharset()) ? soundUrl.getCharset() : GlobalContext.ENCODING; 
				
				for (int i=0; i<parameters.length; i++) {
					if (i > 0) {
						param.append("&");
					} else {
						param.append("?");
					}
					param.append(evaluateContent(instance, parameters[i].getName()).toString())
					.append("=") 
					.append(URLEncoder.encode(evaluateContent(instance, parameters[i].getValue()).toString(), charset));
				}
				
				if (!soundUrl.isPost()) {
					strUrl += param.toString();
				}
				
				URL url = new URL(evaluateContent(instance, this.getSoundUrl().getUrl()).toString());
				HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				
				if (soundUrl.isPost()) {
					urlConn.setDoOutput(true);
					osw = new OutputStreamWriter(urlConn.getOutputStream());
					osw.write(param.substring(1));
					osw.flush();
				}
				
				is = urlConn.getInputStream();
			}
			
			Player player = new Player(is);
			player.play();
			player.close();
			
		} catch (Exception e) {
			if (is != null) is.close();
			if (osw != null) osw.close();
		}
	}
	
	public void executeActivity(ProcessInstance instance) throws Exception {
		play(instance);
		fireComplete(instance);
	}
	
	public ValidationContext validate(Map options) {
		ValidationContext vc =  super.validate(options);
		
		if (!UEngineUtil.isNotEmpty(getMode())) {
			vc.add("Mode is mandatory.");
		} else {
			if ("url".equals(getMode())) {
				if(getSoundUrl() == null || !UEngineUtil.isNotEmpty(getSoundUrl().getUrl())) {
					vc.add("Url is mandatory.");
				}
			} else {
				if(getSoundFile() == null || !UEngineUtil.isNotEmpty(getSoundFile().getPath())) {
					vc.add("FilePath is mandatory.");
				}
			}
		}

		return vc;
	}
	
	/* 
	 * TTS 가 읽지 않는 특수기호 : []_~!@^-*(){},.?<>/
	 * TTS 가 읽는특수기호 : #$%&
	 */
	public static void main(String[] args) throws Exception{
		ExtSoundPlayActivity act = new ExtSoundPlayActivity();
		SoundUrlContext soundUrl = new SoundUrlContext();
		KeyValueContext[] parameters = new KeyValueContext[2];
		parameters[0] = new KeyValueContext("speaker", "10");
		parameters[1] = new KeyValueContext("content", "배달하는 집배원. 물건파는 판애원. 기타치는 김태원. 모두 모여 이태원. 이태원 프리덤. 저 찬란한 불빛. 오. 오오.");
		
		soundUrl.setCharset(GlobalContext.ENCODING);
		soundUrl.setUrl("http://www.neospeech.com/GetAudio1.ashx");
		soundUrl.setPost(true);
		soundUrl.setParameters(parameters);
		
		act.setMode("url");
		act.setSoundUrl(soundUrl);
		
		//act.executeActivity(null);
		act.play(null);
		System.out.println("완료...");
	}
}