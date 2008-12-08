package partner.chainOfResponsibility.car;

public class CarHeadHandler extends CarHandler {
	@Override
	public void handleCar(int lastStep) {
		if (STEP_HANDLE_HEAD <= lastStep) {
			System.out.println("Handle car's head.");
		}

		if (carHandler != null) {
			carHandler.handleCar(lastStep);
		}
	}
}
