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

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class PuntosValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		PuntosElectricos electricalPanel = (PuntosElectricos) obj;
		String name = electricalPanel.getCups();
		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", REQUIRED, "El nombre no debe estar vacio");
		}

		// description validation
		String description = electricalPanel.getTarifa();
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", REQUIRED, "La descripcion no debe estar vacia");
		}
	}

	/**
	 * This Validator validates *just* ElectricalPanel instances
	 */

	@Override
	public boolean supports(Class<?> clazz) {
		return PuntosElectricos.class.isAssignableFrom(clazz);
	}

}
