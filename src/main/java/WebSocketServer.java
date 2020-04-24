import recognization.AudioRecognizer;

import javax.sound.sampled.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Date;

@ServerEndpoint("/ws")
public class WebSocketServer {
    static Integer chunkNumber = 0;
    static File uploadedFile = null;
    static String fileName = null;
    static FileOutputStream fos = null;
    final static String filePath="d:/download/";

    @OnOpen
    public void onOpen() {
        System.out.println("Open connection...");
    }

    @OnClose
    public void onClose() {
        System.out.println("Closing connection...");
    }

    /*@OnMessage
    public String onMessage(String message) {
        System.out.println("Message from client: " + message);
        String echo = "Echo from server: " + message;
        return echo;
    }*/

    /*@OnMessage
    public void message(Session session, String msg) {
        System.out.println("got msg: " + msg);
        if(!msg.equals("end")) {
            fileName=msg.substring(msg.indexOf(':')+1);
            uploadedFile = new File(filePath+fileName);
            try {
                fos = new FileOutputStream(uploadedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*@OnMessage
    public void message(byte[] data, boolean last, Session session) {
        System.out.println("Binary Data");

        try {
            // The temporary file that contains our captured audio stream
            File f = new File("out.wav");

            // if the file already exists we append it.
            if (f.exists()) {
                System.out.println("Adding received block to existing file.");

                // two clips are used to concat the data
                AudioInputStream clip1 = AudioSystem.getAudioInputStream(f);
                AudioInputStream clip2 = AudioSystem.getAudioInputStream(new ByteArrayInputStream(data));

                // use a sequenceinput to cat them together
                AudioInputStream appendedFiles =
                        new AudioInputStream(
                                new SequenceInputStream(clip1, clip2),
                                clip1.getFormat(),
                                clip1.getFrameLength() + clip2.getFrameLength());

                // write out the output to a temporary file
                AudioSystem.write(appendedFiles,
                        AudioFileFormat.Type.WAVE,
                        new File("out2.wav"));

                // rename the files and delete the old one
                File f1 = new File("out.wav");
                File f2 = new File("out2.wav");
                f1.delete();
                f2.renameTo(new File("out.wav"));
            } else {
                System.out.println("Starting new recording.");
                FileOutputStream fOut = new FileOutputStream("out.wav",true);
                fOut.write(data);
                fOut.close();
            }
        } catch (Exception e) {
            System.out.println("Exception!");
        }
    }*/

    @OnMessage
    public void message(String base64Data) {
        chunkNumber++;
         System.out.println("Binary Data");
         if(base64Data.equals("end")) {
             //end of record
             System.out.println("audio is received!");
         } else {
             System.out.println(base64Data);
             Base64.Decoder base64Decoder = Base64.getDecoder();
             byte[] decodedBytes = base64Decoder.decode(base64Data.split(",")[1]);
             //byte[] decodedBytes = base64Decoder.decode(base64Data);
             InputStream is = new ByteArrayInputStream(decodedBytes);
// Get AudioInputStream from InputStream
             try {
                 AudioInputStream audioIn = AudioSystem.getAudioInputStream(is);
             } catch (UnsupportedAudioFileException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             //byte[] decodedBytes = base64Decoder.decode(base64Data);


             FileOutputStream fos;
             try {

                 fos = new FileOutputStream("MyAudioFileNewww.mp3", true);
                 System.out.println(decodedBytes);
                 fos.write(decodedBytes);
                 fos.close();
             } catch(FileNotFoundException e) {
                 e.printStackTrace();
             } catch(IOException i) {
                 i.printStackTrace();
             } catch(Exception ex) {
                 ex.printStackTrace();
             }
         }



         /*String fileName ="test"+new Date().getTime()+".wav";
         String filePath = fileName;

         try {
             byte[] decoded = DatatypeConverter.parseBase64Binary(data.toString());
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                     new ByteArrayInputStream(decoded));

             /*byte[] decoded = DatatypeConverter.parseBase64Binary(data);
             InputStream b_in = new ByteArrayInputStream(decoded);
             AudioFormat format = new AudioFormat(48000, 16, 1, true, false);
             AudioInputStream stream = new AudioInputStream(b_in, format,
                     decoded.length);
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(format, stream);
             File file = new File(filePath);
             AudioSystem.write(stream,javax.sound.sampled.AudioFileFormat.Type.WAVE, file);
             System.out.println("File saved: " + file.getName() + ", bytes: "
                     + file.length());
             System.out.println("done");

         } catch (IOException ex) {
             System.err.println(ex);
         }*/
     }

    @OnError
    public void onError(Throwable e) {
        System.out.println("ERROR!");
        System.out.println(e.getLocalizedMessage());
        e.printStackTrace();
    }
}
