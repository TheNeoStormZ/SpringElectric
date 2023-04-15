package org.springframework.samples.petclinic.ElectricalData.Potencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.ElectricalData.PuntosElectricos;
import org.springframework.samples.petclinic.ElectricalData.PuntosRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
class PotenciaController {

	private final PotenciaRepository potencia;

	private final PuntosRepository panelRepository;

	@Autowired
	CSVServicePotencia csvService;

	@Autowired
	CSVHelperPotencia csvHelper;

	public PotenciaController(PuntosRepository panelRepository, PotenciaRepository potencia) {
		this.panelRepository = panelRepository;
		this.potencia = potencia;
	}

	@GetMapping("/potencia/{CUPS}")
	public ModelAndView showOwner(@PathVariable("CUPS") String CUPS) {
		ModelAndView mav = new ModelAndView("panels/panelDetails");
		PuntosElectricos punto = this.panelRepository.findByCups(CUPS).orElse(new PuntosElectricos());
		List<Potencia> listaPotencia = this.potencia.findByPunto(punto);
		mav.addObject("potencia", listaPotencia);
		return mav;
	}

	private String addPaginationModel(int page, Page<Potencia> paginated, Model model) {

		List<Potencia> listPotencia = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("potencia", listPotencia);
		return "panels/potenciaList";
	}

	@PostMapping("/potencias/upload")
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
