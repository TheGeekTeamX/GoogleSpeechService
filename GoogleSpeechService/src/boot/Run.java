package boot;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Run {
	public static void main(String[] args) throws Exception, IOException {
		
		SpeechToText st = new SpeechToText();
		
		Path path = Paths.get("./resources/fix.wav");
		byte[] data = Files.readAllBytes(path);
		ArrayList<String> test = new ArrayList<String>();
		test = st.getConvertText(data);
		System.out.println(test.toString());

	}
}


/*
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
*/