package boot;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
//Imports the Google Cloud client library
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;

public class SpeechToText {
	
	ArrayList<String> getConvertText(byte[] data) throws Exception {
		String textRow;
		ArrayList<String> textList = new ArrayList<String>();
		
		// Configure GoogleCredential
		FileInputStream credentialsStream = new FileInputStream("My First Project-fa257743d788.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
		FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);

		SpeechSettings speechSettings = 
				SpeechSettings.newBuilder()
					.setCredentialsProvider(credentialsProvider)
					.build();

		SpeechClient speech = SpeechClient.create(speechSettings);
		
		// Initial audio to translate
		// Path path = Paths.get("./resources/fix.wav");
		// byte[] data = Files.readAllBytes(path);
		ByteString audioBytes = ByteString.copyFrom(data);

		// Configure request with local raw PCM audio
		RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.ENCODING_UNSPECIFIED)
				.setLanguageCode("he-il").setSampleRateHertz(16000).build();
		RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

		// Use blocking call to get audio transcript
		RecognizeResponse response = speech.recognize(config, audio);
		List<SpeechRecognitionResult> results = response.getResultsList();

		for (SpeechRecognitionResult result : results) {
			// There can be several alternative transcripts for a given chunk of
			// speech. Just use the
			// first (most likely) one here.
			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
			textRow = alternative.getTranscript();
			textList.add(textRow);
			//System.out.printf("Transcription: %s%n", alternative.getTranscript());
		}
		speech.close();
		
		
		return textList;
	}

}
