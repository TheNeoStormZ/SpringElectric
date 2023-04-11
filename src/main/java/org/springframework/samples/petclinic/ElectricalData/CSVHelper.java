package org.springframework.samples.petclinic.ElectricalData;

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

	String[] HEADERS = { "CUPS", "DISTRIBUIDOR", "CODIGO OPERADOR", "TITULAR IDENTIFICATIVO", "TITULAR NOMBRE",
			"TITULAR DIRECCION", "VIVIENDA HABITUAL", "UBICACION", "POBLACION", "CODIGO POSTAL", "PROVINCIA",
			"FECHA ALTA SUMINISTRO", "TARIFA", "TENSION SUMINISTRO (V)", "DISCRIMINACION HORARIA",
			"TIPO DISCRIMINACION HORARIA", "POTENCIA MAX BOLETIN (kW)", "POTENCIA MAX ACTA (kW)", "TIPO PUNTO MEDIDA",
			"DISPONIBILIDAD ICP", "TIPO PERFIL", "DERECHO EXTENSION (kW)", "DERECHO ACCESO (kW)",
			"PROPIEDAD EQUIPO MEDIDA", "PROPIEDAD ICP", "FECHA ULTIMO MOVIMIENTO DE CONTRATACION",
			"FECHA ULTIMO CAMBIO COMERCIALIZADOR", "FECHA LIMITE DERECHOS EXTENSION", "FECHA ULTIMA LECTURA", "IMPAGOS",
			"DEPOSITO DE GARANTIA", "IMPORTE DEPOSITO DE GARANTIA" };

	public CSVHelper() {

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

	public List<PuntosElectricos> csvElectricalPanels(InputStream is) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
			.setHeader(HEADERS)
			.setDelimiter(";")
			.setTrim(true)
			.setSkipHeaderRecord(true)
			.build();
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "Windows-1252"));
				CSVParser csvParser = new CSVParser(fileReader, csvFormat);) {

			List<PuntosElectricos> electricalPanels = new ArrayList<PuntosElectricos>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			System.out.println("T2");
			for (CSVRecord csvRecord : csvRecords) {
				System.out.println(csvRecord.toString());
				PuntosElectricos electricalPanel = new PuntosElectricos(csvRecord.get("CUPS"),
						csvRecord.get("DISTRIBUIDOR"), csvRecord.get("CODIGO OPERADOR"),
						csvRecord.get("TITULAR IDENTIFICATIVO"), csvRecord.get("TITULAR NOMBRE"),
						csvRecord.get("TITULAR DIRECCION"), Boolean.parseBoolean(csvRecord.get("VIVIENDA HABITUAL")),
						csvRecord.get("UBICACION"), csvRecord.get("POBLACION"),
						converNumber(csvRecord.get("CODIGO POSTAL").replace(".", "")), csvRecord.get("PROVINCIA"),
						csvRecord.get("FECHA ALTA SUMINISTRO"), csvRecord.get("TARIFA"),
						converNumber(csvRecord.get("TENSION SUMINISTRO (V)").replace(".", "")),
						Boolean.parseBoolean(csvRecord.get("DISCRIMINACION HORARIA")),
						csvRecord.get("TIPO DISCRIMINACION HORARIA"),
						converNumber(csvRecord.get("POTENCIA MAX BOLETIN (kW)").replace(".", "")),
						converNumber(csvRecord.get("POTENCIA MAX ACTA (kW)").replace(".", "")),
						csvRecord.get("TIPO PUNTO MEDIDA"), Boolean.parseBoolean(csvRecord.get("DISPONIBILIDAD ICP")),
						csvRecord.get("TIPO PERFIL"),
						converNumber(csvRecord.get("DERECHO EXTENSION (kW)").replace(".", "")),
						converNumber(csvRecord.get("DERECHO ACCESO (kW)").replace(".", "")),
						csvRecord.get("PROPIEDAD EQUIPO MEDIDA"), csvRecord.get("PROPIEDAD ICP"),
						csvRecord.get("FECHA ULTIMO MOVIMIENTO DE CONTRATACION"),
						csvRecord.get("FECHA ULTIMO CAMBIO COMERCIALIZADOR"),
						csvRecord.get("FECHA LIMITE DERECHOS EXTENSION"), csvRecord.get("FECHA ULTIMA LECTURA"),
						converNumber(csvRecord.get("IMPAGOS").replace(".", "")),
						Boolean.parseBoolean(csvRecord.get("DEPOSITO DE GARANTIA")),
						(csvRecord.get("IMPORTE DEPOSITO DE GARANTIA")));

				electricalPanels.add(electricalPanel);
			}
			return electricalPanels;

		}
		catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}