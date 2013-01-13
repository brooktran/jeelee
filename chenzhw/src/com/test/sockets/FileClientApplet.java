package com.test.sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileClientApplet extends JApplet {

  private static final int PORT = 5000; // Default port

  public void init() {
    final JTextArea text = new JTextArea(8, 20);
    getContentPane().add(new JScrollPane(text), "Center");

    JButton saveButton = new JButton("Save");
    getContentPane().add(saveButton, "South");
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Socket socket = null;
        String message = "File Saved";
        try {
          // Create socket connected to server on specified port
          socket = new Socket(getCodeBase().getHost(), PORT);

          socket.getOutputStream().write(text.getText().getBytes());


        } catch (Exception ex) {
          message = ex.getMessage();
        } finally {
          text.setText(message);
          try {
            socket.close();
          } catch (IOException ex) {
          }
        }
      }
    });
  }
}
