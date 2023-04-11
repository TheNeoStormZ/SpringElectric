package org.springframework.samples.petclinic.ElectricalData;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PuntosRepository extends MongoRepository<PuntosElectricos, BigInteger> {

	Optional<PuntosElectricos> findByCups(String cups);

}
