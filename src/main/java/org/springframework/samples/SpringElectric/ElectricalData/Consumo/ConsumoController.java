package org.springframework.samples.SpringElectric.ElectricalData.Consumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
class ConsumoController {

	@Autowired
	CSVServiceConsumo csvService;

	@Autowired
	CSVHelperConsumo csvHelper;

	public ConsumoController() {
	}

	@PostMapping("/consumos/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (csvHelper.hasCSVFormat(file)) {
			try {
				csvService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new String(message));
			}
			catch (Exception e) {
				System.out.println("ERROR: " + e.getLocalizedMessage());
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new String(message));
			}
		}

		message = "Please upload a valid file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String(message));
	}

}
