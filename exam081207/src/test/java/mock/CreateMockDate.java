package mock;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.exam.ExamBaseTest;
import com.exam.entity.Book;
import com.exam.entity.Customer;
import com.exam.entity.Employee;
import com.exam.entity.OrderInfo;
import com.exam.entity.Orders;
import com.exam.service.ICommonService;

@TransactionConfiguration(transactionManager = "transactionManager",
		defaultRollback = false)
		@Transactional 
		public class CreateMockDate extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;


	@Test
	public void createData(){
		Customer c = this.doGetCustomer();
		Book b = this.doGetBook();
		Employee e = this.doGetEmployee();
		Orders o = this.doGetOrders(b, c, e);
		OrderInfo oi = this.doGetOrderInfo(o, b);


		ClassValidator<Book> classValidator = new ClassValidator<Book> (Book.class);
		InvalidValue[] validMessages = classValidator.getInvalidValues(b);
		for (InvalidValue value : validMessages) {
			System.out.println("InvalidValue 的长度是:" + validMessages.length);
			System.out.println("验证消息是: " + value.getMessage());
			System.out.println("PropertyPath 是:" + value.getPropertyPath());
			System.out.println("PropertyName 是: " +value.getPropertyName());
			System.out.println("Value 是: " + value.getValue());
			System.out.println("Bean 是: "+ value.getBean());
			System.out.println("BeanClass 是:" + value.getBeanClass());
		}

		MockUser mu  = new MockUser();
		mu.mockEmployee();
		commonService.processSaveObj(b, "1", "session_employee_id");
		commonService.processSaveObj(c, "1", "session_employee_id");
		commonService.processSaveObj(e, "1", "session_employee_id");
		commonService.processSaveObj(o, "1", "session_employee_id");
		commonService.processSaveObj(oi, "1", "session_employee_id");

		commonService.save(o);
		commonService.save(oi);
	}




	private Customer doGetCustomer( ){
		Customer c = new Customer();
		c.setCity("city");
		c.setCreditcardinfo("creditcardinfo");
		c.setEmail("email");
		c.setPassword(DigestUtils.md5Hex("password"));
		c.setPhone("phone");
		c.setState("state");
		c.setStreet("street");
		c.setUsername("username");
		c.setZipcode("zipcode");
		return c;		 
	}

	private Book doGetBook(){
		Book b = new Book();
		b.setAuthorName("作者名称");
		b.setCategory("category");
		b.setEditior("editior");
		b.setIsbn("isbn");
		b.setPrice(new BigDecimal("-1")); //this value violate validator
		b.setPublisher("publisher");
		b.setQuantityInStock(100L);
		b.setTitle("title");
		b.setYear(new Date());
		return b;
	}

	private Employee doGetEmployee(){
		Employee e = new Employee();
		e.setJobNum("jobNum");
		e.setName("name");
		e.setPassword(DigestUtils.md5Hex("password"));
		e.setRole(1L);
		return e;
	}

	private Orders doGetOrders(Book b, Customer c,Employee e){
		Orders o = new Orders();
		o.setCustomerName("customerName");
		o.setOrderDate(new Date());
		o.setOrderNum("orderNum");
		o.setSendDate(new Date());
		o.setFkCustomerId(c);
		o.setFkEmployeeId(e);
		return o;
	}

	private OrderInfo doGetOrderInfo(Orders orders,Book bk){
		OrderInfo oi =  new OrderInfo();
		oi.setBookDealPrice(new BigDecimal("1234.12"));
		oi.setSendAddress("sendAddress");
		oi.setFkOrderId(orders);
		oi.setFkBookId(bk);
		return oi;
	}


}
