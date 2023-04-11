package org.springframework.samples.petclinic.ElectricalData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
class UploadController {

	private final PuntosRepository pannelRepository;

	@Autowired
	CSVService csvService;

	@Autowired
	CSVHelper csvHelper;

	public UploadController(PuntosRepository pannelRepository) {
		this.pannelRepository = pannelRepository;
	}

	@GetMapping("/pannels.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects so it is simpler for Object-Xml mapping
		Page<PuntosElectricos> paginated = findPaginated(page);
		List<PuntosElectricos> paneles = new ArrayList<>();
		paneles.addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	private Page<PuntosElectricos> findPaginated(int page) {
		int pageSize = 30;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return pannelRepository.findAll(pageable);
	}

	private String addPaginationModel(int page, Page<PuntosElectricos> paginated, Model model) {

		List<PuntosElectricos> listPannels = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listPannels", listPannels);
		return "pannels/pannelList";
	}

	@PostMapping("/electricalPanels/upload")
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
