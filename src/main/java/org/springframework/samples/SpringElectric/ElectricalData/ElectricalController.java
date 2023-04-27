package org.springframework.samples.SpringElectric.ElectricalData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.SpringElectric.ElectricalData.Consumo.Consumo;
import org.springframework.samples.SpringElectric.ElectricalData.Consumo.ConsumoRepository;
import org.springframework.samples.SpringElectric.ElectricalData.Potencias.Potencia;
import org.springframework.samples.SpringElectric.ElectricalData.Potencias.PotenciaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
class ElectricalController {

	private final PuntosRepository panelRepository;

	private final ConsumoRepository cr;

	private final PotenciaRepository pr;

	private static final String VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM = "electricalPanels/createOrUpdateElectricalPanelForm";

	@Autowired
	CSVServicePuntos csvService;

	@Autowired
	CSVHelperPuntos csvHelper;

	public ElectricalController(PuntosRepository panelRepository, ConsumoRepository cr, PotenciaRepository pr) {
		this.panelRepository = panelRepository;
		this.cr = cr;
		this.pr = pr;
	}

	@GetMapping("/panels.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects so it is simpler for Object-Xml mapping
		Page<PuntosElectricos> paginated = findPaginated(page);
		List<PuntosElectricos> paneles = new ArrayList<>();
		paneles.addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	/**
	 * Custom handler for displaying an pannel.
	 * @param CUPS the ID of the pannel to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/panel/{CUPS}")
	public ModelAndView showOwner(@PathVariable("CUPS") String CUPS) {
		ModelAndView mav = new ModelAndView("panels/panelDetails");
		PuntosElectricos punto = this.panelRepository.findByCups(CUPS).orElse(new PuntosElectricos());
		List<Consumo> consumos = this.cr.findByPunto(punto);
		List<Potencia> potencias = this.pr.findByPunto(punto);

		mav.addObject("punto", punto);
		mav.addObject("listConsumo", consumos);
		mav.addObject("listPotencia", potencias);

		return mav;
	}

	private Page<PuntosElectricos> findPaginated(int page) {
		int pageSize = 30;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return panelRepository.findAll(pageable);
	}

	private String addPaginationModel(int page, Page<PuntosElectricos> paginated, Model model) {

		List<PuntosElectricos> listPanels = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listPanels", listPanels);
		return "panels/panelList";
	}

	@GetMapping("/panel/{CUPS}/edit")
	public String initUpdateForm(@PathVariable("CUPS") String cups, ModelMap model) {
		PuntosElectricos electricalPanel = panelRepository.findByCups(cups).orElse(new PuntosElectricos());
		model.put("puntoElectrico", electricalPanel);
		return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
	}

	public void cppy(PuntosElectricos origen, PuntosElectricos destino) {
		destino.setDistribuidor(origen.getDistribuidor());
		destino.setCodigoOperador(origen.getCodigoOperador());
		destino.setTitularIdentificativo(origen.getTitularIdentificativo());
		destino.setTitularNombre(origen.getTitularNombre());
		destino.setTitularDireccion(origen.getTitularDireccion());
		destino.setViviendaHabitual(origen.getViviendaHabitual());
		destino.setUbicacion(origen.getUbicacion());
		destino.setPoblacion(origen.getPoblacion());
		destino.setCodigoPostal(origen.getCodigoPostal());
		destino.setProvincia(origen.getProvincia());
		destino.setFechaAltaSuministro(origen.getFechaAltaSuministro());
		destino.setTarifa(origen.getTarifa());
		destino.setTensionSuministro(origen.getTensionSuministro());
		destino.setDiscriminacionHoraria(origen.getDiscriminacionHoraria());
		destino.setTipoDiscriminacionHoraria(origen.getTipoDiscriminacionHoraria());
		destino.setPotenciaMaxBoletin(origen.getPotenciaMaxBoletin());
		destino.setPotenciaMaxActa(origen.getPotenciaMaxActa());
		destino.setTipoPuntoMedida(origen.getTipoPuntoMedida());
		destino.setDisponibilidadIcp(origen.getDisponibilidadIcp());
		destino.setTipoPerfil(origen.getTipoPerfil());
		destino.setDerechoExtension(origen.getDerechoExtension());
		destino.setDerechoAcceso(origen.getDerechoAcceso());
		destino.setPropiedadEquipoMedida(origen.getPropiedadEquipoMedida());
		destino.setPropiedadIcp(origen.getPropiedadIcp());
		destino.setFechaUltimoMovimientoContratacion(origen.getFechaUltimoMovimientoContratacion());
		destino.setFechaUltimoCambioComercializador(origen.getFechaUltimoCambioComercializador());
		destino.setFechaLimiteDerechosExtension(origen.getFechaLimiteDerechosExtension());
		destino.setFechaUltimaLectura(origen.getFechaUltimaLectura());
		destino.setImpagos(origen.getImpagos());
		destino.setDepositoGarantia(origen.getDepositoGarantia());
		destino.setImporteDepositoGarantia(origen.getImporteDepositoGarantia());
	}

	@PostMapping("/panel/{CUPS}/edit")
	public String processUpdateForm(@Valid PuntosElectricos electricalPanel, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("puntoElectrico", electricalPanel);
			return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
		}
		PuntosElectricos p = panelRepository.findByCups(electricalPanel.getCups()).orElse(new PuntosElectricos());

		cppy(electricalPanel, p);

		this.panelRepository.save(p);
		return "redirect:/panels.html";
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
