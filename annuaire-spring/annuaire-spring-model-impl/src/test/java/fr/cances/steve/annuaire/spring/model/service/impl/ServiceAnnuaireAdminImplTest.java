/**
 * 
 */
package fr.cances.steve.annuaire.spring.model.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cances.steve.annuaire.spring.model.persistence.config.TestPersistenceConfig;
import fr.cances.steve.annuaire.spring.model.persistence.repositories.TelephoneRepository;
import fr.cances.steve.annuaire.spring.model.persistence.repositories.jpa.AbstractRepositoryJpaTest;
import fr.cances.steve.annuaire.spring.model.service.api.ServiceAnnuaire;
import fr.cances.steve.annuaire.spring.model.service.api.ServiceAnnuaireAdmin;
import fr.cances.steve.annuaire.spring.model.service.api.valueobjects.PersonneVo;
import fr.cances.steve.annuaire.spring.model.service.api.valueobjects.TelephoneVo;

/**
 * @author Steve Cancès
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class})
public class ServiceAnnuaireAdminImplTest extends AbstractRepositoryJpaTest {

	@Autowired
	private ServiceAnnuaire serviceAnnuaire;

	@Autowired
	private ServiceAnnuaireAdmin serviceAnnuaireAdmin;
	
	@Autowired
	private TelephoneRepository telephoneRepository;

	@Before
	public void before() {
		this.truncateSchemaPublic();
		PersonneVo personneVo = new PersonneVo();
		personneVo.setNom("Jobs");
		personneVo.setPrenom("Steve");
		this.serviceAnnuaireAdmin.createPersonne(personneVo);
	}
	
	private PersonneVo fakePersonneVo() {
		PersonneVo personneVo = new PersonneVo();
		personneVo.setId(1l);
		personneVo.setNom("Cancès");
		personneVo.setPrenom("Steve");
		Collection<TelephoneVo> telephonesVo = new ArrayList<>();
		personneVo.setTelephones(telephonesVo);
		TelephoneVo telephoneVo = new TelephoneVo();
		telephoneVo.setTelephone("0000000000");
		telephonesVo.add(telephoneVo);
		return personneVo;
	}

	@Test
	public void testCreatePersonne() {
		PersonneVo personneVo = fakePersonneVo();
		personneVo = this.serviceAnnuaireAdmin.createPersonne(personneVo);
		personneVo = this.serviceAnnuaire.getPersonne(personneVo.getId());
		assertEquals("Steve", personneVo.getPrenom());
		assertEquals("Steve", personneVo.getPrenom());
		assertEquals("Cancès", personneVo.getNom());
		for(TelephoneVo telephoneVo2 : personneVo.getTelephones()) {
			assertEquals("0000000000", telephoneVo2.getTelephone());
		}
	}

	@Test
	public void testDeletePersonne() {
		assertEquals(1, this.serviceAnnuaire.getAllPersonnes().size());
		PersonneVo personneVo = fakePersonneVo();
		personneVo = this.serviceAnnuaireAdmin.createPersonne(personneVo);
		assertEquals(2, this.serviceAnnuaire.getAllPersonnes().size());
		this.serviceAnnuaireAdmin.deletePersonne(personneVo.getId());
		assertEquals(1, this.serviceAnnuaire.getAllPersonnes().size());
		assertEquals(0, this.telephoneRepository.findAll().size());
	}

	@Test
	public void testEditPersonne() {
		PersonneVo personneVo = fakePersonneVo();
		personneVo = this.serviceAnnuaireAdmin.createPersonne(personneVo);
		assertEquals("Steve", personneVo.getPrenom());
		assertEquals("Cancès", personneVo.getNom());
		personneVo.setPrenom("Steeve");
		personneVo = this.serviceAnnuaireAdmin.editPersonne(personneVo.getId(), personneVo);
		assertEquals("Steeve", personneVo.getPrenom());
		personneVo.setNom("Jobs");
		personneVo = this.serviceAnnuaireAdmin.editPersonne(personneVo.getId(), personneVo);
		assertEquals("Jobs", personneVo.getNom());
	}
	
	@Test
	public void testEditPersonne2() {
		PersonneVo personneVo = fakePersonneVo();
		personneVo = this.serviceAnnuaireAdmin.createPersonne(personneVo);
		for(TelephoneVo telephoneVo : personneVo.getTelephones()) {
			assertEquals("0000000000", telephoneVo.getTelephone());
			telephoneVo.setTelephone("1111111111");
		}
		personneVo = this.serviceAnnuaireAdmin.editPersonne(personneVo.getId(), personneVo);
		for(TelephoneVo telephoneVo : personneVo.getTelephones()) {
			assertEquals("1111111111", telephoneVo.getTelephone());
		}
	}

}