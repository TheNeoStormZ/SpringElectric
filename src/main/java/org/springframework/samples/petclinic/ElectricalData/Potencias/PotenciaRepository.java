package org.springframework.samples.petclinic.ElectricalData.Potencias;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.samples.petclinic.ElectricalData.PuntosElectricos;

public interface PotenciaRepository extends MongoRepository<Potencia, BigInteger> {

	List<Potencia> findByPunto(PuntosElectricos punto);

}
