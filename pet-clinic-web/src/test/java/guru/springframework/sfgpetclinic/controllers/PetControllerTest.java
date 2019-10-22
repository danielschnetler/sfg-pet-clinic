package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

	@Mock
	PetService petService;

	@Mock
	OwnerService ownerService;

	@Mock
	PetTypeService petTypeService;

	@InjectMocks
	PetController petController;

	MockMvc mockMvc;

	Owner owner;
	Set<PetType> petTypes;

	@BeforeEach
	void setUp() throws Exception {
		owner = Owner.builder().id(1L).build();

		petTypes = new HashSet<>();
		petTypes.add(PetType.builder().id(1L).build());
		petTypes.add(PetType.builder().id(2L).build());

		mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
	}

	@Test
	void testInitCreateForm() throws Exception {
		when(petTypeService.findAll()).thenReturn(petTypes);
		when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("types"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
				.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"));
	}
	
	@Test
	void testProcessCreateForm() throws Exception {
		when(petTypeService.findAll()).thenReturn(petTypes);
		when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/new"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
			
		verify(petService, times(1)).save(ArgumentMatchers.any());
	}
	
	@Test
	void testInitUpdateForm() throws Exception {
		when(petTypeService.findAll()).thenReturn(petTypes);
		when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
		when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(Pet.builder().id(1L).build());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pet/1/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("types"))
				.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"));		
	}
	
	@Test
	void testProcessUpdateForm() throws Exception {
		when(petTypeService.findAll()).thenReturn(petTypes);
		when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pet/1/edit"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
		
		verify(petService, times(1)).save(ArgumentMatchers.any());
	}

}
