package com.jecp.trade.record.controller;

import java.io.PrintWriter;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.base.page.PageBean;
import com.jecp.trade.record.api.TradeRecordApi;
import com.jecp.trade.record.dto.TradeRecordListDTO;

/**
 * @Title 交易记录管理
 * @author WWT
 * @date 2018年10月25日
 */
@Controller
@RequestMapping("/trade/record")
public class TradeRecordController extends BaseController {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private TradeRecordApi tradeRecordApi;

	/**
	 * 列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list() {
		PageBean<TradeRecordListDTO> pageBean = tradeRecordApi.list(super.getUserId(), super.getPageQuery());
		return pageBean.toDataGridJSON();
	}
	/**
	 * 获得所有ID(压力测试)
	 */
	@RequestMapping("/allTradeId")
	public void allTradeId() {
		super.getResponse().setCharacterEncoding("utf-8");
		super.getResponse().setContentType("multipart/form-data");
		super.getResponse().setHeader("Content-Disposition", "attachment;fileName=allTradeId.txt");
		Set<Long> tradeIdSet = tradeRecordApi.getAllGtid();
		PrintWriter writer = null;
		try {		
			writer = super.getResponse().getWriter();
			for(Long tradeId:tradeIdSet) {
				writer.write(String.valueOf(tradeId));
				writer.write(System.getProperty("line.separator"));
			}			
		}catch(Exception e) {
			log.error("",e);
		}finally {
			if(writer!=null) {
				writer.close();
			}			
		}
	}
}
