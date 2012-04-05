package nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClient {
    static int SIZE = 10;
    static InetSocketAddress ip = new InetSocketAddress("localhost", 12345);
    static CharsetEncoder encoder = Charset.forName("GB2312").newEncoder();
    static FileChannel[] filechannel = new FileChannel[SIZE];

    static class Download implements Runnable {
        protected int index;

        public Download(int index) {
            this.index = index;
        }

        public void run() {
            try {
                long start = System.currentTimeMillis();
                SocketChannel client = SocketChannel.open();
                client.configureBlocking(false);
                Selector selector = Selector.open();
                client.register(selector, SelectionKey.OP_CONNECT);
                client.connect(ip);
                ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
                int total = 0;
                boolean flag = true;
                while (flag) {
                    selector.select();
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isConnectable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            if (channel.isConnectionPending())
                                channel.finishConnect();
                            channel.write(encoder.encode(CharBuffer.wrap("Hello from " + index)));
                            channel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            int count = channel.read(buffer);
                            if (count > 0) {
                                writeToFile(buffer, index);
                                total += count;
                                buffer.clear();
                            } else {
                                filechannel[index].close();
                                client.close();
                                flag = false;
                            }
                        }
                    }
                }
                double last = (System.currentTimeMillis() - start) * 1.0 / 1000;
                System.out.println("Thread " + index + " downloaded " + total + "bytes in " + last + "s.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void writeToFile(ByteBuffer bb, int index) {
        try {
            if (filechannel[index] == null) {
                filechannel[index] = new FileOutputStream(new File("test" + index + ".jpg")).getChannel();
            }
            bb.flip();
            filechannel[index].write(bb);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService exec = Executors.newFixedThreadPool(SIZE);
        for (int index = 0; index < SIZE; index++) {
            exec.execute(new Download(index));
        }
        exec.shutdown();
    }
}

