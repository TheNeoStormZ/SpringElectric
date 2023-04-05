package org.springframework.samples.petclinic.owner;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ElectricalPanelDTO implements Serializable {

	private BigInteger _id;

	private BigInteger ID;

	private String NOMBRE;

	private String DESCRIPCION;

	private Integer BAJA;

}
