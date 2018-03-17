package boot;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
//Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Run {
	public static void main(String[] args) throws Exception, IOException {
		
		// Configure GoogleCredential
		FileInputStream credentialsStream = new FileInputStream("My Project-d3bff28438ec.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
		FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);

		SpeechSettings speechSettings = 
				SpeechSettings.newBuilder()
					.setCredentialsProvider(credentialsProvider)
					.build();

		SpeechClient speech = SpeechClient.create(speechSettings);
		
		// Initial audio to translate
		Path path = Paths.get("./resources/fix.wav");
		byte[] data = Files.readAllBytes(path);
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
			System.out.printf("Transcription: %s%n", alternative.getTranscript());
		}
		speech.close();
	}
}
