package com.stylefeng.guns.modular.school.request;

import org.springframework.web.multipart.MultipartFile;

public class  FileBucket {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "FileBucket [file=" + file + "]";
	}
} 
