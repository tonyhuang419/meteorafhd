package partner.chainOfResponsibility.car;

abstract  class CarHandler {
	public static final int STEP_HANDLE_HEAD = 0;
	public static final int STEP_HANDLE_BODY = 0;
	public static final int STEP_HANDLE_TAIL = 0;
	public static final int STEP_HANDLE_COLOR = 3;

	protected CarHandler carHandler;

	public CarHandler setNextCarHandler(CarHandler carHandler) {
		this.carHandler = carHandler;

		return this.carHandler;
	}

	abstract public void handleCar(int lastStep);
}
