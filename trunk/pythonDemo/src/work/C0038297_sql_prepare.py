# coding=UTF-8

import pyodbc

cnxn = pyodbc.connect('DRIVER={SQL Server};SERVER=10.47.131.54;DATABASE=nasa2_db_20121119;Trusted_Connection?=yes')
cursor = cnxn.cursor()

sapBuPostingByBillNoSql = '''
SELECT
    SUBSTRING(A.KOSTL,6,5)    AS buCd ,
    A.HKONT                   AS accountCd ,
    SUBSTRING( C.LIFNR, 4, 7) AS partVendorCd ,
    B.WAERS                   AS docCurrencyCd ,
    B.XBLNR                   AS billNo ,
    C.ZUONR                   AS billingRefNo ,
    SUM (
        CASE
            WHEN A.SHKZG = 'S'
            THEN A.DMBTR
            ELSE A.DMBTR * -1
        END) AS homeAmt ,
    SUM (
        CASE
            WHEN A.SHKZG = 'S'
            THEN A.WRBTR
            ELSE A.WRBTR * -1
        END) AS docAmt ,
    SUM (
        CASE
            WHEN D.SHKZG = 'S'
            THEN D.DMBTR
            ELSE D.DMBTR * -1
        END) AS homeTax ,
    SUM (
        CASE
            WHEN D.SHKZG = 'S'
            THEN D.WRBTR
            ELSE D.WRBTR * -1
        END) AS docTax,
        SUM (
        CASE
            WHEN A.SHKZG = 'S'
            THEN A.DMBTR
            ELSE A.DMBTR * -1
        END)  +  SUM (
       isnull( CASE
            WHEN D.SHKZG = 'S'
            THEN D.DMBTR
            ELSE D.DMBTR * -1
        END , 0))   AS amount
FROM
    nasa2.I_BSEG A
INNER JOIN nasa2.I_BKPF B
ON
    B.BUKRS = A.BUKRS
AND B.BELNR = A.BELNR
AND B.GJAHR = A.GJAHR
INNER JOIN nasa2.I_BSEG C
ON
    C.BUKRS = A.BUKRS
AND C.BELNR = A.BELNR
AND C.GJAHR = A.GJAHR
LEFT OUTER JOIN nasa2.I_BSEG D
ON
    D.BUKRS = A.BUKRS
AND D.BELNR = A.BELNR
AND D.GJAHR = A.GJAHR
AND D.HKONT = '0002480100'
WHERE
    A.BUKRS = '0150'
AND A.AUFNR IS NULL
AND
    (
        A.ZUONR IS NULL
     OR NOT EXISTS
        (
            SELECT
                1
            FROM
                nasa2.M_TIMESHEET
            WHERE
                TIMESHEET_MAIN_CD = A.ZUONR
        )
    )
AND A.HKONT IN
    (
        SELECT
            ACCOUNT_CD
        FROM
            nasa2.SMS_M_ACCOUNT_CD
        WHERE
            COMPANY_ID = 200
    )
AND A.GJAHR = ?
AND B.MONAT = ?
AND B.BLART IN ('KX', 'KY')
AND C.LIFNR IS NOT NULL
and B.XBLNR  in (?)
GROUP BY
    A.KOSTL ,
    A.HKONT ,
    SUBSTRING( C.LIFNR, 4, 7) ,
    B.WAERS ,
    B.XBLNR ,
    C.ZUONR
ORDER BY
    buCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
'''


