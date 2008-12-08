package partner.chainOfResponsibility.car;

public class CarTailHandler extends CarHandler {

	@Override
	public void handleCar(int lastStep) {
		if (STEP_HANDLE_TAIL <= lastStep) {
			System.out.println("Handle car's tail.");
		}

		if (carHandler != null) {
			carHandler.handleCar(lastStep);
		}
	}

}
