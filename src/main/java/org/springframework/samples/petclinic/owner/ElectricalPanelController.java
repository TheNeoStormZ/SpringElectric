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
class ElectricalPanelController {

	private static final String VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM = "electricalPanels/createOrUpdateElectricalPannelForm";

	private final OwnerRepository owners;

	private final ElectricalPannelRepository pannels;

	public ElectricalPanelController(OwnerRepository owners, ElectricalPannelRepository pannels) {
		this.owners = owners;
		this.pannels = pannels;
	}

	// @ModelAttribute("types")
	// public Collection<ElectricalPanelType> populateElectricalPanelTypes() {
	// return this.owners.findElectricalPanelTypes();
	// }

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") BigInteger ownerId) {
		return this.owners.findById(ownerId).orElse(null);
	}

	@ModelAttribute("ElectricalPanel")
	public ElectricalPanel findElectricalPanel(@PathVariable("ownerId") BigInteger ownerId,
			@PathVariable(name = "ElectricalPanelId", required = false) BigInteger ElectricalPanelId) {
		return ElectricalPanelId == null ? new ElectricalPanel()
				: this.owners.findById(ownerId).orElse(null).getElectricalPanel(ElectricalPanelId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("ElectricalPanel")
	public void initElectricalPanelBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ElectricalPanelValidator());
	}

	@GetMapping("/electricalPanel/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		ElectricalPanel electricalPannel = new ElectricalPanel();
		owner.addElectricalPanel(electricalPannel);
		model.put("electricalPanel", electricalPannel);
		return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
	}

	@GetMapping("/electricalPanel/populate")
	public String initPopulateForm(Owner owner) {
		ElectricalPanel electricalPannel = new ElectricalPanel();
		electricalPannel.setName("TEST");
		electricalPannel.setDescription("DESC");
		owner.addElectricalPanel(electricalPannel);
		owners.save(owner);

		return "redirect:/";
	}

	@PostMapping("/electricalPanel/new")
	public String processCreationForm(Owner owner, @Valid ElectricalPanel electricalPannel, BindingResult result,
			ModelMap model) {
		if (StringUtils.hasLength(electricalPannel.getName()) && electricalPannel.isNew()
				&& owner.getElectricalPanel(electricalPannel.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		electricalPannel.setIsNotClient(false);
		owner.addElectricalPanel(electricalPannel);
		if (result.hasErrors()) {
			model.put("ElectricalPanel", electricalPannel);
			return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
		}
		this.pannels.save(electricalPannel);
		this.owners.save(owner);
		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/electricalPanels/{ElectricalPanelId}/edit")
	public String initUpdateForm(Owner owner, @PathVariable("ElectricalPanelId") BigInteger ElectricalPanelId,
			ModelMap model) {
		ElectricalPanel electricalPannel = owner.getElectricalPanel(ElectricalPanelId);
		model.put("electricalPanel", electricalPannel);
		return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/electricalPanels/{ElectricalPanelId}/edit")
	public String processUpdateForm(@Valid ElectricalPanel electricalPanel, BindingResult result, Owner owner,
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("electricalPanel", electricalPanel);
			return VIEWS_ElectricalPanelS_CREATE_OR_UPDATE_FORM;
		}

		owner.addElectricalPanel(electricalPanel);
		this.pannels.save(electricalPanel);
		this.owners.save(owner);
		return "redirect:/owners/{ownerId}";
	}

}
