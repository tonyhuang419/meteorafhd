/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.fhdone.struts.action;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.fhdone.struts.form.UploadForm;

public class UploadAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UploadForm uploadForm = (UploadForm) form;// TODO Auto-generated method stub


		String dir = servlet.getServletContext().getRealPath("/upload");
		FormFile file = uploadForm.getFile();
		if(file==null){
			return mapping.findForward("s");
		}

		String fname = file.getFileName();
		String size = Integer.toString(file.getFileSize())+"bytes";
		int bytesRead = 0;
		byte[] buffer = new  byte[8192];
		try{
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir+"/"+fname);
			while ( (bytesRead = streamIn.read(buffer,0,8192))!=-1 ){
				streamOut.write(buffer, 0, bytesRead);
			}
			System.out.println("OK");
			streamOut.close();
			streamIn.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
}