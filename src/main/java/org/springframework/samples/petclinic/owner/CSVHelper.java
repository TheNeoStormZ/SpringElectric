package org.springframework.samples.petclinic.owner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CSVHelper {

	public String TYPE = "text/csv";

	String[] HEADERs = { "_id", "ID", "NOMBRE", "DESCRIPCION", "BAJA" };

	OwnerRepository ow;

	public CSVHelper(OwnerRepository ow) {
		this.ow = ow;
	}

	public boolean hasCSVFormat(MultipartFile file) {

		if (!this.TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public List<ElectricalPanel> csvElectricalPanels(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<ElectricalPanel> electricalPanels = new ArrayList<ElectricalPanel>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				ElectricalPanel electricalPanel = new ElectricalPanel(new BigInteger(csvRecord.get("_id")),
						csvRecord.get("NOMBRE"), csvRecord.get("DESCRIPCION"),
						Boolean.parseBoolean(csvRecord.get("BAJA")));
				BigInteger ownerId = new BigInteger(csvRecord.get("ID"));
				Owner o = ow.findById(ownerId).orElse(null);

				electricalPanels.add(electricalPanel);
			}
			return electricalPanels;

		}
		catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}