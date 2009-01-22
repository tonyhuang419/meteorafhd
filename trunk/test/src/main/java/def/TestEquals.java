package def;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *	 https://www6.software.ibm.com/developerworks/cn/education/java/j-lessismore/index.html
 */
public class TestEquals {

	class Clazz {
		private String x;

		@Override
		public boolean equals(Object obj) {
			//			if (this == obj) {
			//				return true;
			//			}
			//			if (obj == null || this.getClass() != obj.getClass()) {
			//				return false;
			//			}
			//			Clazz c = (Clazz) obj;
			//			return new EqualsBuilder().append(this.x, c.x).isEquals();
			return EqualsBuilder.reflectionEquals(this, obj);
		}

		@Override
		public int hashCode() {
//			return new HashCodeBuilder(11, 21).append(this.x) .toHashCode();
			return super.hashCode();
		}

		@Override
		public String toString() {
			//			return new ToStringBuilder(this).append("x", this.x).toString();
			return ToStringBuilder.reflectionToString(this);
		}
	}

	public void testEquals(){
		Clazz c1 =  new Clazz();
		Clazz c2 =  new Clazz();
		System.out.println(c1.hashCode());
		System.out.println(c2.hashCode());
		Clazz c3 = c2;
		System.out.println(c3.hashCode());

		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);

		System.out.println(c1.equals(c2));

		c1.x = "xxxx";
		System.out.println(c1.toString());
	}
	
	

	public void testEquals2() {
		Stu i = new Stu();
		Stu ii = i;
		System.out.println(i.hashCode());
		System.out.println(ii.hashCode());
		System.out.println(i.getI());
		System.out.println(ii.getI());
		i.setI(11);
		System.out.println(i.hashCode());
		System.out.println(ii.hashCode());
		System.out.println(i.getI());
		System.out.println(ii.getI());
	}
	

	public  void test(){
		this.testEquals();
	}

	public static void main(String[] args) {
		new TestEquals().testEquals2();
	}
}
