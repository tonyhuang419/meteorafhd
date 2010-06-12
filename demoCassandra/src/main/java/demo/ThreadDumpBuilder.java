package demo;
import java.util.Map;


/**  
* 使用 Java 远程代码生成 ThreadDump. 适用于 JDK 1.5+.  
* 参考: {@link Thread#getStackTrace()}  
* {@link Throwable#getStackTrace()}  
* @see StackTraceElement  
* @author beansoft@126.com  
* 转载请注明出处: beansoft.blogjava.net  
*/  
public class ThreadDumpBuilder {   
    /**  
     * 生成并返回 Thread Dump.  
     * 转载请注明出处: beansoft.blogjava.net  
     * @return  
     */  
    public String build() {   
        StringBuilder output = new StringBuilder(1000);   
        for (Map.Entry stackTrace : Thread.getAllStackTraces().entrySet()) {   
            appendThreadStackTrace(output, (Thread) stackTrace.getKey(),   
                    (StackTraceElement[]) stackTrace.getValue());   
        }   
        return output.toString();   
    }    
  
    /**  
     * 处理并输出堆栈信息.  
     * @param output  
     *            输出内容  
     * @param thread  
     *            线程  
     * @param stack  
     *            线程堆栈  
     */  
    private void appendThreadStackTrace(StringBuilder output, Thread thread,   
            StackTraceElement[] stack) {   
        // 忽略当前线程的堆栈信息   
        if (thread.equals(Thread.currentThread())) {   
            return;   
        }    
  
        output.append(thread).append("\n");   
        for (StackTraceElement element : stack) {   
            output.append("\t").append(element).append("\n");   
        }   
    }    
    
    public static void main(String[] args){
    	String s = new ThreadDumpBuilder().build();
    	System.out.println(s);
    }
  
}  
