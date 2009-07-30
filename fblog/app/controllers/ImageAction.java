package controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import models.Image;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import play.Logger;
import play.mvc.Controller;

import com.google.appengine.api.datastore.Blob;

public class ImageAction  extends Controller {

	public static void newImage(){
		render();
	}
	
	public static void save() {
		String info = "";
		String contentType = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iterator;
		try {
			request.contentType = "text/plain";
			iterator = upload.getItemIterator(request.originalResquest);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				if (!item.isFormField()) {
					if (item.getName().endsWith("gif")) {
						contentType = "image/GIF";
					} else if (item.getName().endsWith("jpg")) {
						contentType = "image/JPEG";
					} else {
						info = "Not support this file format.";
						render(info);
					}
					int len;
					byte[] buffer = new byte[8192];
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						out.write(buffer, 0, len);
					}
					if (out.size() > 990000) {
						info = "File is too big!";
						render(info);
					}
					Long imageStamp = new Date().getTime();
					Image image = new Image(imageStamp, contentType, new Blob(out.toByteArray()));
					image.save();
					info = "<img src=\"http://meteorafhd.appspot.com/show/%22%20+%20imageStamp%20+%20%22\">";
					render(info);
				}
			}
		} catch (FileUploadException e) {
			Logger.error(e, "Cannot open the image file!");
		} catch (IOException e) {
			Logger.error(e, "IO error when save a photo!");
		}
	} 


	public static void show(Long imageStamp) {
		List<Image> imageList = Image.findBy("imageStamp", imageStamp);
		if (null == imageList || 0 == imageList.size()) {
			Logger.error("No image found by imageStamp: " + imageStamp);
			return;
		}
		Image image = imageList.get(0);
		if (image != null) {
			response.contentType = image.contentType;
			try {
				response.out.write(image.image.getBytes());
			} catch (IOException e) {
				Logger.error(e, "IO error when show a photo: " + imageStamp);
			}
		}
	}

}
