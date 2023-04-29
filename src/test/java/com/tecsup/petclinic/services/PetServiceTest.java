package com.tecsup.petclinic.services;


import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PetServiceTest {

    @Autowired
    private PetService petService;

    /**
     *
     */
    @Test
    public void testFindPetById() {

        long ID = 1;
        String NAME = "Leo";
        Pet pet = null;

        try {
            pet = petService.findById(ID);
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + pet);
        assertEquals(NAME, pet.getName());

    }

    /**
     *
     */
    @Test
    public void testFindPetByName() {

        String FIND_NAME = "Leo";
        int SIZE_EXPECTED = 1;

        List<Pet> pets = petService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByTypeId() {

        int TYPE_ID = 5;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = petService.findByTypeId(TYPE_ID);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByOwnerId() {

        int OWNER_ID = 10;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = petService.findByOwnerId(OWNER_ID);

        assertEquals(SIZE_EXPECTED, pets.size());

    }

    /**
     * To get ID generate , you need
     * setup in id primary key in your
     * entity this annotation :
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     */
    @Test
    public void testCreatePet() {

        String PET_NAME = "Ponky";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        Pet pet = new Pet(PET_NAME, 1, 1);

        Pet petCreated = petService.create(pet);

        log.info("PET CREATED :" + petCreated);

        assertNotNull(pet.getId());
        assertEquals(PET_NAME, pet.getName());
        assertEquals(OWNER_ID, pet.getOwnerId());
        assertEquals(TYPE_ID, pet.getTypeId());

    }


    /**
     *
     */
    @Test
    public void testUpdatePet() {

        String PET_NAME = "Bear";
        int OWNER_ID = 1;
        int TYPE_ID = 1;
        long create_id = -1;

        String UP_PET_NAME = "Bear2";
        int UP_OWNER_ID = 2;
        int UP_TYPE_ID = 2;

        Pet pet = new Pet(PET_NAME, OWNER_ID, TYPE_ID);

        // Create record
        log.info(">" + pet);
        Pet petCreated = petService.create(pet);
        log.info(">>" + petCreated);

        create_id = petCreated.getId();

        // Prepare data for update
        petCreated.setName(UP_PET_NAME);
        petCreated.setOwnerId(UP_OWNER_ID);
        petCreated.setTypeId(UP_TYPE_ID);

        // Execute update
        Pet upgradePet = petService.update(petCreated);
        log.info(">>>>" + upgradePet);

        //        ACTUAL       EXPECTED
        assertNotNull(create_id);
        assertEquals(create_id, upgradePet.getId());
        assertEquals(UP_PET_NAME, upgradePet.getName());
        assertEquals(UP_OWNER_ID, upgradePet.getTypeId());
        assertEquals(UP_TYPE_ID, upgradePet.getOwnerId());
    }

    /**
     *
     */
    @Test
    public void testDeletePet() {

        String PET_NAME = "Bird";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        Pet pet = new Pet(PET_NAME, OWNER_ID, TYPE_ID);
        pet = petService.create(pet);
        log.info("" + pet);

        try {
            petService.delete(pet.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            petService.findById(pet.getId());
            assertTrue(false);
        } catch (PetNotFoundException e) {
            assertTrue(true);

        }

    }
}