sapBuPostingByVendorCodeSql = '''
SELECT
    SUBSTRING(A.KOSTL,6,5)    AS buCd ,
    A.HKONT                   AS accountCd ,
    SUBSTRING( C.LIFNR, 4, 7) AS partVendorCd ,
    B.WAERS                   AS docCurrencyCd ,
    B.XBLNR                   AS billNo ,
    C.ZUONR                   AS billingRefNo ,
    SUM (
        CASE
            WHEN A.SHKZG = 'S'
            THEN A.DMBTR
            ELSE A.DMBTR * -1
        END) AS homeAmt ,
    SUM (
        CASE
            WHEN A.SHKZG = 'S'
            THEN A.WRBTR
            ELSE A.WRBTR * -1
        END) AS docAmt ,
    SUM (
        CASE
            WHEN D.SHKZG = 'S'
            THEN D.DMBTR
            ELSE D.DMBTR * -1
        END) AS homeTax ,
    SUM (
        CASE
            WHEN D.SHKZG = 'S'
            THEN D.WRBTR
            ELSE D.WRBTR * -1
        END) AS docTax,
        SUM (
        CASE
            WHEN A.SHKZG = 'S'
            THEN A.DMBTR
            ELSE A.DMBTR * -1
        END)  +  SUM (
       isnull( CASE
            WHEN D.SHKZG = 'S'
            THEN D.DMBTR
            ELSE D.DMBTR * -1
        END , 0))   AS amount
FROM
    nasa2.I_BSEG A
INNER JOIN nasa2.I_BKPF B
ON
    B.BUKRS = A.BUKRS
AND B.BELNR = A.BELNR
AND B.GJAHR = A.GJAHR
INNER JOIN nasa2.I_BSEG C
ON
    C.BUKRS = A.BUKRS
AND C.BELNR = A.BELNR
AND C.GJAHR = A.GJAHR
LEFT OUTER JOIN nasa2.I_BSEG D
ON
    D.BUKRS = A.BUKRS
AND D.BELNR = A.BELNR
AND D.GJAHR = A.GJAHR
AND D.HKONT = '0002480100'
WHERE
    A.BUKRS = '0150'
AND A.AUFNR IS NULL
AND
    (
        A.ZUONR IS NULL
     OR NOT EXISTS
        (
            SELECT
                1
            FROM
                nasa2.M_TIMESHEET
            WHERE
                TIMESHEET_MAIN_CD = A.ZUONR
        )
    )
AND A.HKONT IN
    (
        SELECT
            ACCOUNT_CD
        FROM
            nasa2.SMS_M_ACCOUNT_CD
        WHERE
            COMPANY_ID = 200
    )
AND A.GJAHR = ?
AND B.MONAT = ?
AND B.BLART IN ('KX', 'KY')
AND C.LIFNR IS NOT NULL
and SUBSTRING( C.LIFNR, 4, 7)  in (?)
GROUP BY
    A.KOSTL ,
    A.HKONT ,
    SUBSTRING( C.LIFNR, 4, 7) ,
    B.WAERS ,
    B.XBLNR ,
    C.ZUONR
ORDER BY
    buCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
'''


