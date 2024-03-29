package com.exam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.Min;

@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Book extends PriEntity implements Serializable {
	private static final long serialVersionUID = 3303303263841824748L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 20)
	private Long  id;

	@Column(name = "title", length = 100)
	private String title;

	@Column(name = "isbn", length = 100)
	private String isbn;

	@Column(name = "authorName", length = 100)
	private String authorName;

	@Column(name = "editior", length = 100)
	private String editior;

	@Temporal(TemporalType.DATE)
	@Column(name = "year" )
	private Date year;

	@Column(name = "category", length = 100)
	private String category;

	@Column(name = "publisher", length = 100)
	private String publisher;

	@Column(name = "quantityInStock", length = 20 )
	private Long quantityInStock;

	@Column(name = "price", length = 20 , scale = 2)
	@Min(value = 0 ,message="must large than 0")    
	//如果不设定message ， 默认为必须大于等于 0  reference resorces: org.hibernate.validator.resorces
	private BigDecimal price;

	@OneToMany(mappedBy="fkBookId" ,
			cascade=CascadeType.ALL, fetch = FetchType.LAZY )
	private Set<OrderInfo> orderInfo;

	public Book(){	}
	
	
	
	public Book(Long created, Date createdby, Long updated, Date updatedby,
			String isActive, Long id, String title, String isbn,
			String authorName, String editior, Date year, String category,
			String publisher, Long quantityInStock, BigDecimal price ) {
		super(created, createdby, updated, updatedby, isActive);
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.authorName = authorName;
		this.editior = editior;
		this.year = year;
		this.category = category;
		this.publisher = publisher;
		this.quantityInStock = quantityInStock;
		this.price = price;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getEditior() {
		return editior;
	}

	public void setEditior(String editior) {
		this.editior = editior;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Long quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public Set<OrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Set<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}

}
