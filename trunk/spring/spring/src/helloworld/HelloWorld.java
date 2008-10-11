package helloworld;




public class HelloWorld{
   // protected static final Log log=LogFactory.getLog(HelloWorld.class);
    public String getContent(){
        FileHelloStr fhStr=new FileHelloStr("helloworld.properties");
        String helloworld = fhStr.getContent();
        return helloworld;
    }
}
