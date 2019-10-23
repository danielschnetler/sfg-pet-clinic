package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

	@Mock
	VisitService visitService;

	@Mock
	PetService petService;

	@InjectMocks
	VisitController visitController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		visitController = new VisitController(visitService, petService);

		mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
	}

	@Test
	void testInitNewVisitForm() throws Exception {
		when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(Pet.builder().id(1L).build());

		mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/1/visits/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateVisitForm"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visit"));

		verifyZeroInteractions(visitService);
	}

	@Test
	void testProcessNewVisitForm() throws Exception {
		when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(Pet.builder().id(1L).build());

		mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/1/visits/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("date", "2018-11-11")
				.param("description", "Yet another visit"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));

		verify(visitService, times(1)).save(ArgumentMatchers.any());

	}

}
