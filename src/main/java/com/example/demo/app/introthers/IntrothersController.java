package com.example.demo.app.introthers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Introthers;
import com.example.demo.service.IntrothersService;

@Controller
@RequestMapping("/introthers")
public class IntrothersController {

//	これでIntrothersServiceクラスの使用できる。
	private final IntrothersService introthersService;

	@Autowired
	public IntrothersController(IntrothersService introthersService) {
		this.introthersService = introthersService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Introthers> list = introthersService.getAll();
		model.addAttribute("indexList", list);
		model.addAttribute("title", "Introthers Index");
		
//		ログ
		System.out.println("11111　/introtheresを通りました");
		return "introthers/index";
	}
	
	@GetMapping("/createUserForm")
	public String createUserForm(CreateUserForm createUserForm, Model model,
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "ユーザ登録フォーム画面");		

		return "introthers/createUserForm";
	}
	
	@PostMapping("/createUserForm")
	public String goBackCreateUserForm(CreateUserForm createUserForm, Model model) {
		model.addAttribute("title", "ユーザ登録フォーム 確認画面からの戻り");

		return "/introthers/createUserForm";
	}
	
//	  ユーザ登録　確認ボタン押下時の処理
	@PostMapping("/createUserConfirm")
	public String createUserConfirm(@Validated CreateUserForm createUserForm, Model model,
			BindingResult result) {
		if(result.hasErrors()) {
			model.addAttribute("title", "ユーザ登録フォーム画面　確認ボタン押下時のエラー戻り");
			return "introthers/createUserForm";
		}
		model.addAttribute("title", "ユーザ登録確認画面");
		
		return "introthers/createUserConfirm";
	}
	
//	  ユーザ詳細　詳細ボタン押下時の処理
	@GetMapping("/{id}")
	public String getUserDetail(CreateUserForm userForm, Model model, @PathVariable int id) {
		Introthers user = introthersService.getOneUser(id);
//		Entiryを取得（Optionalでラップ）
		Optional<Introthers> userOpt = introthersService.getUser(id);
//		CreateUserForm型へ詰め直し
		Optional<CreateUserForm> userFormOpt = userOpt.map(t -> makeUserForm(t));
//		userFormがnull出なければ中身を取り出す
		if(userFormOpt.isPresent()) {
			userForm = userFormOpt.get();
		}
		model.addAttribute("userForm", userForm);
		model.addAttribute("userId", id);
		model.addAttribute("title", "ユーザ詳細画面");
				
//		ここから下の5行は自分で書いたもの
//		createUserForm.setName(user.getName());
//		createUserForm.setComment(user.getComment());		
//		model.addAttribute("title", "ユーザ詳細画面");
//		model.addAttribute("userForm", createUserForm);
//		model.addAttribute("userId", userId);
		
//		ログ
		System.out.println("22222　/introthers/idを通りました");
		return "introthers/detailUserForm";
	}
	
//	  ユーザ登録　登録ボタン押下時の処理
	@PostMapping("/createUserComplete")
	public String createUserComplete(@Validated CreateUserForm createUserForm, Model model,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "ユーザ登録フォーム画面　完了ボタン押下時のエラー戻り");
			return "/introthers/createUserForm";
		}
//		Entityにフォームからのデータを詰め込む
		Introthers introthers = new Introthers();
		introthers.setName(createUserForm.getName());
		introthers.setComment(createUserForm.getComment());
		
//		テーブル更新処理
		introthersService.save(introthers);
		
		redirectAttributes.addFlashAttribute("complete", "登録しました！");
		
//		URLを指定
		return "redirect:/introthers";
	}
	
//	  ユーザ登録　ユーザ詳細確認画面の登録ボタン押下時の処理
	@PostMapping("/detailUserComplete/{id}")
	public String update(@Validated @ModelAttribute CreateUserForm userForm, Model model,
			BindingResult result,
			@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		
		if(!result.hasErrors()) {
//			CreateUserForm型データをIntrothers型に格納
			Introthers introthers = makeIntrothers(userForm, id);
//			テーブル更新処理
			introthersService.updateOneUser(introthers);
			redirectAttributes.addFlashAttribute("complete", "更新しました！");
//			return "redirect:/introthers/" + id;
//			ログ
			System.out.println("33333　/introthers/detailUserCompleteを通りました");
			return "redirect:/introthers/";
		} else {
			model.addAttribute("title", "更新エラー！");
			return "introthers/";
		}
		
//		ここから下の9行は自分で書いたもの
//		if(result.hasErrors()) {
//			model.addAttribute("title", " エラー　ユーザ詳細確認画面の登録ボタン押下から");
//			return "/introthers/detailUserForm";
//		}
//		Introthers introthers = new Introthers();
//		introthers.setName(createUserForm.getName());
//		introthers.setComment(createUserForm.getComment());
//		introthersService.updateOneUser(introthers);
//		return "redirect:/introthers";
	}
	
    /**
     * userOpt<Introthers型>のデータをuserForm<CreateUserForm型>に入れて返す
     * @param user
     * @return
     */
	private CreateUserForm makeUserForm(Introthers user) {
		
		CreateUserForm userForm = new CreateUserForm();
		
		userForm.setName(user.getName());
		userForm.setComment(user.getComment());
		
		return userForm;
	}
	
    /**
     * CreateUserForm型のuserFormのデータをIntrothers型に入れて返す
     * @param userForm
     * @param id 新規登録の場合は0を指定
     * @return
     */
	private Introthers makeIntrothers(CreateUserForm userForm, int id) {
		Introthers introthers = new Introthers();
		if(id != 0) {
			introthers.setId(id);
		}
		introthers.setName(userForm.getName());
		introthers.setComment(userForm.getComment());
		
		return introthers;
	}
	
}
