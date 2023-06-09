package org.springframework.samples.SpringElectric.ElectricalData.Potencias;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.samples.SpringElectric.ElectricalData.PuntosElectricos;

public interface PotenciaRepository extends MongoRepository<Potencia, BigInteger> {

	List<Potencia> findByPunto(PuntosElectricos punto);

}
