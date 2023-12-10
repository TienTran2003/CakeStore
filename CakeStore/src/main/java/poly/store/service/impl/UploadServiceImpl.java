package poly.store.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import poly.store.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
	@Autowired
	ServletContext app;

	public File save(MultipartFile file, String folder) {
		File dir = new File(app.getRealPath("/user/" + folder));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// Keep the original filename without any modification
		String originalFilename = file.getOriginalFilename();
		// Create a unique name using the current timestamp and the original filename
		try {
			File savedFile = new File(dir, originalFilename);
			file.transferTo(savedFile);
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}