package fr.robot.twitterClient.web.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import fr.robot.twitterClient.service.QuestionService;
import fr.robot.twitterClient.service.exceptions.BusinessException;
import fr.robot.twitterClient.dataaccess.entity.Question;
import fr.robot.twitterClient.web.dto.QuestionDto;

@Controller
// @PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public ModelAndView questionHome(HttpServletRequest request, Principal currentUser) {
		ModelAndView mav = new ModelAndView("question");
		mav.addObject("questionList", questionService.getAll());
		return mav;
	} 

	@RequestMapping(value = { "/showAddQuestion", "/question/edit" }, method = RequestMethod.GET)
	public ModelAndView showAddQuestion(@RequestParam(required = false) Long id, Principal currentUser) {
		ModelAndView mav = new ModelAndView("question");
		QuestionDto qdto = new QuestionDto();
		try {
			if (id != null) {
				Question temp = questionService.get(id);
				qdto.setId(temp.getId());
				qdto.setQuestion(temp.getQuestion());
				qdto.setAnswer(temp.getAnswer());
			}
		} catch (Exception e) {
			mav.addObject("ERROR", "Something went wrong, please retry!");
		}

		mav.addObject("questionDto", qdto);
		return mav;
	}
	

	@RequestMapping(value = "/saveQuestion", method = RequestMethod.POST)
	public ModelAndView saveQuestion(@ModelAttribute("questionDto") @Valid final QuestionDto questionDto,
			final BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("question");
		if (result.hasErrors()) {
			return mav;
		} else {
			try {
				Question qsnInfo = new Question();
				if (questionDto.getId() != null) {
					qsnInfo = questionService.get(questionDto.getId());
				}
				qsnInfo.setQuestion(questionDto.getQuestion());
				qsnInfo.setAnswer(questionDto.getAnswer());
				questionService.saveOrUpdate(qsnInfo);
				mav.addObject("SUCCESS", "Record added/updated successfully. ");
			} catch (Exception e) {
				mav.addObject("ERROR", e.getMessage());
			}
		}
		return mav;
	}
	

	@RequestMapping(value = {"/question/delete" }, method = RequestMethod.GET)
	public ModelAndView deleteQuestion(@RequestParam(required = false) Long id, Principal currentUser, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/admin/questions");
				try {
					questionService.delete(id);
					redirectAttributes.addFlashAttribute("SUCCESS", "Record deleted successfully. ");
				} catch (BusinessException e) {
					redirectAttributes.addFlashAttribute("ERROR", "Could not delete record");
				}
		return mav;
	}
	

	@RequestMapping(value = "/question/downloadCSV")
	public void fooAsCSV(HttpServletResponse response) {
		String csvFileName = "questions.csv";
		response.setContentType("text/plain; charset=utf-8");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);
		try {
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			String[] header = { "Question", "Answer" };
			csvWriter.writeHeader(header);
			for (Question questionInfo : questionService.getAll()) {
				csvWriter.write(questionInfo, header);
			}
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