sapTimsheetPostingByBillNoSql='''
SELECT
    X.timesheetCd   AS timesheetCd ,
    X.accountCd     AS accountCd ,
    X.partVendorCd  AS partVendorCd ,
    X.docCurrencyCd AS docCurrencyCd ,
    X.billNo        AS billNo ,
    X.billingRefNo  AS billingRefNo ,
    SUM(X.homeAmt)  AS homeAmt ,
    SUM(X.docAmt)   AS docAmt ,
    SUM(X.homeTax)  AS homeTax ,
    SUM(X.docTax)   AS docTax,
    SUM(X.homeAmt) +  isnull(SUM(X.homeTax),0) AS amount
FROM
    (
        SELECT
            A.AUFNR                   AS timesheetCd ,
            A.HKONT                   AS accountCd ,
            SUBSTRING( C.LIFNR, 4, 7) AS partVendorCd ,
            B.WAERS                   AS docCurrencyCd ,
            B.XBLNR                   AS billNo ,
            C.ZUONR                   AS billingRefNo ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.DMBTR
                    ELSE A.DMBTR * -1
                END) AS homeAmt ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.WRBTR
                    ELSE A.WRBTR * -1
                END) AS docAmt ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.DMBTR
                    ELSE D.DMBTR * -1
                END) AS homeTax ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.WRBTR
                    ELSE D.WRBTR * -1
                END) AS docTax
        FROM
            nasa2.I_BSEG A
        INNER JOIN nasa2.I_BKPF B
        ON
            B.BUKRS = A.BUKRS
        AND B.BELNR = A.BELNR
        AND B.GJAHR = A.GJAHR
        INNER JOIN nasa2.I_BSEG C
        ON
            C.BUKRS = A.BUKRS
        AND C.BELNR = A.BELNR
        AND C.GJAHR = A.GJAHR
        LEFT OUTER JOIN nasa2.I_BSEG D
        ON
            D.BUKRS = A.BUKRS
        AND D.BELNR = A.BELNR
        AND D.GJAHR = A.GJAHR
        AND D.HKONT = '0002480100'
        WHERE
            A.BUKRS = '0150'
        AND A.AUFNR IS NOT NULL
        AND A.HKONT IN
            (
                SELECT
                    ACCOUNT_CD
                FROM
                    nasa2.SMS_M_ACCOUNT_CD
                WHERE
                    COMPANY_ID = 200
            )
        AND A.GJAHR = ?
        AND B.MONAT = ?
        AND B.BLART IN ('KX', 'KY')
        AND C.LIFNR IS NOT NULL
              and B.XBLNR  in (?)    
        GROUP BY
            A.AUFNR ,
            A.HKONT ,
            SUBSTRING( C.LIFNR, 4, 7) ,
            B.WAERS ,
            B.XBLNR ,
            C.ZUONR
        UNION ALL
        SELECT
            A.ZUONR                   AS timesheetCd ,
            A.HKONT                   AS accountCd ,
            SUBSTRING( C.LIFNR, 4, 7) AS partVendorCd ,
            B.WAERS                   AS docCurrencyCd ,
            B.XBLNR                   AS billNo ,
            C.ZUONR                   AS billingRefNo ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.DMBTR
                    ELSE A.DMBTR * -1
                END) AS homeAmt ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.WRBTR
                    ELSE A.WRBTR * -1
                END) AS docAmt ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.DMBTR
                    ELSE D.DMBTR * -1
                END) AS homeTax ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.WRBTR
                    ELSE D.WRBTR * -1
                END) AS docTax
        FROM
            nasa2.I_BSEG A
        INNER JOIN nasa2.I_BKPF B
        ON
            B.BUKRS = A.BUKRS
        AND B.BELNR = A.BELNR
        AND B.GJAHR = A.GJAHR
        INNER JOIN nasa2.I_BSEG C
        ON
            C.BUKRS = A.BUKRS
        AND C.BELNR = A.BELNR
        AND C.GJAHR = A.GJAHR
        LEFT OUTER JOIN nasa2.I_BSEG D
        ON
            D.BUKRS = A.BUKRS
        AND D.BELNR = A.BELNR
        AND D.GJAHR = A.GJAHR
        AND D.HKONT = '0002480100'
        WHERE
            A.BUKRS = '0150'
        AND A.AUFNR IS NULL
        AND A.HKONT IN
            (
                SELECT
                    ACCOUNT_CD
                FROM
                    nasa2.SMS_M_ACCOUNT_CD
                WHERE
                    COMPANY_ID = 200
            )
        AND A.GJAHR = ?
        AND A.KOSTL IS NULL
        AND A.ZUONR IS NOT NULL
        AND EXISTS
            (
                SELECT
                    1
                FROM
                    nasa2.M_TIMESHEET
                WHERE
                    TIMESHEET_MAIN_CD = A.ZUONR
            )
        AND B.MONAT = ?
        AND B.BLART IN ('KX', 'KY')
        AND C.LIFNR IS NOT NULL
                and B.XBLNR  in (?)      
        GROUP BY
            A.ZUONR ,
            A.HKONT ,
            SUBSTRING( C.LIFNR, 4, 7) ,
            B.WAERS ,
            B.XBLNR ,
            C.ZUONR
    )
    X
GROUP BY
    timesheetCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
ORDER BY
    timesheetCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
'''


