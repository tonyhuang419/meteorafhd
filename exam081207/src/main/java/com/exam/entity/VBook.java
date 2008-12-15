package com.exam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *  这个是视图类
 *  	CREATE VIEW VBOOK as select * from book
 *  NOTICE： hibernate.cfg.xml 注释掉  <property name="hbm2ddl.auto">update</property>
 */
@Entity
@Table(name = "VBook")
public class VBook extends PriEntity implements Serializable {
	private static final long serialVersionUID = -2244324357919503071L;

	@Id
	@Column(name = "id", length = 20)
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
	private BigDecimal price;


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

}
