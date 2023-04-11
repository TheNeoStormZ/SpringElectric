package org.springframework.samples.petclinic.ElectricalData;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PuntosRepository extends MongoRepository<PuntosElectricos, BigInteger> {

}
