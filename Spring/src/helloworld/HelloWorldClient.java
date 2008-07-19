package helloworld;



public class HelloWorldClient{
    //protected static final Log log=LogFactory.getLog(HelloWorldClient.class);
    public static void main(String[] args){
        HelloWorld hw = new HelloWorld();
        System.out.println(hw.getContent());
       // log.info(hw.getContent());
    }
   
}


