/**
 * 
 */
package fr.cances.steve.annuaire.spring.model.persistence.repositories.jpa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cances.steve.annuaire.spring.model.persistence.config.TestPersistenceConfig;
import fr.cances.steve.annuaire.spring.model.persistence.entities.Personne;
import fr.cances.steve.annuaire.spring.model.persistence.repositories.PersonneRepository;

/**
 * @author Steve Cancès
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class})
public class PersonneRepositoryJpaTest extends AbstractRepositoryJpaTest {

	@Autowired
	private PersonneRepository personneRepository;
	
	@Before
	public void before() {
		this.truncateSchemaPublic();
	}

	@Test
	public void testCreate() {
		assertEquals(0, this.personneRepository.count());
		Personne personne = new Personne();
		personne.setPrenom("Steve");
		personne.setNom("Cancès");
		this.personneRepository.create(personne);
		assertEquals(1, this.personneRepository.count());
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreate2() {
		assertEquals(0, this.personneRepository.count());
		Personne personne = new Personne();
		personne.setPrenom("Steve");
		personne.setNom(null);
		this.personneRepository.create(personne);
		assertEquals(0, this.personneRepository.count());
	}

}