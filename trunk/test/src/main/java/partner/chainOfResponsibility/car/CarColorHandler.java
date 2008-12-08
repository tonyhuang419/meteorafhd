package partner.chainOfResponsibility.car;

public class CarColorHandler extends CarHandler {

	@Override
	public void handleCar(int lastStep) {
		if (STEP_HANDLE_COLOR == lastStep) {
			System.out.println("Handle car's color.");
		}


		if (carHandler != null) {
			carHandler.handleCar(lastStep);
		}
	}
}