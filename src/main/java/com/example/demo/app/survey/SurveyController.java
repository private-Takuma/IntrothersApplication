package com.example.demo.app.survey;

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

import com.example.demo.entity.Survey;
import com.example.demo.service.SurveyService;

@Controller
@RequestMapping("/survey")
public class SurveyController {
	
	private final SurveyService surveyService;
	
	@Autowired
	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Survey> list = surveyService.getAll();
		
		model.addAttribute("surveyList", list);
		model.addAttribute("title", "survey Index!");
		
		return "survey/index";
	}

	@GetMapping("/form")
	public String form(SurveyForm suveyForm,
			Model model,
			@ModelAttribute("complete") String complete) {
//		ここにフラッシュスコープを用いるための@ModeAttributeのcomplete変数を記載しないといけない。
		model.addAttribute("title", "Survey Form");
		return "survey/form";
	}
	
	@PostMapping("/form")
	public String formGoBack(SurveyForm surveyForm,
			Model model) {
		model.addAttribute("title", "Survey Form!!");
		return "survey/form";
	}
	
	@PostMapping("/confirm")
	public String confirm(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form!");
			return "survey/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "survey/confirm";
	}
	
//	完了ボタン押下の際は二重クリック防止のためにリダイレクト処理を入れる必要がある。
	@PostMapping("/complete")
	public String complete(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form!!");
			return "survey/form";
		}
		
//		データベース処理をここに記載する。
		Survey survey = new Survey();
		survey.setAge(surveyForm.getAge());
		survey.setSatisfaction(surveyForm.getSatisfaction());
		survey.setComment(surveyForm.getComment());
		survey.setCreated(LocalDateTime.now());
		
//		Formから入力されたデータをデータベースに挿入する処理をServiceクラスに渡している。
		surveyService.save(survey);
		
		redirectAttributes.addFlashAttribute("complete", "Registered!");
		return "redirect:/survey/form";
	}

}
