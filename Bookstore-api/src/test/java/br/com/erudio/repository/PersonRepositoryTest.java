package br.com.erudio.repository;

import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    private Person person;


    /*Jogar na IA:
    *
    * Method 'setUp' annotated with '@BeforeEach' should be non-static
    * Non-static field 'person' cannot be referenced from a static context
    * */

    @BeforeEach
    void setUp() {
        // Cria um objeto Person preenchendo todos os campos obrigatórios
        person = new Person();
        person.setFirstName("Nikola");
        person.setLastName("Tesla");
        person.setGender("Male");
        person.setEnabled(true);
        person.setAddress("123 Main Street"); // ⚠ obrigatório
        // Campos opcionais podem ficar nulos
        person.setPhotoUrl(null);
        person.setProfileUrl(null);
    }

    @Test
    @Order(1)
    void findPeopleByName() {
        // Salva a pessoa no banco
        repository.save(person);

        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "firstName"));

        // Busca por nome
        var result = repository.findPeopleByName("iko", pageable);
        Person found = result.getContent().get(0);

        assertNotNull(found);
        assertNotNull(found.getId());
        assertEquals("Nikola", found.getFirstName());
        assertEquals("Tesla", found.getLastName());
        assertEquals("Male", found.getGender());
        assertTrue(found.getEnabled());
    }

    @Test
    @Order(2)
    void disablePerson() {
        // garante que a pessoa existe no banco
        Person savedPerson = repository.save(person);

        // desabilita
        repository.disablePerson(savedPerson.getId());

        // busca novamente
        Person updated = repository.findById(savedPerson.getId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada após desabilitar"));

        assertNotNull(updated);
        assertEquals("Nikola", updated.getFirstName());
        assertEquals("Tesla", updated.getLastName());
        assertEquals("Male", updated.getGender());
        assertFalse(updated.getEnabled());
    }
}