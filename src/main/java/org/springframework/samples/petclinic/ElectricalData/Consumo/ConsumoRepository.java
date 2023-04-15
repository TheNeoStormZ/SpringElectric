package org.springframework.samples.petclinic.ElectricalData.Consumo;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.samples.petclinic.ElectricalData.PuntosElectricos;

public interface ConsumoRepository extends MongoRepository<Consumo, BigInteger> {

	List<Consumo> findByPunto(PuntosElectricos punto);

}