sapTimsheetPostingByVendorCodeSql='''
SELECT
    X.timesheetCd   AS timesheetCd ,
    X.accountCd     AS accountCd ,
    X.partVendorCd  AS partVendorCd ,
    X.docCurrencyCd AS docCurrencyCd ,
    X.billNo        AS billNo ,
    X.billingRefNo  AS billingRefNo ,
    SUM(X.homeAmt)  AS homeAmt ,
    SUM(X.docAmt)   AS docAmt ,
    SUM(X.homeTax)  AS homeTax ,
    SUM(X.docTax)   AS docTax,
    SUM(X.homeAmt) +  isnull(SUM(X.homeTax),0) AS amount
FROM
    (
        SELECT
            A.AUFNR                   AS timesheetCd ,
            A.HKONT                   AS accountCd ,
            SUBSTRING( C.LIFNR, 4, 7) AS partVendorCd ,
            B.WAERS                   AS docCurrencyCd ,
            B.XBLNR                   AS billNo ,
            C.ZUONR                   AS billingRefNo ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.DMBTR
                    ELSE A.DMBTR * -1
                END) AS homeAmt ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.WRBTR
                    ELSE A.WRBTR * -1
                END) AS docAmt ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.DMBTR
                    ELSE D.DMBTR * -1
                END) AS homeTax ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.WRBTR
                    ELSE D.WRBTR * -1
                END) AS docTax
        FROM
            nasa2.I_BSEG A
        INNER JOIN nasa2.I_BKPF B
        ON
            B.BUKRS = A.BUKRS
        AND B.BELNR = A.BELNR
        AND B.GJAHR = A.GJAHR
        INNER JOIN nasa2.I_BSEG C
        ON
            C.BUKRS = A.BUKRS
        AND C.BELNR = A.BELNR
        AND C.GJAHR = A.GJAHR
        LEFT OUTER JOIN nasa2.I_BSEG D
        ON
            D.BUKRS = A.BUKRS
        AND D.BELNR = A.BELNR
        AND D.GJAHR = A.GJAHR
        AND D.HKONT = '0002480100'
        WHERE
            A.BUKRS = '0150'
        AND A.AUFNR IS NOT NULL
        AND A.HKONT IN
            (
                SELECT
                    ACCOUNT_CD
                FROM
                    nasa2.SMS_M_ACCOUNT_CD
                WHERE
                    COMPANY_ID = 200
            )
        AND A.GJAHR = ?
        AND B.MONAT = ?
        AND B.BLART IN ('KX', 'KY')
        AND C.LIFNR IS NOT NULL
        and SUBSTRING( C.LIFNR, 4, 7)  in (?) 
        GROUP BY
            A.AUFNR ,
            A.HKONT ,
            SUBSTRING( C.LIFNR, 4, 7) ,
            B.WAERS ,
            B.XBLNR ,
            C.ZUONR
        UNION ALL
        SELECT
            A.ZUONR                   AS timesheetCd ,
            A.HKONT                   AS accountCd ,
            SUBSTRING( C.LIFNR, 4, 7) AS partVendorCd ,
            B.WAERS                   AS docCurrencyCd ,
            B.XBLNR                   AS billNo ,
            C.ZUONR                   AS billingRefNo ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.DMBTR
                    ELSE A.DMBTR * -1
                END) AS homeAmt ,
            SUM (
                CASE
                    WHEN A.SHKZG = 'S'
                    THEN A.WRBTR
                    ELSE A.WRBTR * -1
                END) AS docAmt ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.DMBTR
                    ELSE D.DMBTR * -1
                END) AS homeTax ,
            SUM (
                CASE
                    WHEN D.SHKZG = 'S'
                    THEN D.WRBTR
                    ELSE D.WRBTR * -1
                END) AS docTax
        FROM
            nasa2.I_BSEG A
        INNER JOIN nasa2.I_BKPF B
        ON
            B.BUKRS = A.BUKRS
        AND B.BELNR = A.BELNR
        AND B.GJAHR = A.GJAHR
        INNER JOIN nasa2.I_BSEG C
        ON
            C.BUKRS = A.BUKRS
        AND C.BELNR = A.BELNR
        AND C.GJAHR = A.GJAHR
        LEFT OUTER JOIN nasa2.I_BSEG D
        ON
            D.BUKRS = A.BUKRS
        AND D.BELNR = A.BELNR
        AND D.GJAHR = A.GJAHR
        AND D.HKONT = '0002480100'
        WHERE
            A.BUKRS = '0150'
        AND A.AUFNR IS NULL
        AND A.HKONT IN
            (
                SELECT
                    ACCOUNT_CD
                FROM
                    nasa2.SMS_M_ACCOUNT_CD
                WHERE
                    COMPANY_ID = 200
            )
        AND A.GJAHR = ?
        AND A.KOSTL IS NULL
        AND A.ZUONR IS NOT NULL
        AND EXISTS
            (
                SELECT
                    1
                FROM
                    nasa2.M_TIMESHEET
                WHERE
                    TIMESHEET_MAIN_CD = A.ZUONR
            )
        AND B.MONAT = ?
        AND B.BLART IN ('KX', 'KY')
        AND C.LIFNR IS NOT NULL
        and SUBSTRING( C.LIFNR, 4, 7)  in (?)   
        GROUP BY
            A.ZUONR ,
            A.HKONT ,
            SUBSTRING( C.LIFNR, 4, 7) ,
            B.WAERS ,
            B.XBLNR ,
            C.ZUONR
    )
    X
GROUP BY
    timesheetCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
ORDER BY
    timesheetCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
'''


