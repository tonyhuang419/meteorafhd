package guava.eventBus;

import com.google.common.eventbus.Subscribe;

public class MultipleListener {
	public Integer lastInteger;  
	public Long lastLong;
	public Number lastNumber;

    @Subscribe  
    public void listen(Number event) {  
    	lastNumber = event; 
        System.out.println("event Number:"+lastNumber);
    }  
    
	@Subscribe  
	public void listenInteger(Integer event) {  
		lastInteger = event; 
		System.out.println("event Integer:"+lastInteger);
	}  

	@Subscribe  
	public void listenLong(Long event) {  
		lastLong = event; 
		System.out.println("event Long:"+lastLong);
	}  

	public Integer getLastInteger() {  
		return lastInteger;  
	}  

	public Long getLastLong() {  
		return lastLong;  
	}  
}
