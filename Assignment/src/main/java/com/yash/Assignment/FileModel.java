package com.yash.Assignment;

import org.springframework.web.multipart.MultipartFile;

public class FileModel {

	private MultipartFile file;
	
	public FileModel() {
		System.out.println("filemodel called...");
	}

	public FileModel(MultipartFile file) {
		super();
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
