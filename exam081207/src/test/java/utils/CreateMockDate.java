package utils;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.ExamBaseTest;
import com.exam.entity.Book;
import com.exam.entity.Customer;
import com.exam.entity.Employee;
import com.exam.entity.Orders;
import com.exam.service.ICommonService;

public class CreateMockDate extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;


	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(false);
	}
	
	public void testCreateData(){
		Customer c = this.doGetCustomer();
		Book b = this.doGetBook();
		Employee e = this.doGetEmployee();
		Orders o = this.doGetOrders(b, c, e);
		commonService.save(o);
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
		b.setPrice(new BigDecimal("100000.91"));
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
		o.setBookDealPrice(new BigDecimal("1234.12"));
		o.setCustomerName("customerName");
		o.setOrderDate(new Date());
		o.setOrderNum("orderNum");
		o.setSendAddress("sendAddress");
		o.setSendDate(new Date());
		o.setFkBookId(b);
		o.setFkCustomerId(c);
		o.setFkEmployeeId(e);
		return o;
	}
	
}
