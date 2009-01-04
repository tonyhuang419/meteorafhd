package mytest.service.impl;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.vo.ProcessResult;

public class ApplyBillServiceTest extends YingXiaoBaseTest {

	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService applyBillService;

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;

	protected Log logger = LogFactory.getLog(this.getClass());
	private Long cmiId;
	//	private Long itemId;
	private List<Long> planList = null;
	private List<Long> mrPlanList = null;
	private List<Long> itemList = null;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(true);
		super.prepareTestInstance();
	}


	public  void  testDelHasSuredInvoice(){
		Long invoiceId = 4059L;  //2442 3572 3390 3384W 4292
		logger.info("======Before=======");
		printInfo(invoiceId);
		ProcessResult r = applyBillService.delHasSuredInvoice(invoiceId);
//		if(r!=null){
//			logger.info("======exist wrong , cannot canel ==========");
//		}
		logger.info("======After=======");
		printInfo(invoiceId);
	}


	@SuppressWarnings("unchecked")
	private void printInfo(Long invoiceId){
		//获取发票信息
		InvoiceInfo invoice = (InvoiceInfo) commonDao.uniqueResult( " from InvoiceInfo  i   where i.id   = ? " , invoiceId );
		if(invoice!=null){
			cmiId = invoice.getContractMainSid();
			logger.info("开票金额："+  invoice.getInvoiceAmount());
		}
		else{
			logger.info("无开票信息" );
		}

		//获取合同
		logger.info("========合同=========");
		ContractMainInfo contract = (ContractMainInfo) commonDao.uniqueResult( " from	ContractMainInfo cmi where cmi.id = ?", cmiId );
		if(contract!=null){
			logger.info("合同号："+contract.getConId()+"	合同开票总额："+ contract.getBillInvoiceAmount()+"	合同开收据总额："+ contract.getBillReceiptAmount()+
					"	合同应收总额："+ contract.getShouldReceAmount());
		}
		else{
			logger.info("无合同信息" );
		}


		//获取实际计划
		List<RealContractBillandRecePlan> rcbrpList = null;
		if(  planList == null ){
			rcbrpList = commonDao.list(" select r  from   RealContractBillandRecePlan r,  BillandProRelaion br  , ApplyBill ab    ,InvoiceInfo ii " +
					"  where r.realConBillproSid = br.realContractBillandRecePlan  " +
					"  and br.applyBill = ab.billApplyId  " +
					"  and ab.billApplyId = ii.applyInvoiceId  " +
					"  and ii.id = ? order by r.realPredBillDate desc ", invoiceId);
			planList = new ArrayList<Long>();
		}
		else{
			rcbrpList = new ArrayList<RealContractBillandRecePlan>();
			for(Long p : planList){
				rcbrpList.add( (RealContractBillandRecePlan)commonDao.load( RealContractBillandRecePlan.class , p) );
			}
		}
		logger.info("========实际计划=========");
		for( RealContractBillandRecePlan r:rcbrpList ){
			if(r!=null){
				planList.add( r.getRealConBillproSid() );
				logger.info( "实际计划id：" + r.getRealConBillproSid()  + "	合同系统号：" + 	r.getConMainInfoSid() +
						"	预计开票金额：" +r.getRealTaxBillAmount() 	+  "开票类型："+r.getBillType()  +"		实际开票金额："	+ 	r.getBillInvoiceAmount() +
						" 计划应收："+ r.getShouldReceAmount() +	"	最后开票日期：" + r.getRealNowBillDate() + 
						"  计划开票状态："+r.getApplyBillState() +" 关联金额："+r.getRelationReceAmount());
			}
		}


		//通过计划获取项目
		logger.info("========项目信息=========");
		Set<ContractItemMaininfo> itemMainList = null ;
		if( itemMainList == null  && StringUtils.equals(contract.getContractType(), "1")  ){
			itemMainList = new HashSet<ContractItemMaininfo>();
			ContractItemMaininfo itemMain  = null;
			for( RealContractBillandRecePlan plan : rcbrpList){
				if (plan.getContractItemMaininfo() != null) {
					itemMain  = (ContractItemMaininfo) commonDao.load( ContractItemMaininfo.class, plan.getContractItemMaininfo() );
					itemMainList.add ( itemMain );
				}
			}
			itemList = new ArrayList<Long>();
		}
		else{
			itemMainList = new HashSet<ContractItemMaininfo>() ;
			if(itemList!=null){
				for(Long p : itemList){
					itemMainList.add( (ContractItemMaininfo)commonDao.uniqueResult(" from  ContractItemMaininfo m where m.id = ?" , p) );
				}
			}
		}
		for( ContractItemMaininfo i :  itemMainList  ){
			if(itemList!=null){
				itemList.add( i.getConItemMinfoSid());
				logger.info( "项目id：" + i.getConItemMinfoSid()  + "合同系统号：" + 	i.getContractMainInfo()+
						"	项目开票金额：" +i.getBillInvoiceAmount()	+ "	项目开收据金额："	+ i.getBillReceiptAmount() +
						" 项目应收："+ i.getShouldReceAmount() );
			}
		}


		//获取月度计划
		List<MonthlyBillpro>  mrListX = null;
		if(mrPlanList==null){
			mrListX =  commonDao.list(" select m  from MonthlyBillpro m,   RealContractBillandRecePlan r,  BillandProRelaion br  , ApplyBill ab    ,InvoiceInfo ii " +
					"  where  m.realContractBillandRecePlan = r.realConBillproSid " +
					"  and  r.realConBillproSid = br.realContractBillandRecePlan  " +
					"  and br.applyBill = ab.billApplyId  " +
					"  and ab.billApplyId = ii.applyInvoiceId  " +
					"  and ii.id = ? order by r.realPredBillDate desc ", invoiceId);
			mrPlanList = new ArrayList<Long>();
		}
		else{
			mrListX = new ArrayList<MonthlyBillpro>();
			for(Long p : mrPlanList){
				mrListX.add( (MonthlyBillpro)commonDao.uniqueResult(" from  MonthlyBillpro m where m.id = ?" , p) );
			}
		}
		logger.info("========月度计划=========");
		for( MonthlyBillpro mr: mrListX ){
			if(mr!=null){
				mrPlanList.add(mr.getMonthlyBillproSid());
				logger.info("实际计划id：" + mr.getRealContractBillandRecePlan().getRealConBillproSid() +"	实际开票金额："	+ 	mr.getPlanBillAmount()
						+"	最后开票日期：" + mr.getActualBillDate() );
			}
		}
	}


}
