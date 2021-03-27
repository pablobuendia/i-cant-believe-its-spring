package com.pablo.repositories;

import java.util.List;
import org.hibernate.SessionFactory;
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
    return this.sessionFactory.getCurrentSession().createQuery("from User u where u.email = :email")
        .setParameter("email", email).list();
  }
}
