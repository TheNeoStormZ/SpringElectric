package org.springframework.samples.petclinic.ElectricalData;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVServicePuntos {

	@Autowired
	PuntosRepository repository;

	@Autowired
	CSVHelperPuntos csvHelper;

	public void save(MultipartFile file) {
		try {
			System.out.println("T1");
			List<PuntosElectricos> electricalPanels = csvHelper.csvElectricalPanels(file.getInputStream());
			repository.saveAll(electricalPanels);
		}
		catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<PuntosElectricos> getAllElectricalPanels() {
		return repository.findAll();
	}

}
