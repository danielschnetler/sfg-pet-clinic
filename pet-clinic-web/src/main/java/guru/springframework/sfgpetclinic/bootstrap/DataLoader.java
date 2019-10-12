package guru.springframework.sfgpetclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.services.map.PetTypeService;
import guru.springframework.sfgpetclinic.services.map.VetServiceMap;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;
	private final SpecialtyService specialtyService;

	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypreService,
			SpecialtyService specialtyService) {
		super();
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypreService;
		this.specialtyService = specialtyService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		int count = petTypeService.findAll().size();
		if (count == 0) {
			loadData();
		}
					

	}

	private void loadData() {
		PetType dog = new PetType();
		dog.setName("Dog");
		PetType savedDogPetType = petTypeService.save(dog);

		PetType cat = new PetType();
		dog.setName("Cat");
		PetType savedCatPetType = petTypeService.save(cat);
		
		System.out.println("Added Pet Types");
		
		Specialty radiology =  new Specialty();
		radiology.setDescription("Radiology");
		specialtyService.save(radiology);
		
		Specialty surgery =  new Specialty();
		radiology.setDescription("Surgery");
		specialtyService.save(surgery);
		
		Specialty dentistry =  new Specialty();
		radiology.setDescription("Dentistry");
		specialtyService.save(dentistry);
		
		Owner owner1 = new Owner();
		owner1.setFirstName("Michael");
		owner1.setLastName("Weston");
		owner1.setAddress("123 Brickerel");
		owner1.setCity("Miami");
		owner1.setTelephone("123445567");
		
		ownerService.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Fiona");
		owner2.setLastName("Glenanne");
		owner2.setAddress("14 Brickerel");
		owner2.setCity("Miami");
		owner2.setTelephone("1234443127");
		
		Pet mikesPet = new Pet();
		mikesPet.setPetType(savedDogPetType);
		mikesPet.setOwner(owner1);
		mikesPet.setName("Pluto");
		mikesPet.setBirtDate(LocalDate.now());
		owner1.getPets().add(mikesPet);
		
		Pet fionasCat = new Pet();
		fionasCat.setPetType(savedCatPetType);
		fionasCat.setOwner(owner2);
		fionasCat.setName("Kitty");
		fionasCat.setBirtDate(LocalDate.now());
		owner2.getPets().add(fionasCat);
		
		ownerService.save(owner2);
		
		System.out.println("Loaded owners");
		
		Vet vet1 = new Vet();
		vet1.setFirstName("Sam");
		vet1.setLastName("Axe");
		vet1.getSpecialities().add(radiology);
		
		vetService.save(vet1);
		
		Vet vet2 = new Vet();
		vet2.setFirstName("Peter");
		vet2.setLastName("Pan");
		vet2.getSpecialities().add(dentistry);
		
		vetService.save(vet2);
		
		System.out.println("Loaded Vets...");
	}
}
