package pe.gob.sunarp.generatorQR.GeneratorQR;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

@RestController
public class GeneratorQRController {
	
	@RequestMapping(value="/v1/generaBytesQR", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public byte[] generateBytesQR (HttpServletRequest servletRequest, @RequestBody String id, @RequestHeader Map<String, String> header ){
		
		
		StringBuffer sb = new StringBuffer("https://enlinea.sunarp.gob.pe/servicio/verCertificado/");
		
		sb.append(id);
		
		String coVeri = "2";
		
        try {
        	
        	//AV: Create QR image jpg 
        	QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(sb.toString(), com.google.zxing.BarcodeFormat.QR_CODE, 100, 100);
			
						
			//AV: Create temporary file
			//File f = File.createTempFile(coVeri.concat("_"), ".jpg",new File("F:/temp/QR/"));			
			//Path path = FileSystems.getDefault().getPath(f.getAbsolutePath());
			//MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);
					    
			
			//AV: Convert Image to ByteArray			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "JPG", bos);
		    byte [] img = bos.toByteArray();
		    
		    
		    return img;
			
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
		
        
		return null;
		
	}
	
	@GetMapping("/test/{nombre}")
	public String test(@PathVariable String nombre) {
		return "El microservices está funcionando " + nombre;
	}

}
