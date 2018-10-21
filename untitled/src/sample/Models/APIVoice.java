package sample.Models;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import javax.sound.sampled.AudioFormat;
import java.io.FileOutputStream;

public class APIVoice
{
    private static String apiKey = "a28ab2f316f646758c969420f51ad4c2";
    public static void loadVoice  (String text) throws Exception {

        VoiceProvider tts = new VoiceProvider(apiKey);

        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.MP3);
        //params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_8bit_mono);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("../voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();

    }
}

