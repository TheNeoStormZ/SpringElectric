package org.springframework.samples.petclinic.owner;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {

	@Autowired
	ElectricalPannelRepository repository;

	@Autowired
	CSVHelper csvHelper;

	public void save(MultipartFile file) {
		try {
			List<ElectricalPanel> electricalPanels = csvHelper.csvElectricalPanels(file.getInputStream());
			repository.saveAll(electricalPanels);
		}
		catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<ElectricalPanel> getAllElectricalPanels() {
		return repository.findAll();
	}

}
