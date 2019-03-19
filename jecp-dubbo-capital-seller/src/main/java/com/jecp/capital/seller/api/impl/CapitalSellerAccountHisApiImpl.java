package com.jecp.capital.seller.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.capital.seller.api.CapitalSellerAccountHisApi;
import com.jecp.capital.seller.dto.SellerAccountHisInputDTO;
import com.jecp.capital.seller.enums.AccountHisStatusEnum;
import com.jecp.capital.seller.model.CapitalAccount;
import com.jecp.capital.seller.model.CapitalAccountHis;
import com.jecp.capital.seller.service.impl.SellerAccountHisServiceImpl;
import com.jecp.capital.seller.service.impl.SellerAccountServiceImpl;

@Service
public class CapitalSellerAccountHisApiImpl implements CapitalSellerAccountHisApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Autowired
	private SellerAccountServiceImpl capitalAccountService;
	@Autowired
	private SellerAccountHisServiceImpl capitalAccountHisService;

	/**
	 * 商家账户资金转入
	 */
	@Override
	@Compensable(confirmMethod = "confirmCredit", cancelMethod = "cancelCredit", transactionContextEditor = DubboTransactionContextEditor.class)
	public void credit(SellerAccountHisInputDTO capitalAccountHisInputDTO) {
		log.debug("资金转入Try开始[{}]",capitalAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService.getByGtid(capitalAccountHisInputDTO.getGtid());
		if (foundCapitalAccountHis == null) {
			CapitalAccountHis capitalAccountHis = capitalAccountHisInputDTO.convertToSellerAccountHis();			
			/*悲观锁的目的可保证历史交易记录中余额的准确性*/
			/*CapitalAccount capitalAccount = capitalAccountService.getByUserid(capitalAccountHisInputDTO.getUserId(),true);
			capitalAccountHis.trying(capitalAccount.getBalance());*/
			
			capitalAccountHis.trying(null);
			capitalAccountHisService.save(capitalAccountHis);					
		}
	}

	@Transactional
	public void confirmCredit(SellerAccountHisInputDTO capitalAccountHisInputDTO) {
		log.debug("资金转入Confirm开始[{}]",capitalAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService.getByGtid(capitalAccountHisInputDTO.getGtid());		
		if (foundCapitalAccountHis != null && AccountHisStatusEnum.TRYING.name().equals(foundCapitalAccountHis.getStatus())) {
			foundCapitalAccountHis.confirm();
			capitalAccountHisService.save(foundCapitalAccountHis);
			capitalAccountService.credit(capitalAccountHisInputDTO.getUserId(), capitalAccountHisInputDTO.getTradeAmount());
		}
	}

	public void cancelCredit(SellerAccountHisInputDTO capitalAccountHisInputDTO) {
		log.debug("资金转入Cancel开始[{}]",capitalAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService.getByGtid(capitalAccountHisInputDTO.getGtid());		
		if (foundCapitalAccountHis != null && AccountHisStatusEnum.TRYING.name().equals(foundCapitalAccountHis.getStatus())) {
			foundCapitalAccountHis.cancel();
			capitalAccountHisService.save(foundCapitalAccountHis);			
		}
	}

	/**
	 * 商家账户资金转出
	 */
	@Override
	@Compensable(confirmMethod = "confirmDebit", cancelMethod = "cancelDebit", transactionContextEditor = DubboTransactionContextEditor.class)
	@Transactional
	public void debit(SellerAccountHisInputDTO capitalAccountHisInputDTO) {
		
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService
				.getByGtid(capitalAccountHisInputDTO.getGtid());

		if (foundCapitalAccountHis == null) {
			CapitalAccountHis capitalAccountHis = capitalAccountHisInputDTO.convertToSellerAccountHis();
			CapitalAccount capitalAccount = capitalAccountService.getByUserid(capitalAccountHisInputDTO.getUserId(),true);
			capitalAccountHis.trying(capitalAccount.getBalance());
			capitalAccountHisService.save(capitalAccountHis);

			capitalAccount.debitTry(capitalAccountHisInputDTO.getTradeAmount());
			capitalAccountService.save(capitalAccount);
		}
	}

	@Transactional
	public void confirmDebit(SellerAccountHisInputDTO capitalAccountHisInputDTO) {
		log.debug("转账Confirm开始[{}]",capitalAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService
				.getByGtid(capitalAccountHisInputDTO.getGtid());		
		if (foundCapitalAccountHis != null
				&& AccountHisStatusEnum.TRYING.name().equals(foundCapitalAccountHis.getStatus())) {
			foundCapitalAccountHis.confirm();
			capitalAccountHisService.save(foundCapitalAccountHis);
			
			CapitalAccount capitalAccount = capitalAccountService.getByUserid(capitalAccountHisInputDTO.getUserId(),true);
			capitalAccount.debitConfirm(capitalAccountHisInputDTO.getTradeAmount());
			capitalAccountService.save(capitalAccount);
		}
	}

	@Transactional
	public void cancelDebit(SellerAccountHisInputDTO capitalAccountHisInputDTO) {
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService
				.getByGtid(capitalAccountHisInputDTO.getGtid());
		if (foundCapitalAccountHis != null
				&& AccountHisStatusEnum.TRYING.name().equals(foundCapitalAccountHis.getStatus())) {
			foundCapitalAccountHis.cancel();
			capitalAccountHisService.save(foundCapitalAccountHis);
			
			CapitalAccount capitalAccount = capitalAccountService.getByUserid(capitalAccountHisInputDTO.getUserId(),true);
			capitalAccount.debitCancel(capitalAccountHisInputDTO.getTradeAmount());
			capitalAccountService.save(capitalAccount);
		}
	}
}
