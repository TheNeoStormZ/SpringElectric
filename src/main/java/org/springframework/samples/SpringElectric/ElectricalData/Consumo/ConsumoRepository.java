package org.springframework.samples.SpringElectric.ElectricalData.Consumo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.samples.SpringElectric.ElectricalData.PuntosElectricos;

public interface ConsumoRepository extends MongoRepository<Consumo, BigInteger> {

	List<Consumo> findByPunto(PuntosElectricos punto);

}
