/* AudioPlayer.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chess.renju.utils;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * <B>AudioPlayer</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-17 created
 * @since Renju Ver 1.0
 * 
 */
public class AudioPlayer {
	
	
	public AudioPlayer() {
	}
	
	public void play(File file) throws Exception {
		AudioInputStream stream = AudioSystem.getAudioInputStream(file); 
        AudioFormat format = stream.getFormat();   

        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {   
            format = new AudioFormat(   
                    AudioFormat.Encoding.PCM_SIGNED,   
                    format.getSampleRate(),   
                    format.getSampleSizeInBits() ,   
                    format.getChannels(),   
                    format.getFrameSize() ,   
                    format.getFrameRate(),   
                    true); // big endian   
            stream = AudioSystem.getAudioInputStream(format, stream);   
        }   
        // Create the clip   
        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),   
                ((int) stream.getFrameLength() * format.getFrameSize()));   
        Clip clip = (Clip) AudioSystem.getLine(info);   
        // This method does not return until the audio file is completely loaded   
        clip.open(stream);   
        // Start playing   
        clip.start();   
//        FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); 
	}
}
