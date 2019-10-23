package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Pet;

class PetMapServiceTest {

	private PetMapService petMapService;

	private final Long petId = 1L;

	@BeforeEach
	void setUp() throws Exception {
		petMapService = new PetMapService();

		petMapService.save(Pet.builder().id(petId).build());
	}

	@Test
	void testFindAll() {
		Set<Pet> petSet = petMapService.findAll();

		assertEquals(1, petSet.size());
	}

	@Test
	void testFindByIdLong() {
		Pet pet = petMapService.findById(petId);

		assertEquals(petId, pet.getId());
	}

	@Test
	void testFindByNullId() {
		Pet pet = petMapService.findById(null);

		assertNull(pet);
	}

	@Test
	void testSavePetExistingId() {

		Long id = 2L;
		
		Pet pet2 = Pet.builder().id(id).build();
		
		Pet savedPet = petMapService.save(pet2);
		
		assertEquals(id, savedPet.getId());
	}



}
