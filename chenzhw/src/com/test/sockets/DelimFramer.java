package com.test.sockets;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DelimFramer implements Framer {

  private InputStream in;        // data source
  private static final byte DELIMITER = '\n'; // message delimiter

  public DelimFramer(InputStream in) {
    this.in = in;
  }

  
  public void frameMsg(byte[] message, OutputStream out) throws IOException {
    // ensure that the message does not contain the delimiter
    for (byte b : message) {//Verify well-formednes
      if (b == DELIMITER) {//Check that the given message does not contain the delimiter; if so, throw an exception.
        throw new IOException("Message contains delimiter");
      }
    }
    out.write(message);//Output the framed message to the stream
    out.write(DELIMITER);
    out.flush();
  }

  /** extracts messages from input
   * @see com.test.sockets.Framer#nextMsg()
   */
  public byte[] nextMsg() throws IOException {
    ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
    int nextByte;

    // fetch bytes until find delimiter
    while ((nextByte = in.read()) != DELIMITER) {//Read each byte in the stream until the delimiter is found
    	/*Handle end of stream: lines
    	If the end of stream occurs before finding the delimiter, throw an exception if any bytes
    	have been read since construction of the framer or the last delimiter; otherwise return
    	null to indicate that all messages have been received.*/
    	if (nextByte == -1) { // end of stream?
        if (messageBuffer.size() == 0) { // if no byte read
          return null;
        } else { // if bytes followed by end of stream: framing error
          throw new EOFException("Non-empty message without delimiter");
        }
      }
      messageBuffer.write(nextByte); // Write non-delimiter byte to message buffer
    }

    return messageBuffer.toByteArray();
  }
}