webTimsheetPostingDataSql='''
SELECT
    AA.timesheetCd   AS timesheetCd ,
    AA.accountCd     AS accountCd ,
    AA.partVendorCd  AS partVendorCd ,
    AA.docCurrencyCd AS docCurrencyCd ,
    AA.billNo        AS billNo ,
    AA.billingRefNo  AS billingRefNo ,
    SUM(AA.homeAmt)  AS homeAmt ,
    SUM(AA.docAmt)   AS docAmt ,
    SUM(AA.homeTax)  AS homeTax ,
    SUM(AA.docTax)   AS docTax,
    SUM(AA.homeAmt) +  isnull(SUM(AA.homeTax),0) AS amount
FROM
    (
        SELECT
            D.TIMESHEET_MAIN_CD          AS timesheetCd ,
            I.ACCOUNT_CD                 AS accountCd ,
            SUBSTRING(B.VENDER_CODE,4,7) AS partVendorCd ,
            Z.CURRENCY_CD                AS docCurrencyCd ,
            B.BILL_NO                    AS billNo ,
            C.BILL_DETAIL_NO             AS billingRefNo ,
            SUM(A.FEE_NET_AMOUNT_HOME)   AS homeAmt ,
            SUM(A.FEE_NET_AMOUNT)        AS docAmt ,
            SUM(A.FEE_VAT_AMOUNT_HOME)   AS homeTax ,
            SUM(A.FEE_VAT_AMOUNT)        AS docTax
        FROM
            nasa2.SMS_T_SUB_PAYMENT_DETAIL A
        INNER JOIN nasa2.SMS_T_SUB_BILL B
        ON
            B.ID = A.BILL_ID
        INNER JOIN nasa2.SMS_T_SUB_APPLICATION C
        ON
            C.ID = A.APPLICATION_ID
        INNER JOIN nasa2.M_TIMESHEET D
        ON
            D.ID = A.TIMESHEET_ID
        LEFT OUTER JOIN nasa2.M_COMPANY K
        ON
            K.ID = D.COMPANY_ID
        INNER JOIN nasa2.SMS_M_SUBCON E
        ON
            E.ID = B.SUBCON_ID
        INNER JOIN nasa2.SMS_M_SUBCON_PARENT F
        ON
            F.ID = E.SUBCON_PARENT_ID
        LEFT OUTER JOIN nasa2.SMS_M_CODE J
        ON
            J.ID = F.GROUP_CODE
        INNER JOIN nasa2.M_CURRENCY Z
        ON
            Z.ID = B.CURRENCY_ID ,
            nasa2.SMS_M_ACCOUNT_CD I
        WHERE
            A.DELETE_FLG = 0
        AND C.SEND_FLG = 1
        AND A.POSTING_YEAR = ?
        AND A.POSTING_MONTH = ?
        AND K.COMPANY_CD = '0150'
        AND A.FEE_NET_ACC_TITLE <> 22900
        AND A.EXP_NET_ACC_TITLE <> 22900
        AND I.ACCOUNT_TYPE = A.FEE_NET_ACC_TITLE
        AND I.INNER_USE_FLG =
            CASE
                WHEN J.CODE_VALUE = '1'
                THEN '1'
                ELSE '0'
            END
        AND I.COMPANY_ID = 200
                
                and b.BILL_NO in (?)
                
        GROUP BY
            D.TIMESHEET_MAIN_CD ,
            I.ACCOUNT_CD ,
            SUBSTRING(B.VENDER_CODE,4,7) ,
            Z.CURRENCY_CD ,
            B.BILL_NO ,
            C.BILL_DETAIL_NO ,
            B.PAYMENT_DATE
        UNION ALL
        SELECT
            D.TIMESHEET_MAIN_CD          AS timesheetCd ,
            I.ACCOUNT_CD                 AS accountCd ,
            SUBSTRING(B.VENDER_CODE,4,7) AS partVendorCd ,
            Z.CURRENCY_CD                AS docCurrencyCd ,
            B.BILL_NO                    AS billNo ,
            C.BILL_DETAIL_NO             AS billingRefNo ,
            SUM(A.EXP_NET_AMOUNT_HOME)   AS homeAmt ,
            SUM(A.EXP_NET_AMOUNT)        AS docAmt ,
            SUM(A.EXP_VAT_AMOUNT_HOME)   AS homeTax ,
            SUM(A.EXP_VAT_AMOUNT)        AS docTax
        FROM
            nasa2.SMS_T_SUB_PAYMENT_DETAIL A
        INNER JOIN nasa2.SMS_T_SUB_BILL B
        ON
            B.ID = A.BILL_ID
        INNER JOIN nasa2.SMS_T_SUB_APPLICATION C
        ON
            C.ID = A.APPLICATION_ID
        INNER JOIN nasa2.M_TIMESHEET D
        ON
            D.ID = A.TIMESHEET_ID
        LEFT OUTER JOIN nasa2.M_COMPANY K
        ON
            K.ID = D.COMPANY_ID
        INNER JOIN nasa2.SMS_M_SUBCON E
        ON
            E.ID = B.SUBCON_ID
        INNER JOIN nasa2.SMS_M_SUBCON_PARENT F
        ON
            F.ID = E.SUBCON_PARENT_ID
        LEFT OUTER JOIN nasa2.SMS_M_CODE J
        ON
            J.ID = F.GROUP_CODE
        INNER JOIN nasa2.M_CURRENCY Z
        ON
            Z.ID = B.CURRENCY_ID ,
            nasa2.SMS_M_ACCOUNT_CD I
        WHERE
            A.DELETE_FLG = 0
        AND C.SEND_FLG = 1
        AND A.POSTING_YEAR = ?
        AND A.POSTING_MONTH = ?
        AND K.COMPANY_CD = '0150'
        AND A.FEE_NET_ACC_TITLE <> 22900
        AND A.EXP_NET_ACC_TITLE <> 22900
        AND I.ACCOUNT_TYPE = A.EXP_NET_ACC_TITLE
        AND I.INNER_USE_FLG =
            CASE
                WHEN J.CODE_VALUE = '1'
                THEN '1'
                ELSE '0'
            END
        AND I.COMPANY_ID = 200
                
                and b.BILL_NO in (?)
                
        GROUP BY
            D.TIMESHEET_MAIN_CD ,
            I.ACCOUNT_CD ,
            SUBSTRING(B.VENDER_CODE,4,7) ,
            Z.CURRENCY_CD ,
            B.BILL_NO ,
            C.BILL_DETAIL_NO ,
            B.PAYMENT_DATE
    )
    AA
GROUP BY
    AA.timesheetCd ,
    AA.accountCd ,
    AA.partVendorCd ,
    AA.docCurrencyCd ,
    AA.billNo ,
    AA.billingRefNo
ORDER BY
    timesheetCd ,
    accountCd ,
    partVendorCd ,
    docCurrencyCd ,
    billNo ,
    billingRefNo
'''

