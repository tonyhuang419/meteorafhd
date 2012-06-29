SELECT count(*)
FROM   nasa2.SMS_T_SUB_APPROVAL smssubappr0_
       LEFT OUTER JOIN nasa2.SMS_M_CODE smscode1_
         ON smssubappr0_.APPROVAL_TREE = smscode1_.id
       LEFT OUTER JOIN nasa2.SMS_T_SUB_APPLICATION smssubappl2_
         ON smssubappr0_.APPLICATION_ID = smssubappl2_.id
       LEFT OUTER JOIN nasa2.SMS_M_CODE smscode3_
         ON smssubappl2_.APPLICATION_STATUS = smscode3_.id
       LEFT OUTER JOIN nasa2.SMS_T_SUB_BILL smssubbill4_
         ON smssubappl2_.SUB_BILL_ID = smssubbill4_.id
       LEFT OUTER JOIN nasa2.SMS_M_CODE smscode5_
         ON smssubbill4_.PAYMENT_METHOD = smscode5_.id
       LEFT OUTER JOIN nasa2.M_CURRENCY currency6_
         ON smssubbill4_.CURRENCY_ID = currency6_.id
       LEFT OUTER JOIN nasa2.SMS_M_SUBCON smssubcon7_
         ON smssubbill4_.SUBCON_ID = smssubcon7_.id
       LEFT OUTER JOIN nasa2.M_CURRENCY_EXCHAGE_RATE currencyex8_
         ON smssubbill4_.EXCHANGE_RATE_ID = currencyex8_.id
       LEFT OUTER JOIN nasa2.SMS_M_TIMESHEET smstimeshe9_
         ON smssubappl2_.TIMESHEET_ID = smstimeshe9_.id
       LEFT OUTER JOIN nasa2.M_COMPANY company10_
         ON smstimeshe9_.COMPANY_ID = company10_.id
       LEFT OUTER JOIN nasa2.M_BU bu11_
         ON smstimeshe9_.BU_CHARGEABLE_ID = bu11_.id
       LEFT OUTER JOIN nasa2.M_EMPLOYEE user15_
         ON smstimeshe9_.IN_CHARGE_PARTNER_ID = user15_.id
       LEFT OUTER JOIN nasa2.M_EMPLOYEE user13_
         ON smssubappl2_.APPLICANT_ID = user13_.id
       LEFT OUTER JOIN nasa2.M_EMPLOYEE user14_
         ON smssubappl2_.UPDATE_USER_ID = user14_.id
       LEFT OUTER JOIN nasa2.M_EMPLOYEE user12_
         ON smssubappr0_.APPROVER_ID = user12_.id
WHERE  smssubappr0_.DELETE_FLG = '0'
       AND smscode3_.CODE_SUB_CATEGORY = 'SMS_APPLICATION_STATUS'
       AND smscode1_.CODE_SUB_CATEGORY = 'SMS_APPROVAL_TREE'
       AND ( smscode3_.CODE_VALUE - 2 = smscode1_.CODE_VALUE
              OR smscode3_.CODE_VALUE = '1'
              OR smscode3_.CODE_VALUE = '2'
              OR smscode3_.CODE_VALUE = '9'
              OR smscode3_.CODE_VALUE = '10'
              OR smscode3_.CODE_VALUE = '11' )
       AND company10_.id = '200'

