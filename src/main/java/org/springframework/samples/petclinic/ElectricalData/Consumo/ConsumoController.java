package org.springframework.samples.petclinic.ElectricalData.Consumo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
class ConsumoController {

	private final ConsumoRepository cr;

	private final PuntosRepository pr;

	@Autowired
	CSVServiceConsumo csvService;

	@Autowired
	CSVHelperConsumo csvHelper;

	public ConsumoController(ConsumoRepository cr, PuntosRepository pr) {
		this.cr = cr;
		this.pr = pr;
	}

	@GetMapping("/consumo.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects so it is simpler for Object-Xml mapping
		Page<Consumo> paginated = findPaginated(page);
		List<Consumo> paneles = new ArrayList<>();
		paneles.addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	/**
	 * Custom handler for displaying an pannel.
	 * @param CUPS the ID of the pannel to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/consumo/{CUPS}")
	public ModelAndView showOwner(@PathVariable("CUPS") String CUPS) {
		ModelAndView mav = new ModelAndView("panels/panelDetails");
		PuntosElectricos punto = this.pr.findByCups(CUPS).orElse(new PuntosElectricos());

		List<Consumo> listaConsumo = this.cr.findByPunto(punto);

		mav.addObject("consumos", listaConsumo);
		return mav;
	}

	private Page<Consumo> findPaginated(int page) {
		int pageSize = 30;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return cr.findAll(pageable);
	}

	private String addPaginationModel(int page, Page<Consumo> paginated, Model model) {

		List<Consumo> listConsumos = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("consumos", listConsumos);
		return "panels/consumosList";
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
