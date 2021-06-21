package com.pablo.repositories;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pablo.domain.Doctor;

@Repository
@Transactional
public class DoctorDAOImpl implements DoctorDAO {

	private SessionFactory sessionFactory;

	public DoctorDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Doctor> findByLocationAndSpecialty(String location, String specialty) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Doctor> query = session.getNamedQuery("findByLocationAndSpecialty");
		query.setParameter("location", location);
		query.setParameter("specialty", specialty);
		return query.getResultList();
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return null;
	}

}
