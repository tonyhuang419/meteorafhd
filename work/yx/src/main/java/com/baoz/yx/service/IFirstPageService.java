package com.baoz.yx.service;

import java.util.Date;
import java.util.List;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.vo.Department;

public interface IFirstPageService {

	/**
	 * 项目建议关闭数
	 */
	public Long showContractItemInfo();
	
	/**
	 * 合同建议关闭数
	 */
	public Long showContractSuggestClose();
	
	/**
	 * 未签合同开票数
	 */
	public Long countNoSignInvoice();
	
	/**
	 * 未签合同开票
	 */
	public PageInfo countNoSignInvoiceInfo( PageInfo info, String groupId ,  Long saleId );

	
	/**
	 * 查询项目建议关闭
	 */
	public PageInfo queryItemSuggestClose( PageInfo info, String groupId ,  Long saleId );
	
	/**
	 * 查询到款信息
	 */
	public Object[] queryHisReveInfo( PageInfo info, Date reveStartDate , Date reveEndDate , String groupId ,  Long saleId );
	
	
	/**
	 * 合同建议关闭
	 */
	public PageInfo queryConSuggestCloseInfo( PageInfo info,  String groupId ,  Long saleId );
	
	
	/**
	 * 前日收款
	 */
	public Double beforeYesterdayReve();
	
	/**
	 * 昨日收款
	 */
	public Double yesterdayReve();
	
	
	/**
	 * 今日收款
	 */
	public Double todayReve();
	
	
	/**
	 * 本月到款
	 */
	public Double thisMonthReve();
	
	/**
	 * 新签合同  0：all  1：工程类 2：集成类
	 */
	public Long countNewCountContract(int i);
	
	
	/**
	 * 新签合同信息 0：all  1：工程类 2：集成类
	 */
	public PageInfo queryNewSignContractInfo( PageInfo info, Date signStartDate , Date signEndDate , int sign ,String groupId ,  Long saleId );

	
	/**
	 * sum新签合同信息 0：all  1：工程类 2：集成类
	 */
	public Double sumNewSignContractInfo( Date signStartDate , Date signEndDate , int sign ,String groupId ,  Long saleId );
	
	/**
	 *  实际合同开票计划 1 全部 2 已开 3 未开
	 */
	public Long countRealContractBillPlan(int i);
	
	/**
	 *  实际合同收款计划 1 全部 2 已收 3 未收 4 未收，已修改
	 */
	public Long countRealContractRecePlan(int i);
	
	/**
	 * 开票计划查询  
	 * inPlan：1 计划内 0 计划外 
	 * billState： 1 已开 0 未开   
	 * [0]pageInfo,[1]是sums数组
	 */
	public Object[] queryShowTotalBill( PageInfo info,  Integer inPlan , String billState  , Date billStartDate ,
			Date billEndDate , String groupId ,  Long expId , Integer hasApply , Integer hasModify);
	
	
	/**
	 * 获取本月计划外开票数
	 */
	public Long countOutplanBill( );

	/**
	 * 获取本月计划外收款数
	 */
	public Long countOutplanRece( );
	
	/**
	 * 申请待确认
	 */
	public Long countApplyWaitSure( );
	
	
	/**
	 * 申请通过未开票
	 */
	public Long countApplyPassNoInvoice( );
	
	
	/**
	 * 未核销收款
	 */
	public Long noContractReve();
	
	/**
	 * 未核销收款
	 */
	public Double noContractReveSum();
	
	/**
	 *未签收款
	 */
	public Object[] queryNoContractReve( PageInfo info, String groupId ,  Long saleId , String reveState );

	
	/**
	 * 发票 已开，未签收（2：增票 3：商票）
	 */
	public Long countVATInvoiceNoSign( String typeSmall);
	
	/**
	 * 增值税发票 已开，未签收
	 */
	public PageInfo queryVATInvoiceNoSign( PageInfo info, String groupId ,  Long saleId , String billType);
	
	/**
	 * 预决算转结算待确认数
	 */
	public Long countConPreToFinal();
	/**
	 * 是否商务
	 */
	public boolean isBusiness();
	
	/**
	 * 是否领导
	 */
	public boolean isLeader();
	

	/**
	 * 是否Admin
	 */
	public boolean isAdmin();
	
	/**
	 * 获取所有部门
	 */
	public List<Department>  doGetAllTeams( );
	
	
	/**
	 * 外协合同待确认count
	 */
	public Long  countOutSourceContractWaitSure( );
	
	/**
	 * 外协合同待确认
	 */
	public PageInfo  queryOutSourceContractWaitSure( PageInfo info, String groupId ,  Long saleId );
	
	/**
	 * 外协合同付款待确认count
	 */
	public Long  countOutSourcePayWaitSure( );
	
	/**
	 * 外协合同付款待确认
	 */
	public PageInfo queryOutSourcePayWaitSure( PageInfo info, String groupId ,  Long saleId );
	
}