webBuPostingSql='''
SELECT
    BU_CD,
    accountCd,
    partVendorCd,
    docCurrencyCd,
    billNo,
    billRefNo,
    timesheetCode,
    SUM(homeAmt) AS HOME_AMT,
    SUM(docAmt)  AS DOC_AMT,
    SUM(homeTax) AS HOMT_TAX,
    SUM(docTax)  AS DOC_TAX,
    SUM(homeAmt) +  isnull(SUM(homeTax),0) AS amount
FROM
    (
        -- Web Real Posting Fee
        SELECT
            CASE
                WHEN M.BU_CD IS NULL
                THEN H.BU_CD
                ELSE M.BU_CD
            END                          AS BU_CD ,
            I.ACCOUNT_CD                 AS accountCd ,
            SUBSTRING(B.VENDER_CODE,4,7) AS partVendorCd ,
            Z.CURRENCY_CD                AS docCurrencyCd ,
            B.BILL_NO                    AS billNo ,
            C.BILL_DETAIL_NO             AS billRefNo ,
            D.timesheet_main_cd          AS timesheetCode ,
            SUM(A.FEE_NET_AMOUNT_HOME)   AS homeAmt ,
            SUM(A.FEE_NET_AMOUNT)        AS docAmt ,
            SUM(A.FEE_VAT_AMOUNT_HOME)   AS homeTax ,
            SUM(A.FEE_VAT_AMOUNT)        AS docTax
        FROM
            nasa2.SMS_T_SUB_PAYMENT_DETAIL A
        INNER JOIN nasa2.SMS_T_SUB_BILL B
        ON
            B.ID = A.BILL_ID
        INNER JOIN nasa2.SMS_T_SUB_APPLICATION C
        ON
            C.ID = A.APPLICATION_ID
        INNER JOIN nasa2.M_TIMESHEET D
        ON
            D.ID = A.TIMESHEET_ID
        LEFT JOIN NASA2.M_EMPLOYEE G
        ON
            D.IN_CHARGE_PARTNER_ID = G.ID
        INNER JOIN NASA2.M_BU H
        ON
            G.RESOUCE_BU_ID = H.ID
        LEFT JOIN NASA2.M_BU M
        ON
            D.BU_CHARGEABLE_ID=M.ID
        INNER JOIN nasa2.SMS_M_SUBCON E
        ON
            E.ID = B.SUBCON_ID
        INNER JOIN nasa2.SMS_M_SUBCON_PARENT F
        ON
            F.ID = E.SUBCON_PARENT_ID
        LEFT OUTER JOIN nasa2.SMS_M_CODE J
        ON
            J.ID = F.GROUP_CODE
        INNER JOIN nasa2.M_CURRENCY Z
        ON
            Z.ID = B.CURRENCY_ID ,
            nasa2.SMS_M_ACCOUNT_CD I
        WHERE
            A.DELETE_FLG = '0'
        AND C.SEND_FLG = '1'
        AND A.POSTING_YEAR =?
        AND A.POSTING_MONTH = ?
        AND
            (
                A.FEE_NET_ACC_TITLE = 22900
             OR A.EXP_NET_ACC_TITLE = 22900
            )
        AND I.ACCOUNT_TYPE = A.FEE_NET_ACC_TITLE
        AND I.INNER_USE_FLG =
            CASE
                WHEN J.CODE_VALUE = '1'
                THEN '1'
                ELSE '0'
            END
        AND I.COMPANY_ID = '200'
                
                and b.BILL_NO in (?)
                
        GROUP BY
            CASE
                WHEN M.BU_CD IS NULL
                THEN H.BU_CD
                ELSE M.BU_CD
            END ,
            I.ACCOUNT_CD ,
            SUBSTRING(B.VENDER_CODE,4,7) ,
            Z.CURRENCY_CD ,
            B.BILL_NO ,
            C.BILL_DETAIL_NO ,
            D.timesheet_main_cd ,
            B.PAYMENT_DATE
        UNION ALL
        -- Web Real Posting Expense
        SELECT
            CASE
                WHEN M.BU_CD IS NULL
                THEN H.BU_CD
                ELSE M.BU_CD
            END                          AS BU_CD ,
            I.ACCOUNT_CD                 AS accountCd ,
            SUBSTRING(B.VENDER_CODE,4,7) AS partVendorCd ,
            Z.CURRENCY_CD                AS docCurrencyCd ,
            B.BILL_NO                    AS billNo ,
            C.BILL_DETAIL_NO             AS billRefNo ,
            D.timesheet_main_cd          AS timesheetCode ,
            SUM(A.EXP_NET_AMOUNT_HOME)   AS homeAmt ,
            SUM(A.EXP_NET_AMOUNT)        AS docAmt ,
            SUM(A.EXP_VAT_AMOUNT_HOME)   AS homeTax ,
            SUM(A.EXP_VAT_AMOUNT)        AS docTax
        FROM
            nasa2.SMS_T_SUB_PAYMENT_DETAIL A
        INNER JOIN nasa2.SMS_T_SUB_BILL B
        ON
            B.ID = A.BILL_ID
        INNER JOIN nasa2.SMS_T_SUB_APPLICATION C
        ON
            C.ID = A.APPLICATION_ID
        INNER JOIN nasa2.M_TIMESHEET D
        ON
            D.ID = A.TIMESHEET_ID
        LEFT JOIN NASA2.M_EMPLOYEE G
        ON
            D.IN_CHARGE_PARTNER_ID = G.ID
        INNER JOIN NASA2.M_BU H
        ON
            G.RESOUCE_BU_ID = H.ID
        LEFT JOIN NASA2.M_BU M
        ON
            D.BU_CHARGEABLE_ID=M.ID
        INNER JOIN nasa2.SMS_M_SUBCON E
        ON
            E.ID = B.SUBCON_ID
        INNER JOIN nasa2.SMS_M_SUBCON_PARENT F
        ON
            F.ID = E.SUBCON_PARENT_ID
        LEFT OUTER JOIN nasa2.SMS_M_CODE J
        ON
            J.ID = F.GROUP_CODE
        INNER JOIN nasa2.M_CURRENCY Z
        ON
            Z.ID = B.CURRENCY_ID ,
            nasa2.SMS_M_ACCOUNT_CD I
        WHERE
            A.DELETE_FLG = '0'
        AND C.SEND_FLG = '1'
        AND A.POSTING_YEAR =?
        AND A.POSTING_MONTH = ?
        AND
            (
                A.FEE_NET_ACC_TITLE = 22900
             OR A.EXP_NET_ACC_TITLE = 22900
            )
        AND I.ACCOUNT_TYPE = A.EXP_NET_ACC_TITLE
        AND I.INNER_USE_FLG =
            CASE
                WHEN J.CODE_VALUE = '1'
                THEN '1'
                ELSE '0'
            END
        AND I.COMPANY_ID = '200'
                
                and b.BILL_NO in (?)
                
        GROUP BY
            CASE
                WHEN M.BU_CD IS NULL
                THEN H.BU_CD
                ELSE M.BU_CD
            END ,
            I.ACCOUNT_CD ,
            SUBSTRING(B.VENDER_CODE,4,7) ,
            Z.CURRENCY_CD ,
            B.BILL_NO ,
            C.BILL_DETAIL_NO ,
            D.timesheet_main_cd ,
            B.PAYMENT_DATE
    )
    WXY
WHERE
    homeAmt <> 0
 OR docAmt <> 0
 OR homeTax <> 0
 OR docTax <> 0
GROUP BY
    BU_CD,
    accountCd,
    partVendorCd,
    docCurrencyCd,
    billNo,
    billRefNo,
    timesheetCode
'''


    
def sapBuPostingByBillNo(year,month,billNo):    
    cursor.execute(sapBuPostingByBillNoSql, year, month , billNo )
    return cursor.fetchall()

