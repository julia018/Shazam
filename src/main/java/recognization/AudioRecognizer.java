package recognization;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;

public class AudioRecognizer {
    public static void main(String[] args) {
        AudioRecognizer recognizer = new AudioRecognizer();
        //recognizer.generateFingerprint(new File("D:\\SPP\\WebSocketServerExample\\ShazamApp\\src\\main\\resources\\MyAudioFileNewww.mp3"));
        recognizer.generateFingerprint();
    }

    public AudioRecognizer() {
    }

    public void generateFingerprint() {
        try {
            //Path file = Paths.get("D:\\\\SPP\\\\WebSocketServerExample\\\\ShazamApp\\\\src\\\\main\\\\resources\\\\MyAudioFileNew.webm");


            //byte[] decoded = Files.readAllBytes(file);

            //AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                 //   new ByteArrayInputStream(decoded));

            File file = new File("D:\\SPP\\WebSocketServerExample\\ShazamApp\\src\\main\\resources\\30_seconds_to_mars_-_dangerous_night_(zf.fm).wav");
            AudioInputStream in = AudioSystem.getAudioInputStream(file);
            /*byte[] decode = Files.readAllBytes(file);

            String encoded = Base64.encode(decode, 0);
            byte[] decoded = Base64.decode(encoded);
// Convert byte array to inputStream
            InputStream is = new ByteArrayInputStream(decoded);
// Get AudioInputStream from InputStream
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(is);
// Acquire audio format and create a DataLine.Infoobject:
            AudioFormat format = audioIn.getFormat();*/
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }


    }
}
