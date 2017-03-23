package com.csc.team2.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import com.csc.team2.model.File;
import com.csc.team2.model.Medicine;
import com.csc.team2.model.Treatment;
import com.csc.team2.model.TreatmentDetail;
import com.csc.team2.service.FileServiceImpl;
import com.csc.team2.service.TreatmentServiceIplm;

@RestController
public class FileController {
	
	@Autowired
	FileServiceImpl fileService;
	@Autowired
	TreatmentServiceIplm tretmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<?> createMedicine(@RequestBody File file,@RequestParam("file") MultipartFile image,UriComponentsBuilder ucBuilder){
		
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Medicine/{id}").buildAndExpand(file.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadMultipleFileHandler(@RequestParam(value = "files", required = false) MultipartFile mfile,
			@RequestParam("treatmentId") String treatmentId){
		Treatment treatment = getTreatmentById(Integer.parseInt(treatmentId));
		File file = new File();
			try {

				file.setTreatmentId(treatment);
				file.setImage(mfile.getBytes());
				fileService.saveFile(file);
				logger.info("You successfully uploaded file");
			} catch (IOException e) {
				logger.info("can not store file");
				e.printStackTrace();
			}
		
		
		
		return "";
		
	}
	
	public Treatment getTreatmentById(int id){
		return tretmentService.findById(id);
	}
	
	
	
////===================Delete file===================================
	@RequestMapping(value="/file/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMedicine(@PathVariable("id") int id){
		logger.info("Fetch & delete medicine with id {}", id);
		File file = fileService.findById(id);
		if(file==null){
			logger.info("unable to delete medicine with id {}, not found",id);
		}
		fileService.deleteFileById(id);
		return new ResponseEntity<Medicine>(HttpStatus.NO_CONTENT);
	}
	
//	@RequestMapping(value = "/upload" , method = RequestMethod.POST)
//	public void upload(HttpServletRequest request) {
//
//	    //org.springframework.web.multipart.MultipartHttpServletRequest
//	    MultipartHttpServletRequest mRequest;
//	    mRequest = (MultipartHttpServletRequest) request;
//	    File file = new File();
//	    		
//	    Iterator<String> itr = mRequest.getFileNames();
//	    while (itr.hasNext()) {
//	        //org.springframework.web.multipart.MultipartFile
//	        MultipartFile mFile = mRequest.getFile(itr.next());
//	        try {
//	        	file.setTreatmentdtId(treatmentdtId);
//				file.setImage(mFile.getBytes());
//				fileService.saveFile(file);
//				logger.info("You successfully uploaded file");
//			} catch (IOException e) {
//				logger.info("can not store file");
//				e.printStackTrace();
//			}
//	    }
//	}
//	
//	private byte[] getImageByteArray(final InputStream inputStream) {
//	     byte images[] = null;
//	   try{
//	       final BufferedImage image = ImageIO.read(inputStream);
//	            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	       ImageIO.write(image, "jpeg", baos);
//	       images = baos.toByteArray();
//	      }catch (final Exception e) {
//	        e.printStackTrace();
//	    }
//	   return images;
//	 }
}
