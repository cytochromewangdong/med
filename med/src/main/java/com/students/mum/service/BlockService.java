package com.students.mum.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.StudentRepository;

@Service
@Transactional
public class BlockService {

	@Autowired
	private BlockRepository blockRepository;

	public Block saveBlock(long id, LocalDate startDate, LocalDate endDate) {
		Block block = blockRepository.getOne(id);
		block.setStartDate(startDate);
		block.setEndDate(endDate);
		// return blockRepository.save(block);
		return block;
	}

	public List<Block> findAll() {
		Order order = Order.desc("modifyDate");
		List<Block> list = blockRepository.findAll();// Sort.by(order,order)
		list.forEach(block -> block.getCourse());
		return list;
	}

	public void removeBlock(long id) {
		blockRepository.deleteById(id);
	}
}
