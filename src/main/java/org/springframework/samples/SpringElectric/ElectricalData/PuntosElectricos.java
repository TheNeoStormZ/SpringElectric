/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.SpringElectric.ElectricalData;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.samples.SpringElectric.model.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PuntosElectricos extends BaseEntity {

	@Indexed(unique = true)
	private String cups;

	private String distribuidor;

	private String codigoOperador;

	private String titularIdentificativo;

	private String titularNombre;

	private String titularDireccion;

	private Boolean viviendaHabitual;

	private String ubicacion;

	private String poblacion;

	private Integer codigoPostal;

	private String provincia;

	private String fechaAltaSuministro;

	private String tarifa;

	private Integer tensionSuministro;

	private Boolean discriminacionHoraria;

	private String tipoDiscriminacionHoraria;

	private Integer potenciaMaxBoletin;

	private Integer potenciaMaxActa;

	private String tipoPuntoMedida;

	private Boolean disponibilidadIcp;

	private String tipoPerfil;

	private Integer derechoExtension;

	private Integer derechoAcceso;

	private String propiedadEquipoMedida;

	private String propiedadIcp;

	private String fechaUltimoMovimientoContratacion;

	private String fechaUltimoCambioComercializador;

	private String fechaLimiteDerechosExtension;

	private String fechaUltimaLectura;

	private Integer impagos;

	private Boolean depositoGarantia;

	private String importeDepositoGarantia;

}
