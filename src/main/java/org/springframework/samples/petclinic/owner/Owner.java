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
package org.springframework.samples.petclinic.owner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.samples.petclinic.model.Person;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Oliver Drotbohm
 */
@Document
public class Owner extends Person {

	@NotEmpty
	private String address;

	@NotEmpty
	private String city;

	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @JoinColumn(name = "owner_id")
	// @OrderBy("name")
	@DBRef
	private List<ElectricalPanel> electricalPanels = new ArrayList<>();

	public Owner(String string) {
		this.setFirstName(string);
	}

	public Owner() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<ElectricalPanel> getElectricalPanels() {
		return this.electricalPanels;
	}

	public void addElectricalPanel(ElectricalPanel electricalPanel) {
		if (electricalPanel.isNew()) {
			getElectricalPanels().add(electricalPanel);
		}
	}

	/**
	 * Return the Pet with the given id, or null if none found for this Owner.
	 * @param name to test
	 * @return a pet if pet id is already in use
	 */
	public ElectricalPanel getElectricalPanel(BigInteger id) {
		for (ElectricalPanel electricalPanel : getElectricalPanels()) {
			if (!electricalPanel.isNew()) {
				BigInteger compId = electricalPanel.getId();
				if (compId.equals(id)) {
					return electricalPanel;
				}
			}
		}
		return null;
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return a pet if pet name is already in use
	 */
	public ElectricalPanel getElectricalPanel(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (ElectricalPanel electricalPanel : getElectricalPanels()) {
			if (!ignoreNew || !electricalPanel.isNew()) {
				String compName = electricalPanel.getName();
				compName = compName == null ? "" : compName.toLowerCase();
				if (compName.equals(name)) {
					return electricalPanel;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("address", this.address)
			.append("city", this.city)
			.append("telephone", this.telephone)
			.toString();
	}

	/**
	 * Adds the given {@link Visit} to the {@link Pet} with the given identifier.
	 * @param petId the identifier of the {@link Pet}, must not be {@literal null}.
	 * @param visit the visit to add, must not be {@literal null}.
	 */

	// public Owner addVisit(BigInteger petId, Visit visit) {

	// Assert.notNull(petId, "Pet identifier must not be null!");
	// Assert.notNull(visit, "Visit must not be null!");

	// Pet pet = getPet(petId);

	// Assert.notNull(pet, "Invalid Pet identifier!");

	// pet.addVisit(visit);

	// return this;
	// }

}
