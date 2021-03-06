package fr.cances.steve.annuaire.spring.model.persistence.dao.jpa;

import fr.cances.steve.annuaire.spring.model.persistence.dao.DaoUtilisateur;
import fr.cances.steve.annuaire.spring.model.persistence.entity.Utilisateur;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA gérant l'entity {@link Utilisateur}.
 * 
 * @author Steve Cancès
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public class DaoUtilisateurJpa extends AbstractDaoJpa<Utilisateur, Long> implements DaoUtilisateur {

    /**
     * Constructeur par defaut.
     * 
     * @author Steve Cancès
     * @since 1.0.0
     */
    public DaoUtilisateurJpa() {
        super(Utilisateur.class);
    }

    @Override
    public Utilisateur findByUsername(final String username) {
        TypedQuery<Utilisateur> query = this.entityManager.createQuery("From Utilisateur u where u.username =:username", this.domainClass);
        query.setParameter("username", username);
        return this.getSingleResultOrNull(query);
    }

}
