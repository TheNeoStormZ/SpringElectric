package org.springframework.samples.petclinic.owner;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JSONService {

	@Autowired
	private ElectricalPannelRepository electricalPanelRepository;

	@Autowired
	private JSONHelper jsonHelper;

	public void save(MultipartFile file) throws IOException {
		// Convierte el archivo JSON en un objeto ElectricalPanel y lo guarda en la base
		// de datos
		List<ElectricalPanel> electricalPanel = jsonHelper.convertToElectricalPanel(file);
		electricalPanelRepository.saveAll(electricalPanel);
	}

}
