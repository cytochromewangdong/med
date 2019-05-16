package com.students.mum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.students.mum.service.BlockService;

@Controller
@RequestMapping("/block")
public class BlockController {

	@Autowired
	private BlockService blockService;
	
	@RequestMapping("/display")
	public String display(Model model) {
		model.addAttribute("blockList", blockService.findAll());
		return "blockDisplay";
	}
}
