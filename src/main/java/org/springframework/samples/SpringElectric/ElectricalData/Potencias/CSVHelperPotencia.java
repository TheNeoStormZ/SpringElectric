package org.springframework.samples.SpringElectric.ElectricalData.Potencias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SpringElectric.ElectricalData.PuntosElectricos;
import org.springframework.samples.SpringElectric.ElectricalData.PuntosRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CSVHelperPotencia {

	public String TYPE = "text/csv";

	String[] HEADERS = { "CUPS", "PERIODO", "POTENCIA CONTRATADA (kW)" };

	private PuntosRepository pr;

	@Autowired
	public CSVHelperPotencia(PuntosRepository pr) {
		this.pr = pr;
	}

	public boolean hasCSVFormat(MultipartFile file) {

		if (!this.TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public Integer converNumber(String numb) {
		if (numb.isEmpty() || numb.isBlank()) {
			return null;
		}
		return Integer.valueOf(numb);
	}

	public Boolean converBoolean(String numb) {
		if (numb.compareToIgnoreCase("Si") == 0) {
			return true;
		}
		else if (numb.compareToIgnoreCase("No") == 0) {
			return false;
		}
		return null;
	}

	public List<Potencia> csvPotencias(InputStream is) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
			.setHeader(HEADERS)
			.setDelimiter(";")
			.setTrim(true)
			.setSkipHeaderRecord(true)
			.build();
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "Windows-1252"));
				CSVParser csvParser = new CSVParser(fileReader, csvFormat);) {

			List<Potencia> potencias = new ArrayList<Potencia>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {

				String cups = csvRecord.get("CUPS");

				PuntosElectricos punto = pr.findByCups(cups).orElse(new PuntosElectricos());

				System.out.println(csvRecord.toString());
				Potencia electricalPanel = new Potencia(punto, Integer.valueOf(csvRecord.get("PERIODO")),
						Double.valueOf(csvRecord.get("POTENCIA CONTRATADA (kW)").replace(",", ".")));

				potencias.add(electricalPanel);
			}
			return potencias;

		}
		catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}