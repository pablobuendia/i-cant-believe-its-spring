package com.pablo.repositories;

import java.util.List;
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

  @Override
  public List<User> findByEmail(String email) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<User> query = session.getNamedQuery("findByEmail");
    query.setParameter("email", email);
    return query.list();
  }
}
