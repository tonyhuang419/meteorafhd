package mytest.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.PlanReceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IImportHisDataService;

public class ImportHisDataServiceTest extends YingXiaoBaseTest {

	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;

	protected Log logger = LogFactory.getLog(this.getClass());
	private Long cmiId;
	private Long itemId;
	private List<Long> planList = null;
	private List<Long> mrPlanList = null;
	private List<Long> relation = null;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(true);
		super.prepareTestInstance();
	}


	public  void  testCancelReceByRece(){
		Long receId = 3475L; //      2962   3318    3465 3105   3489
		logger.info("======Before=======");
		printInfo(receId);
		importHisDataService.cancelReceByRece(receId);
		logger.info("======After=======");
		printInfo(receId);
	}


	@SuppressWarnings("unchecked")
	private void printInfo(Long receId){
		//获取收款信息
		ReveInfo receInfo = (ReveInfo) commonDao.uniqueResult( " from ReveInfo  r   where r.id   = ? " , receId );
		if(receInfo!=null){
			cmiId = receInfo.getConMainInfoSid();
			itemId = receInfo.getBillSid();
			logger.info("收款金额："+  receInfo.getAmount() );
		}
		else{
			logger.info("无收款信息" );
		}

		//获取合同
		logger.info("========合同=========");
		ContractMainInfo contract = (ContractMainInfo) commonDao.uniqueResult( " from	ContractMainInfo cmi where cmi.id = ?", cmiId );
		if(contract!=null){
			logger.info("合同到款总额："+ contract.getRealArriveAmount());
		}
		else{
			logger.info("无合同信息" );
		}

		//获取项目
		ContractItemMaininfo itemMain = null;
		logger.info("========项目=========");
		if (StringUtils.equals(contract.getContractType(), "1")) {
			itemMain = (ContractItemMaininfo) commonDao.uniqueResult( " from ContractItemMaininfo cimi where cimi.id = ? ",  itemId );
			if(itemMain!=null){
				logger.info("项目到款总额："+ itemMain.getRealArriveAmount() );
			}
			else{
				logger.info("无项目信息" );
			}
		}


		//获取 实际计划-收款关联
		List<PlanReceInfo> planReceInfoList = null;
		if( relation==null){
			planReceInfoList = commonDao.list("from PlanReceInfo pi where pi.reveInfoId = ? order by pi.amountTime desc )", receId);
			relation = new ArrayList<Long>();
		}
		else{
			planReceInfoList = new ArrayList<PlanReceInfo>();
			for(Long p : relation){
				planReceInfoList.add( (PlanReceInfo)commonDao.uniqueResult(" from  PlanReceInfo p where p.id = ?" , p) );
			}
		}
		logger.info("========实际计划-收款关联=========");
		for(  PlanReceInfo r : planReceInfoList ){
			if(r!=null){
				relation.add(r.getId());
				logger.info("关联id："+ r.getId() +  "	到款金额：" + r.getAmount()+ "	到款时间：" + r.getAmountTime() );
			}
		}

		//获取实际计划
		List<RealContractBillandRecePlan> rcbrpList = null;
		if(  planList == null ){
			rcbrpList = commonDao.list(" select r  from PlanReceInfo pi , RealContractBillandRecePlan r  " +
					"   where  pi.planId = r.realConBillproSid  " +
					"  and  pi.reveInfoId = ? " +
					" order by r.realPredReceDate desc)", receId );
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
					"	预计到款金额：" +r.getRealTaxReceAmount() 	+ "	实际到款金额："	+ 	r.getRealArriveAmount() +"	最后收款日期：" + r.getRealNowReceDate());
			}
		}



		//获取月度计划
		List<MonthlyRecepro>  mrListX = null;
		if(mrPlanList==null){
			mrListX =  commonDao.list(" select mr   from PlanReceInfo pi , RealContractBillandRecePlan r ,  MonthlyRecepro mr " +
					"   where  pi.planId = r.realConBillproSid    " +
					" and   r.realConBillproSid  = mr.realContractBillandRecePlan   " +
					" and  pi.reveInfoId = ? " +
					"  order by r.realPredReceDate desc)", receId );
			mrPlanList = new ArrayList<Long>();
		}
		else{
			mrListX = new ArrayList<MonthlyRecepro>();
			for(Long p : mrPlanList){
				mrListX.add( (MonthlyRecepro)commonDao.uniqueResult(" from  MonthlyRecepro m where m.id = ?" , p) );
			}
		}
		logger.info("========月度计划=========");
		for( MonthlyRecepro mr: mrListX ){
			if(mr!=null){
				mrPlanList.add(mr.getMonthlyReceproSid());
				logger.info("实际计划id：" + mr.getRealContractBillandRecePlan().getRealConBillproSid() +"	实际到款金额："	+ 	mr.getAlreadyArrivedAmount()
						+"	最后收款日期：" + mr.getActualArrivedDate());
			}
		}
	}

}
