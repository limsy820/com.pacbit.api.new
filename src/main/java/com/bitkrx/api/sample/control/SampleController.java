package com.bitkrx.api.sample.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitkrx.api.auth.vo.CmeLoginVO;
import com.bitkrx.api.mail.service.MailApiService;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.api.sample.vo.bataMailVO;
import com.bitkrx.api.trade.service.TradeService;
import com.bitkrx.api.trade.vo.CmeTradeReqVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import com.bitkrx.config.logging.CmeCommonLogger;
import com.bitkrx.config.util.CmmCdConstant;
import com.bitkrx.config.util.StringUtils;
import com.bitkrx.config.vo.SendInfoVO;
import com.bitkrx.core.util.SecurityUtil;

@Controller
public class SampleController extends CmeDefaultExtendController{
    protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());

   
}
