package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IntrothersDao;
import com.example.demo.entity.Introthers;

@Service
public class IntrothersServiceImpl implements IntrothersService {

	private final IntrothersDao introthersDao;
	
	@Autowired	
	public IntrothersServiceImpl(IntrothersDao introthersDao) {
		this.introthersDao = introthersDao;
	}

	@Override
	public void save(Introthers introthers) {
		introthersDao.updateUser(introthers);
	}

	@Override
	public List<Introthers> getAll() {
		return introthersDao.getAll();
	}
	
	@Override
	public Introthers getOneUser(int userId) {
		return introthersDao.getOneUser(userId);
	}
	
	@Override
	public void updateOneUser(Introthers introthers) {
		introthersDao.updateOneUser(introthers);
	}
	
	@Override
	public Optional<Introthers> getUser(int userId) {
		return introthersDao.getUser(userId);
	}

}
