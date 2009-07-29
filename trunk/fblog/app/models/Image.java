package models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.jpa.JPASupport;

import com.google.appengine.api.datastore.Blob;

@Entity
public class Image extends JPASupport {
	private static final long serialVersionUID = -5257165062306305774L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public Long imageStamp;

	@Enumerated
	public Blob image;

	public String contentType;

	public Image(Long imageStamp, String contentType, Blob image) {
		this.imageStamp = imageStamp;
		this.contentType = contentType;
		this.image = image;

	}
}
