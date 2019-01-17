package wangfeixixi.com.cw.widget.udp.server;//package wangfeixixi.cip.widget.udp.server;
//
//import wangfeixixi.cip.widget.udp.server.IUDPResultListener;
//import wangfeixixi.cip.widget.udp.server.UDPServerThread;
//import wangfeixixi.cip.widget.udp.server.UdpServerRunable;
//import wangfeixixi.com.base.crash.LogUtils;
//
//public class UDPUtils {
//    private static UdpServerRunable udpServer;
//
//    public static UDPServerThread udpserverthread;
//
//    public static void startUDPServer() {
//        udpserverthread = new UDPServerThread();
//        udpserverthread.start();
//    }
//
//    public static void stopUDPServer() {
//        if (udpserverthread == null) return;
//        udpserverthread.isRunning = false;
//        udpserverthread.interrupt();
//        try {
//            udpserverthread.join();
//        } catch (InterruptedException e) {
////            e.printStackTrace();
//            LogUtils.d("udp线程中止失败" + e.getMessage());
//        }
//        udpserverthread = null;
//    }
//
//
//    public static boolean isStart = false;
//
//    public static void udpServer(IUDPResultListener listener) {
//        if (isStart) {
//            stopServer();
//        } else {
//            startServer(listener);
//        }
//        isStart = !isStart;
//    }
//
//    public static void startServer(IUDPResultListener listener) {
//        udpServer = new UdpServerRunable();
//        udpServer.setResultListener(listener);
//        Thread thread = new Thread(udpServer);
//        thread.start();
//    }
//
//    public static void stopServer() {
//        LogUtils.d("停止");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //关闭UDP
//                if (udpServer != null)
//                    udpServer.setUdpLife(false);
//            }
//        }).start();
//    }
//}