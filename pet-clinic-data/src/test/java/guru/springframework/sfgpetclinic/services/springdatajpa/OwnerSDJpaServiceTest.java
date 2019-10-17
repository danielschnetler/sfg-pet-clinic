package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRespository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
	
	private static final String LAST_NAME = "Smith";
	
	@Mock
	OwnerRepository ownerRepository;
	
	@Mock
	PetRespository petRepository;
	
	@Mock
	PetTypeRepository petTypeRepository;
	
	@InjectMocks
	OwnerSDJpaService service;
	
	private Owner returnOwner;

	@BeforeEach
	void setUp() throws Exception {
		returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
	}

	@Test
	void testFindAll() {
		Set<Owner> ownerSet = new HashSet();
		ownerSet.add(Owner.builder().id(1l).build());
		ownerSet.add(Owner.builder().id(2l).build());
		
		when(ownerRepository.findAll()).thenReturn(ownerSet);
		
		Set<Owner> owners = service.findAll();
		
		assertNotNull(owners);
		assertEquals(2, owners.size());		
	}

	@Test
	void testFindById() {
		
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
		
		Owner owner = service.findById(1L);
		
		assertNotNull(owner);
		assertEquals(LAST_NAME, owner.getLastName());		
	}
	
	@Test
	void testFindByIdNotFound() {
		
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		Owner owner = service.findById(1L);
		
		assertNull(owner);
	}

	@Test
	void testSave() {

		when(ownerRepository.save(any())).thenReturn(returnOwner);
		
		Owner owner = service.save(returnOwner);
		
		assertNotNull(owner);
		
		verify(ownerRepository).save(any());
		
	}

	@Test
	void testDelete() {
		service.delete(returnOwner);
		
		verify(ownerRepository).delete(any());
	}

	@Test
	void testDeleteById() {
		service.deleteById(1l);
		
		verify(ownerRepository).deleteById(anyLong());
	}

	@Test
	void testFindByLastName() {
				
		when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
		
		Owner smith = service.findByLastName(LAST_NAME);
		
		assertEquals(LAST_NAME, smith.getLastName());
		
		verify(ownerRepository).findByLastName(any());
	}

}
