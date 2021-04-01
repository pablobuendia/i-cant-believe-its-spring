package com.pablo.repositories;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.pablo.domain.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  public UserDAOImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * It uses namedQuery, which are static and more optimal than other query mechanisms
   */
  @Override
  public List<User> findByEmail(String email) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<User> query = session.getNamedQuery("findByEmail");
    query.setParameter("email", email);
    return query.list();
  }

  /**
   * Uses the criteria API to return a list of users based on the email and password
   * 
   * @param email
   * @param password
   * @return
   */
  @Override
  public List<User> findByEmailAndPassword(String email, String password) {

    // Create instance of Session from SessionFactory
    Session session = this.sessionFactory.getCurrentSession();

    // Create an instance of CriteriaBuilder by calling the getCriteriaBuilder() method
    CriteriaBuilder builder = session.getCriteriaBuilder();

    // Create an instance of CriteriaQuery by calling the CriteriaBuilder createQuery() method
    CriteriaQuery<User> criteria = builder.createQuery(User.class);

    Root<User> root = criteria.from(User.class);
    criteria.select(root).where(builder.and(builder.equal(root.get("email"), email)),
        builder.equal(root.get("password"), password));

    // Create an instance of Query by calling the Session createQuery() method
    Query<User> query = session.createQuery(criteria);

    return query.getResultList();
  }

  @Override
  public User save(User user) {
    Session session = this.sessionFactory.openSession();
    session.save(user);
    session.close();
    return user;
  }
}
