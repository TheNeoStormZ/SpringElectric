package org.springframework.samples.petclinic.owner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@Component
public class JSONHelper {

	public boolean hasJSONFormat(MultipartFile file) {
		// Verifica si el archivo tiene la extensión .json
		String extension = file.getOriginalFilename().split("\\.")[1];
		return extension.equals("json");
	}

	public List<ElectricalPanel> convertToElectricalPanel(MultipartFile file) throws IOException {
		Gson gson = new Gson();
		try {
			ElectricalPanelDTO[] object = gson.fromJson(new InputStreamReader(file.getInputStream()),
					ElectricalPanelDTO[].class);
			List<ElectricalPanelDTO> list = Arrays.asList(object);
			List<ElectricalPanel> result = list.stream()
				.map(dto -> new ElectricalPanel(dto.getID(), dto.getNOMBRE(), dto.getDESCRIPCION(), dto.getBAJA() == 1))
				.collect(Collectors.toList());
			return result;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

}