package controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import models.Photo;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import play.Logger;
import play.mvc.Controller;

import com.google.appengine.api.datastore.Blob;

public class PhotoAction  extends Controller {

	public static void index(){
		List<Photo> photos = Photo.findAll();
		render(photos);
	}
	
	public static void newPhoto(){
		render();
	}
	
	public static void save() {
//		String info = "";
//		String contentType = null;
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		ServletFileUpload upload = new ServletFileUpload();
//		FileItemIterator iterator;
//		try {
//			request.contentType = "text/plain";
//			iterator = upload.getItemIterator(request.originalResquest);
//			while (iterator.hasNext()) {
//				FileItemStream item = iterator.next();
//				InputStream stream = item.openStream();
//				if (!item.isFormField()) {
//					if (item.getName().toLowerCase().endsWith("gif")) {
//						contentType = "image/GIF";
//					} else if (item.getName().toLowerCase().endsWith("jpg") || item.getName().toLowerCase().endsWith("jpeg") ) {
//						contentType = "image/JPEG";
//					} else {
////						info = "Not support this file format.";
//						index();
//					}
//					int len;
//					byte[] buffer = new byte[8192];
//					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
//						out.write(buffer, 0, len);
//					}
//					if (out.size() > 990000) {
////						info = "File is too big!";
//						index();
//					}
//					Long photoStamp = new Date().getTime();
//					Photo photo = new Photo(photoStamp, contentType, new Blob(out.toByteArray()));
//					photo.save();
////					info = "<img src=\"http://meteorafhd.appspot.com/show/%22%20+%20photoStamp%20+%20%22\">";
//					index();
//				}
//			}
//		} catch (FileUploadException e) {
//			Logger.error(e, "Cannot open the photo file!");
//		} catch (IOException e) {
//			Logger.error(e, "IO error when save a photo!");
//		}
	} 


	public static void show(Long id) {
//		List<Photo> photoList = Photo.findBy("id", id);
//		if (null == photoList || 0 == photoList.size()) {
//			Logger.error("No photo found by photoStamp: " + id);
//			return;
//		}
//		Photo photo = photoList.get(0);
//		if (photo != null) {
//			response.contentType = photo.contentType;
//			try {
//				response.out.write(photo.photo.getBytes());
//			} catch (IOException e) {
//				Logger.error(e, "IO error when show a photo: " + id);
//			}
//		}
	}
	
}
