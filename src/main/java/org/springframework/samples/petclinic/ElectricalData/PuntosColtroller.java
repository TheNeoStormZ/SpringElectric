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
package org.springframework.samples.petclinic.ElectricalData;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PuntosColtroller {

	private static final String VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM = "electricalPanels/createOrUpdateElectricalPannelForm";

	private final PuntosRepository pannels;

	public PuntosColtroller(PuntosRepository pannels) {
		this.pannels = pannels;
	}

	// @ModelAttribute("types")
	// public Collection<ElectricalPanelType> populateElectricalPanelTypes() {
	// return this.owners.findElectricalPanelTypes();
	// }

	@ModelAttribute("ElectricalPanel")
	public PuntosElectricos findElectricalPanel(@PathVariable("ownerId") BigInteger ownerId,
			@PathVariable(name = "ElectricalPanelId", required = false) BigInteger ElectricalPanelId) {
		return ElectricalPanelId == null ? new PuntosElectricos()
				: pannels.findById(ElectricalPanelId).orElse(new PuntosElectricos());
	}

	@InitBinder("ElectricalPanel")
	public void initElectricalPanelBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PuntosValidator());
	}

	@GetMapping("/electricalPanel/new")
	public String initCreationForm(ModelMap model) {
		PuntosElectricos electricalPannel = new PuntosElectricos();
		model.put("electricalPanel", electricalPannel);
		return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/electricalPanel/new")
	public String processCreationForm(@Valid PuntosElectricos electricalPannel, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(electricalPannel.getCups()) && electricalPannel.isNew()) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		if (result.hasErrors()) {
			model.put("ElectricalPanel", electricalPannel);
			return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
		}
		this.pannels.save(electricalPannel);
		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/electricalPanels/{ElectricalPanelId}/edit")
	public String initUpdateForm(@PathVariable("ElectricalPanelId") BigInteger ElectricalPanelId, ModelMap model) {
		PuntosElectricos electricalPannel = pannels.findById(ElectricalPanelId).orElse(new PuntosElectricos());
		model.put("electricalPanel", electricalPannel);
		return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/electricalPanels/{ElectricalPanelId}/edit")
	public String processUpdateForm(@Valid PuntosElectricos electricalPanel, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("electricalPanel", electricalPanel);
			return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
		}

		this.pannels.save(electricalPanel);
		return "redirect:/owners/{ownerId}";
	}

}
