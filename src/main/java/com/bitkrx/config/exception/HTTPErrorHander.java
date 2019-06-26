package com.bitkrx.config.exception;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.dao.ErrorDAO;
import com.bitkrx.config.vo.CmeExceptionVO;

public class HTTPErrorHander {
	
	@Autowired
	ErrorDAO errorDAO;
	
	/* 404 error */
	@ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView NoHandlerFoundException(NoHandlerFoundException e, Model model) {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		e.printStackTrace();
		CmeExceptionVO exceptionVO = new CmeExceptionVO();
		exceptionVO.setErrorStatusCode(String.valueOf(HttpStatus.NOT_FOUND));
		exceptionVO.setErrorMessage(e.getMessage());
		model.addAttribute("exceptionVO", exceptionVO);
		 
		model.addAttribute("resultMsg", "ERROR : " + exceptionVO.getErrorMessage());
		model.addAttribute("resultStrCode", "-1");
		model.addAttribute("resultCode", "-1");
		model.addAttribute("data", new HashMap());
		
        return mv;
    }
	
	@ExceptionHandler(value= {CmeApplicationException.class})
    public ModelAndView handleRuntimeException(CmeApplicationException e, Model model) {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		//e.printStackTrace();
		CmeExceptionVO exceptionVO = new CmeExceptionVO();
		
		
		if(e.getExceptVo() != null) {
			exceptionVO = e.getExceptVo();
		} else if(!e.getCustomMessage().equals("")){
			exceptionVO.setErrorStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
			exceptionVO.setErrorMessage(e.getCustomMessage());
		} else {
			exceptionVO.setErrorStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
			exceptionVO.setErrorMessage("CmeApplicationException!");
		}
		
		model.addAttribute("resultMsg", "ERROR : " + exceptionVO.getErrorMessage());
		model.addAttribute("resultStrCode", "-1");
		model.addAttribute("resultCode", "-1");
		model.addAttribute("data", new HashMap());
		
		errorDAO.prErrorLog(exceptionVO);
		
        return mv;
    }
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, Model model) {
		
		ModelAndView mv = new ModelAndView("jsonView");
		CmeResultVO vo = new CmeResultVO();
		//e.printStackTrace();
		
		CmeExceptionVO exceptionVO = new CmeExceptionVO();
		exceptionVO.setErrorStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
		exceptionVO.setErrorMessage(e.getMessage());
		vo.setResultMsg(e.getMessage());
		model.addAttribute("resultCode", vo.getResultCode());
		model.addAttribute("resultStrCode1", e.getMessage());
		model.addAttribute("resultStrCode", "-1");
		model.addAttribute("resultMsg", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR) + "ERROR");
		
		errorDAO.prErrorLog(exceptionVO);
        
        return mv;
    }
	
	
}
