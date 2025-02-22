package guru.springframework.sfgpetclinic.controllers;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;

@Controller
public class VisitController {
	private final VisitService visitService;
	private final PetService petService;

	public VisitController(VisitService visitService, PetService petService) {
		super();
		this.visitService = visitService;
		this.petService = petService;
	}

	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");

		dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDate.parse(text));
			}
		});
	}

	@ModelAttribute("visit")
	public Visit loadPetWithVisits(@PathVariable Long petId, Model model) {
		Pet pet = petService.findById(petId);
		model.addAttribute("pet", pet);
		Visit visit = new Visit();
		pet.getVisits().add(visit);
		visit.setPet(pet);
		return visit;
	}

	@GetMapping("/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable Long petId, Model model) {
		return "pets/createOrUpdateVisitForm";
	}

	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result, @PathVariable Long ownerId) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		} else {
			visitService.save(visit);
			return "redirect:/owners/" + ownerId;
		}

	}
}
