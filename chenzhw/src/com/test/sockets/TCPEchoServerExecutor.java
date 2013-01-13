package com.test.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 类<B> TCPEchoServerExecutor </B>
 * 自动管理线程，当线程异常退出时， Executor 自动生成新的线程代替之.
 * <p>
 * 若一个线程空闲时间超过60 秒，则 Executor 将其从线程池删除.
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-15 新建
 * @since chenzhw Ver 1.0
 */
public class TCPEchoServerExecutor {

  /**
   * The main method.
   * 
   * @param args the arguments
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void main(String[] args) throws IOException {

    if (args.length != 1) { // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");
    }

    int echoServPort = Integer.parseInt(args[0]); // Server port

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(echoServPort);

    Logger logger = Logger.getLogger("practical");

    Executor service = Executors.newCachedThreadPool();  // Dispatch svc 传送程序管理器

    // Run forever, accepting and spawning threads to service each connection
    while (true) {
      Socket clntSock = servSock.accept(); // Block waiting for connection
      service.execute(new EchoProtocol(clntSock, logger));	//调用 service
    }
    /* NOT REACHED */
  }
}
