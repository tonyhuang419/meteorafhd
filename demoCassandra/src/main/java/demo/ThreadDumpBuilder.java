package demo;
import java.util.Map;


/**  
* ʹ�� Java Զ�̴������� ThreadDump. ������ JDK 1.5+.  
* �ο�: {@link Thread#getStackTrace()}  
* {@link Throwable#getStackTrace()}  
* @see StackTraceElement  
* @author beansoft@126.com  
* ת����ע������: beansoft.blogjava.net  
*/  
public class ThreadDumpBuilder {   
    /**  
     * ���ɲ����� Thread Dump.  
     * ת����ע������: beansoft.blogjava.net  
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
     * ���������ջ��Ϣ.  
     * @param output  
     *            �������  
     * @param thread  
     *            �߳�  
     * @param stack  
     *            �̶߳�ջ  
     */  
    private void appendThreadStackTrace(StringBuilder output, Thread thread,   
            StackTraceElement[] stack) {   
        // ���Ե�ǰ�̵߳Ķ�ջ��Ϣ   
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
