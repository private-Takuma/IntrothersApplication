package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
//	42番　この42番の記載でServiceクラスを使用することができるようになった。
	private final InquiryService inquiryService;
	
//	42番
	@Autowired
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");
		
		return "inquiry/index_boot";
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, 
			Model model,
//			フラッシュスコープを用いる際は下記を追記する必要がある。
//			下記の記載でフラッシュスコープの値をHTMLでレンダリングできるようになる。
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "Inquiry Form!!!!!");
		return "inquiry/form_boot";
	}
	
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form!!!!!!!");
		return "inquiry/form_boot";
	}
	
//	29番の動画
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form!!!!");
			return "inquiry/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "inquiry/confirm_boot";
	}
	
//	32番の動画
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "InquiryForm!!!!!!!!!!");
			return "inquiry/form_boot";
		}
		
//		inquiryFormからinquiryと言うEntityのクラスにデータを詰め替える必要がある。
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);
		
		redirectAttributes.addFlashAttribute("complete", "Registered!!!");
//		ここはhtmlファイルを指しているのではなく、URLを指している。
		return "redirect:/inquiry/form";
	}

}
