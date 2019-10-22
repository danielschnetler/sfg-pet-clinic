package guru.springframework.sfgpetclinic.formatters;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Component
public class PetTypeFormatter implements Formatter<PetType> {
	
	private final PetTypeService petTypeService;

	public PetTypeFormatter(PetTypeService petTypeService) {
		super();
		this.petTypeService = petTypeService;
	}

	@Override
	public String print(PetType object, Locale locale) {
		return object.getName();
	}

	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		Set<PetType> findPetTypes = petTypeService.findAll();
		for (PetType petType : findPetTypes) {
			if(petType.getName().equals(text)) {
				return petType;
			}
		}
		throw new ParseException("Type not found" + text, 0);
	}

}
