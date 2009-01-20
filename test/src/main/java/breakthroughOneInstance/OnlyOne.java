package breakthroughOneInstance;

public class OnlyOne implements IOnlyOne{
	
    private static final OnlyOne myOne = new OnlyOne();
    
    public void printOne(){
        System.out.println(myOne);
    }
    public static void main(String[] args) throws Exception{
        myOne.printOne();
        OnlyOneLoader loader = new OnlyOneLoader();
        IOnlyOne o = loader.createNewOne();
        o.printOne();
    }
}
