package partner.chainOfResponsibility.car;

public class Client {
	public static void main(String[] args) {
		//工作流程1：先组装车头，然后是车身，车尾，最后是上色
		System.out.println("---workfolow1----");
		CarHandler carHandler1 = new CarHeadHandler();
		carHandler1.setNextCarHandler(
				new CarBodyHandler()).setNextCarHandler(
						new CarTailHandler()).setNextCarHandler(
								new CarColorHandler());

		carHandler1.handleCar(CarHandler.STEP_HANDLE_COLOR);


		//工作流程2：因为某种原因，我们需要先组装车尾，然后是车身，车头，最后是上色
		System.out.println("---workfolow2---");
		CarHandler carHandler2 = new CarTailHandler();
		carHandler2.setNextCarHandler(
				new CarBodyHandler()).setNextCarHandler(
						new CarHeadHandler()).setNextCarHandler(
								new CarColorHandler());

		carHandler2.handleCar(CarHandler.STEP_HANDLE_COLOR);
	}
}
