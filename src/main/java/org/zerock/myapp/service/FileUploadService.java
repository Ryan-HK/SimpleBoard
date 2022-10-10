package org.zerock.myapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.exception.ServiceException;

public interface FileUploadService {

	public abstract void fileUploadRegister(List<MultipartFile> uploadFiles, int bno) throws ServiceException;

} // end interface