def sapBuPostingByVendorCode(year,month,vendorCode):
    cursor.execute(sapBuPostingByVendorCodeSql, year, month , vendorCode )
    return cursor.fetchall()


def sapTimsheetPostingByBillNo(year,month,billno):
    cursor.execute(sapTimsheetPostingByBillNoSql, year, month , billno , year, month , billno  )
    return cursor.fetchall()

def sapTimsheetPostingByVendorCode(year,month,billno):
    cursor.execute(sapTimsheetPostingByVendorCodeSql, year, month , billno , year, month , billno  )
    return cursor.fetchall()

def webTimsheetPostingDate(year,month,billno):
    cursor.execute(webTimsheetPostingDataSql, year, month , billno , year, month , billno  )
    return cursor.fetchall()

def webBuPosting(year,month,billno):
    cursor.execute(webBuPostingSql, year, month , billno , year, month , billno  )
    return cursor.fetchall()
    
if __name__ == "__main__":
    print('##############sapBuPostingByBillNo###############')
    rows = sapBuPostingByBillNo(2012, 10 , 'ABJ1210005-1,ABJ');
    for row in rows:
#        print row
        print '%s,%s,%s,%s,%s' % (row.partVendorCd,row.billNo,row.homeAmt,row.docAmt,row.amount)
    
    
    print('##############sapBuPostingByVendorCode###############')
    rows = sapBuPostingByVendorCode(2012, 10 , '1023954');
    for row in rows:
