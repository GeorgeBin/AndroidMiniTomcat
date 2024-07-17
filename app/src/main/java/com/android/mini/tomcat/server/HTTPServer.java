package com.android.mini.tomcat.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class HTTPServer extends Service {
  private static final String TAG = "HTTPServer-->";

  public static final int port = 8080;
  public static final String localAddress = "127.0.0.1";

  private static boolean needRunning = true;

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  public void await() {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port, 1, InetAddress.getByName(localAddress));
    } catch (Exception e) {
      e.printStackTrace();
      Log.i(TAG, "await-->" + e.getMessage());
    }

    while (needRunning) {
      Socket socket = null;
      InputStream inputStream = null;
      OutputStream outputStream = null;
      try {
        socket = serverSocket.accept();
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        Request request = new Request(inputStream);
        request.parse();
      } catch (Exception e) {
        Log.i(TAG, "await", e);
      }
    }
  }
}
