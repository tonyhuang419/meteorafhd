package com.exam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.Min;

@Entity
@Table(name = "book")
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ShardBook extends PriEntity implements Serializable {

	private static final long serialVersionUID = 222203029629458824L;

	@Id
    @GenericGenerator(name="ShardBook", strategy="org.hibernate.shards.id.ShardedUUIDGenerator")
	private BigInteger  id;

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

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
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

	public Long getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Long quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
