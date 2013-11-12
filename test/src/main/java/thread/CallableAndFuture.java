package thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// http://blog.csdn.net/ghsau/article/details/7451464
public class CallableAndFuture {  
    public static void main(String[] args) {  
        Callable<Integer> callable = new Callable<Integer>() {  
            public Integer call() throws Exception {  
                return new Random().nextInt(100);  
            }  
        };  
        FutureTask<Integer> future = new FutureTask<Integer>(callable);  
        new Thread(future).start();  
        try {  
            Thread.sleep(5000);
            System.out.println(future.get());  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (ExecutionException e) {  
            e.printStackTrace();  
        }  
    }  
}  