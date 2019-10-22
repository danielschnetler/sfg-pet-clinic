package guru.springframework.sfgpetclinic.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@RequestMapping("/owners")
@Controller
public class OwnerController {
	
	private final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		super();
		this.ownerService = ownerService;
	}

	@RequestMapping("/find")
	public String findOwners(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return "owners/findOwners";
	}
	
	@GetMapping("")
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		
		if(owner.getLastName() == null) {
			owner.setLastName("");
		}
		
		List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() +"%");
		
		if(results.isEmpty()) {
			result.rejectValue("lastname", "Not Found", "Not Found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			//1 owner found
			owner = results.iterator().next();
			return "redirect:owners/" + owner.getId();
		} else {
			//multiple owners found
			model.addAttribute("selections", results);
			return "owners/ownersList";
		}
		
		
	}
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(ownerService.findById(ownerId));
		return mav;
	}
	
	@GetMapping("/new")
	public String initCreateForm(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/new")
	public String processCreateForm(@Valid Owner owner, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" +savedOwner.getId();
		}		
		
	}
	
	@GetMapping("/{ownerId}/edit")
	public String initUpdateForm(@PathVariable Long ownerId, Model model) {
		model.addAttribute("owner", ownerService.findById(ownerId));
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/{ownerId}/edit")
	public String processUpdateForm(@PathVariable Long ownerId,@Valid Owner owner, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();
		}	
		
	}
}
