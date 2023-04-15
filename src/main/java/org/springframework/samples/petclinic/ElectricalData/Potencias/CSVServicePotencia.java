package org.springframework.samples.petclinic.ElectricalData.Potencias;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVServicePotencia {

	@Autowired
	PotenciaRepository repository;

	@Autowired
	CSVHelperPotencia csvHelper;

	public void save(MultipartFile file) {
		try {
			List<Potencia> potencias = csvHelper.csvPotencias(file.getInputStream());
			repository.saveAll(potencias);
		}
		catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<Potencia> getAllPotencias() {
		return repository.findAll();
	}

}
