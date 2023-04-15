package org.springframework.samples.petclinic.ElectricalData.Consumo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVServiceConsumo {

	@Autowired
	ConsumoRepository repository;

	@Autowired
	CSVHelperConsumo csvHelper;

	public void save(MultipartFile file) throws NumberFormatException, ParseException {
		try {
			List<Consumo> electricalPanels = csvHelper.csvConsumo(file.getInputStream());
			repository.saveAll(electricalPanels);
		}
		catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<Consumo> getAllConsumos() {
		return repository.findAll();
	}

}