#        print row
        print '%s,%s,%s,%s,%s' % (row.partVendorCd,row.billNo,row.homeAmt,row.docAmt,row.amount)
    
    
    
    print('##############sapTimsheetPosting###############')
    rows = sapTimsheetPostingByBillNo(2012, 10 , 'ABJ1210005-1,ABJ');
    for row in rows:
#        print row
        print '%s,%s,%s,%s,%s' % (row.partVendorCd,row.billNo,row.homeAmt,row.docAmt,row.amount)
        
        
    print('##############sapTimsheetPostingByVendorCode###############')
    rows = sapTimsheetPostingByVendorCode(2012, 10 , '1023954');
    for row in rows:
#        print row
        print '%s,%s,%s,%s,%s' % (row.partVendorCd,row.billNo,row.homeAmt,row.docAmt,row.amount)
        
        
#    print('#############################')
#    rows = webTimsheetPostingDate(2012, 10 , 'ABJ1210005-1,ABJ');
#    for row in rows:
#        print row
#        print row.partVendorCd
#        print row.billNo
#        print row.homeAmt
#        print row.docAmt        
#    
#    print('#############################')
#    rows = webBuPosting(2012, 10 , 'ABJ1210005-1,ABJ');
#    for row in rows:
#        print row
#        print row.partVendorCd
#        print row.billNo
#        print row.homeAmt
#        print row.docAmt        
       
        

