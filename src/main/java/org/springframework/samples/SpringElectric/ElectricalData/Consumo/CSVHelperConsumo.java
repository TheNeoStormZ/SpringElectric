package org.springframework.samples.SpringElectric.ElectricalData.Consumo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class CSVHelperConsumo {

	public String TYPE = "text/csv";

	String[] HEADERS = { "CUPS", "FECHA MEDIDA", "TIPO MEDIDA", "PERIODO", "CONSUMO" };

	private PuntosRepository pr;

	@Autowired
	public CSVHelperConsumo(PuntosRepository pr) {
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

	public List<Consumo> csvConsumo(InputStream is) throws NumberFormatException, ParseException {
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
			.setHeader(HEADERS)
			.setDelimiter(";")
			.setTrim(true)
			.setSkipHeaderRecord(true)
			.build();
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "Windows-1252"));
				CSVParser csvParser = new CSVParser(fileReader, csvFormat);) {

			List<Consumo> consumos = new ArrayList<Consumo>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {

				String cups = csvRecord.get("CUPS");

				System.out.println(csvRecord.toString());

				PuntosElectricos punto = pr.findByCups(cups).orElse(new PuntosElectricos());

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

				Consumo consumo = new Consumo(punto, formato.parse(csvRecord.get("FECHA MEDIDA")),
						String.valueOf("TIPO MEDIDA"), Integer.valueOf(csvRecord.get("PERIODO")),
						Double.valueOf(csvRecord.get("CONSUMO").replace(",", ".")));

				consumos.add(consumo);
			}
			return consumos;

		}
		catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}