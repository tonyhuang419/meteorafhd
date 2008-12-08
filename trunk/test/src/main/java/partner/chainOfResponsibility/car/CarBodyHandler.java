package partner.chainOfResponsibility.car;

public class CarBodyHandler extends CarHandler {

	@Override
	public void handleCar(int lastStep) {
		if (STEP_HANDLE_BODY <= lastStep) {
			System.out.println("Handle car's body.");
		}

		if (carHandler != null) {
			carHandler.handleCar(lastStep);
		}
	}
}
