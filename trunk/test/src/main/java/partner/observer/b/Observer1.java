package partner.observer.b;

public class Observer1 implements IObserver {
	private String state;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void update(String state) {
		setState(state);
		System.out.println("Observer1 has received update signal with new state: " + getState());
	}
}

