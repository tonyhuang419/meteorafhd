package nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

//http://zheng12tian.iteye.com/blog/1094811
public class TestNio {
	
	public static void main(String[] args) {  
		/** 
		 * 分配空间 隐含地在内存中分配了一个byte型数组来存储10个byte 
		 */  
		ByteBuffer buffer = ByteBuffer.allocate(10);  
		/** 
		 * 填充元素 buffer.hasRemaining()用于判断缓冲区是否达到上界limit。 
		 * 该填充过程等效于：
		 * int remainCount = buffer.remaining();
		 * for (int j = 0; j < remainCount; j++)
		 * {buffer.put((byte) j++);} 
		 */  
		int i = 0;  
		while (buffer.hasRemaining()) {  
			buffer.put((byte) i++);  
		}  
		/** 
		 * 翻转缓冲区 将缓冲区进行翻转操作，即在缓冲区写入完毕时，将缓冲区翻转成一个准备读出元素的状态。 
		 * flip操作等效于
		 * buffer.limit(buffer.position()).position(0);
		 * 同时将mark设为-1。源码如下：
		 * public final Buffer flip()
		 * { limit = position;position = 0;mark = -1;return this;} 
		 */  
		buffer.flip(); 
		
		/** 
		 * 读取缓冲区 
		 */  
		int remainCount = buffer.remaining();  
		for (int j = 0; j < remainCount; j++) {  
			System.out.print(buffer.get() + " ");  
		}  
		System.out.println();  

		/** 
		 * 字节顺序 
		 */  
		System.out.println("ByteOrder的字节顺序为：" + ByteOrder.nativeOrder());  
		System.out.println("ByteBuffer的字节顺序为：" + buffer.order());  
		CharBuffer charBuffer = CharBuffer.allocate(10);  
		System.out.println("CharBuffer的字节顺序为：" + charBuffer.order());  
		// 修改ButyBuffer的字节顺序  
		buffer.order(ByteOrder.LITTLE_ENDIAN);  
		System.out.println("ByteBuffer的字节顺序为：" + buffer.order());  

		/** 
		 * 只有ByteBuffer可以创建直接缓冲区，用wrap函数创建的缓冲区都是非直接的缓冲区 
		 */  
		ByteBuffer redirectByteBuffer = ByteBuffer.allocateDirect(10);  
		System.out.println("判断缓冲区是否为直接缓冲区：" + redirectByteBuffer.isDirect());  

		/** 
		 * 先创建一个大端字节顺序的ByteBuffer，然后再创建一个字符视图缓冲区 
		 */  
		ByteBuffer bigByteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);  
		CharBuffer viewCharBuffer = bigByteBuffer.asCharBuffer();  
		/** 
		 * 在字符视图的基础上创建只读字符视图 
		 */  
		CharBuffer onlyReadCharBuffer = viewCharBuffer.asReadOnlyBuffer();  
		/** 
		 * 创建一个与原始缓冲区相似的新缓冲区，两个缓冲区共享数据元素，拥有同样的容量，但每个缓冲区拥有各自的位置、上界、标记属性。 对一个缓冲区的数据元素所做的改变会反映在另一个缓冲区上，新的缓冲区会继承原始缓冲区的这些属性。 
		 */  
		CharBuffer copyCharBuffer = viewCharBuffer.duplicate();  
		/** 
		 * 创建原始缓冲区子集的新缓冲区 
		 */  
		CharBuffer cutCharBuffer = viewCharBuffer.slice();  
	}  
}
