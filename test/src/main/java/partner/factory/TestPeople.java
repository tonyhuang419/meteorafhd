package partner.factory;

public class TestPeople {


	public static void main(String[] args) {
		People people = Factory.getPeople("partner.factory.Stu");
		people.say();
		people = Factory.getPeople("partner.factory.Teacher");
		people.say();
	}

}
