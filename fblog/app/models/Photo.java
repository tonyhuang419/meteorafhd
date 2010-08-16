package models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.jpa.JPASupport;

@Entity
public class Photo extends JPASupport {
	private static final long serialVersionUID = -5257165062306305774L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public Long photoStamp;

	@Enumerated
//	public Blob photo;

	public String contentType;

	public Photo(Long photoStamp, String contentType) {
		this.photoStamp = photoStamp;
		this.contentType = contentType;
//		this.photo = photo;

	}
}
