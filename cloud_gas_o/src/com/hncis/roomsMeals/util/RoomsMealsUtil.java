package com.hncis.roomsMeals.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hncis.common.application.SessionInfo;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;

public class RoomsMealsUtil {

	String colorMy ="#FFCC66";
	String colorEnd ="#FF0000";
	String[] colorIng ={"#0066CC","#0099FF","#66CCFF"};
	String[][] objEmpChk =null;
	int intColNo=0;
	
public List<BgabGascrm01Dto> getRequstRoomByPlace(List<BgabGascrm01Dto> list, List<BgabGascrm01Dto> placeList, BgabGascrm01Dto dto, HttpServletRequest req) {
		
		List<BgabGascrm01Dto> newList = new ArrayList<BgabGascrm01Dto>(); 
		BgabGascrm01Dto vo = new BgabGascrm01Dto();
		BgabGascrm01Dto vo2 = new BgabGascrm01Dto();

		String strEENO;
		try {
			strEENO = SessionInfo.getSess_empno(req);
		
			String oldCode="";
			int maxRow=0;
			int intRow=0;		//row(index)
			
			// row count
			maxRow = placeList.size();
			
			// 寃곌낵媛��뗮똿
			String[][] objRtn 		= new String[maxRow+1][48];
			String[][] objName 		= new String[maxRow+1][3];
			
			// �곗냽���덉빟(�щ쾲) 泥댄겕
			objEmpChk = new String[maxRow+1][48];
			
			String strKeySetNew = dto.getDoc_no();
			
			//�꾩껜李⑤웾 媛�
			for (int j=0; j<placeList.size(); j++){
				vo2 = placeList.get(j);
				objName[j][0]=vo2.getRoom_code();		
				objName[j][1]=vo2.getRoom_place();
				objName[j][2]=vo2.getUse_yn();		
			}
			
			for (int i=0; i<list.size(); i++){
				vo2 = (BgabGascrm01Dto)list.get(i);
				
				intRow= maxRow;
				
				for (int j=0; j<maxRow; j++){
					if(objName[j][0].equals(vo2.getRoom_place())){
						intRow= j;
					}
				}
	
				for (int m=0; m<vo2.getTim_info_sbc().length(); m++){
					if("Y".equals(vo2.getTim_info_sbc().substring(m,m+1))){
						objRtn[intRow][m]= get_result_value(vo2, intRow, m, strEENO );
					}
				}
			}
			
			for(int n=0;n<maxRow;n++){
				vo = new BgabGascrm01Dto() ;
				
				vo.setRoom_place(objName[n][1]);
				vo.setUse_yn(objName[n][2]);
				vo.setCol_value(objRtn[n]) ;
				vo.setNew_write_key(strKeySetNew+objName[n][0]);
	
				newList.add(vo);
			}
			
			for(int i=0;i<newList.size();i++){
				int cnt = newList.get(i).getCol_value().length;
				String arr[] = newList.get(i).getCol_value();
				newList.get(i).setTm0000_yn(StringUtil.isNullToString(arr[0]));
				newList.get(i).setTm0030_yn(StringUtil.isNullToString(arr[1]));
				newList.get(i).setTm0100_yn(StringUtil.isNullToString(arr[2]));
				newList.get(i).setTm0130_yn(StringUtil.isNullToString(arr[3]));
				newList.get(i).setTm0200_yn(StringUtil.isNullToString(arr[4]));
				newList.get(i).setTm0230_yn(StringUtil.isNullToString(arr[5]));
				newList.get(i).setTm0300_yn(StringUtil.isNullToString(arr[6]));
				newList.get(i).setTm0330_yn(StringUtil.isNullToString(arr[7]));
				newList.get(i).setTm0400_yn(StringUtil.isNullToString(arr[8]));
				newList.get(i).setTm0430_yn(StringUtil.isNullToString(arr[9]));
				newList.get(i).setTm0500_yn(StringUtil.isNullToString(arr[10]));
				newList.get(i).setTm0530_yn(StringUtil.isNullToString(arr[11]));
				newList.get(i).setTm0600_yn(StringUtil.isNullToString(arr[12]));
				newList.get(i).setTm0630_yn(StringUtil.isNullToString(arr[13]));
				newList.get(i).setTm0700_yn(StringUtil.isNullToString(arr[14]));
				newList.get(i).setTm0730_yn(StringUtil.isNullToString(arr[15]));
				newList.get(i).setTm0800_yn(StringUtil.isNullToString(arr[16]));
				newList.get(i).setTm0830_yn(StringUtil.isNullToString(arr[17]));
				newList.get(i).setTm0900_yn(StringUtil.isNullToString(arr[18]));
				newList.get(i).setTm0930_yn(StringUtil.isNullToString(arr[19]));
				newList.get(i).setTm1000_yn(StringUtil.isNullToString(arr[20]));
				newList.get(i).setTm1030_yn(StringUtil.isNullToString(arr[21]));
				newList.get(i).setTm1100_yn(StringUtil.isNullToString(arr[22]));
				newList.get(i).setTm1130_yn(StringUtil.isNullToString(arr[23]));
				newList.get(i).setTm1200_yn(StringUtil.isNullToString(arr[24]));
				newList.get(i).setTm1230_yn(StringUtil.isNullToString(arr[25]));
				newList.get(i).setTm1300_yn(StringUtil.isNullToString(arr[26]));
				newList.get(i).setTm1330_yn(StringUtil.isNullToString(arr[27]));
				newList.get(i).setTm1400_yn(StringUtil.isNullToString(arr[28]));
				newList.get(i).setTm1430_yn(StringUtil.isNullToString(arr[29]));
				newList.get(i).setTm1500_yn(StringUtil.isNullToString(arr[30]));
				newList.get(i).setTm1530_yn(StringUtil.isNullToString(arr[31]));
				newList.get(i).setTm1600_yn(StringUtil.isNullToString(arr[32]));
				newList.get(i).setTm1630_yn(StringUtil.isNullToString(arr[33]));
				newList.get(i).setTm1700_yn(StringUtil.isNullToString(arr[34]));
				newList.get(i).setTm1730_yn(StringUtil.isNullToString(arr[35]));
				newList.get(i).setTm1800_yn(StringUtil.isNullToString(arr[36]));
				newList.get(i).setTm1830_yn(StringUtil.isNullToString(arr[37]));
				newList.get(i).setTm1900_yn(StringUtil.isNullToString(arr[38]));
				newList.get(i).setTm1930_yn(StringUtil.isNullToString(arr[39]));
				newList.get(i).setTm2000_yn(StringUtil.isNullToString(arr[40]));
				newList.get(i).setTm2030_yn(StringUtil.isNullToString(arr[41]));
				newList.get(i).setTm2100_yn(StringUtil.isNullToString(arr[42]));
				newList.get(i).setTm2130_yn(StringUtil.isNullToString(arr[43]));
				newList.get(i).setTm2200_yn(StringUtil.isNullToString(arr[44]));
				newList.get(i).setTm2230_yn(StringUtil.isNullToString(arr[45]));
				newList.get(i).setTm2300_yn(StringUtil.isNullToString(arr[46]));
				newList.get(i).setTm2330_yn(StringUtil.isNullToString(arr[47]));
			}                       
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newList;
	}


	public List<BgabGascrm01Dto> getRequstRoomByDay(List<BgabGascrm01Dto> list, BgabGascrm01Dto dto, HttpServletRequest req) {
		
		List<BgabGascrm01Dto> newList = new ArrayList<BgabGascrm01Dto>(); 
		BgabGascrm01Dto vo = new BgabGascrm01Dto();
		BgabGascrm01Dto vo2 = new BgabGascrm01Dto();
	
		String strEENO;
		try {
		
			strEENO = SessionInfo.getSess_empno(req);
			String ymd	= dto.getSply_ymd()+"01";
			int maxDay	   = CurrentDateTime.getMonthDays(ymd);
			int intRow	   = 0;		//row(index) = '�쇱옄 - 1'
			
			// 寃곌낵媛��뗮똿
			String[][] objRtn = new String[maxDay][48];
			// �곗냽���덉빟(�щ쾲) 泥댄겕
			objEmpChk = new String[maxDay][48];
			
			//�덇��곌린 �뚮씪誘명꽣
			String strKeySetNew = dto.getDoc_no();
			
			
			String [] day_name_en = {"", "Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat" };
			String [] day_name_ko = {"", "일", "월", "화", "수", "목", "금", "토" };
			
			String strYYMM = dto.getSply_ymd();
			
			String cormUseYn = "";
			
			for (int i=0; i<list.size(); i++){
				vo2 = (BgabGascrm01Dto)list.get(i) ;
				cormUseYn = vo2.getUse_yn();	
				
				if(vo2.getFr_ymd().equals("")){		//�좎껌�뺣낫, �쇰퀎�좎껌�뺣낫 �곸씠
					intRow= 0;
				}else{
					intRow= vo2.getSply_ymd().equals("")?0:Integer.parseInt( vo2.getSply_ymd().substring(6,8));
				}
	
				if(intRow>0){
					for (int m=0; m<vo2.getTim_info_sbc().length(); m++){
	
						if("Y".equals(vo2.getTim_info_sbc().substring(m,m+1))){
							objRtn[intRow-1][m]= get_result_value(vo2, intRow-1, m, strEENO );
						}
					}
				}
			}
			for(int n=0;n<maxDay;n++){
				vo = new BgabGascrm01Dto();
				
				vo.setDay_num(strNum(n+1,"00")) ;
				vo.setCol_value(objRtn[n]);
				vo.setNew_write_key(strKeySetNew+vo.getDay_num());
				if(dto.getLocale().equals("ko")){
					vo.setDay_name(day_name_ko[CurrentDateTime.getDayOfWeek(strYYMM+vo.getDay_num())]);
				}else{
					vo.setDay_name(day_name_en[CurrentDateTime.getDayOfWeek(strYYMM+vo.getDay_num())]);
				}
				vo.setUse_yn(cormUseYn);
				newList.add(vo);
			}
			for(int i=0;i<newList.size();i++){
				int cnt = newList.get(i).getCol_value().length;
				
				String arr[] = newList.get(i).getCol_value();
				
				newList.get(i).setTm0000_yn(StringUtil.isNullToString(arr[0]));
				newList.get(i).setTm0030_yn(StringUtil.isNullToString(arr[1]));
				newList.get(i).setTm0100_yn(StringUtil.isNullToString(arr[2]));
				newList.get(i).setTm0130_yn(StringUtil.isNullToString(arr[3]));
				newList.get(i).setTm0200_yn(StringUtil.isNullToString(arr[4]));
				newList.get(i).setTm0230_yn(StringUtil.isNullToString(arr[5]));
				newList.get(i).setTm0300_yn(StringUtil.isNullToString(arr[6]));
				newList.get(i).setTm0330_yn(StringUtil.isNullToString(arr[7]));
				newList.get(i).setTm0400_yn(StringUtil.isNullToString(arr[8]));
				newList.get(i).setTm0430_yn(StringUtil.isNullToString(arr[9]));
				newList.get(i).setTm0500_yn(StringUtil.isNullToString(arr[10]));
				newList.get(i).setTm0530_yn(StringUtil.isNullToString(arr[11]));
				newList.get(i).setTm0600_yn(StringUtil.isNullToString(arr[12]));
				newList.get(i).setTm0630_yn(StringUtil.isNullToString(arr[13]));
				newList.get(i).setTm0700_yn(StringUtil.isNullToString(arr[14]));
				newList.get(i).setTm0730_yn(StringUtil.isNullToString(arr[15]));
				newList.get(i).setTm0800_yn(StringUtil.isNullToString(arr[16]));
				newList.get(i).setTm0830_yn(StringUtil.isNullToString(arr[17]));
				newList.get(i).setTm0900_yn(StringUtil.isNullToString(arr[18]));
				newList.get(i).setTm0930_yn(StringUtil.isNullToString(arr[19]));
				newList.get(i).setTm1000_yn(StringUtil.isNullToString(arr[20]));
				newList.get(i).setTm1030_yn(StringUtil.isNullToString(arr[21]));
				newList.get(i).setTm1100_yn(StringUtil.isNullToString(arr[22]));
				newList.get(i).setTm1130_yn(StringUtil.isNullToString(arr[23]));
				newList.get(i).setTm1200_yn(StringUtil.isNullToString(arr[24]));
				newList.get(i).setTm1230_yn(StringUtil.isNullToString(arr[25]));
				newList.get(i).setTm1300_yn(StringUtil.isNullToString(arr[26]));
				newList.get(i).setTm1330_yn(StringUtil.isNullToString(arr[27]));
				newList.get(i).setTm1400_yn(StringUtil.isNullToString(arr[28]));
				newList.get(i).setTm1430_yn(StringUtil.isNullToString(arr[29]));
				newList.get(i).setTm1500_yn(StringUtil.isNullToString(arr[30]));
				newList.get(i).setTm1530_yn(StringUtil.isNullToString(arr[31]));
				newList.get(i).setTm1600_yn(StringUtil.isNullToString(arr[32]));
				newList.get(i).setTm1630_yn(StringUtil.isNullToString(arr[33]));
				newList.get(i).setTm1700_yn(StringUtil.isNullToString(arr[34]));
				newList.get(i).setTm1730_yn(StringUtil.isNullToString(arr[35]));
				newList.get(i).setTm1800_yn(StringUtil.isNullToString(arr[36]));
				newList.get(i).setTm1830_yn(StringUtil.isNullToString(arr[37]));
				newList.get(i).setTm1900_yn(StringUtil.isNullToString(arr[38]));
				newList.get(i).setTm1930_yn(StringUtil.isNullToString(arr[39]));
				newList.get(i).setTm2000_yn(StringUtil.isNullToString(arr[40]));
				newList.get(i).setTm2030_yn(StringUtil.isNullToString(arr[41]));
				newList.get(i).setTm2100_yn(StringUtil.isNullToString(arr[42]));
				newList.get(i).setTm2130_yn(StringUtil.isNullToString(arr[43]));
				newList.get(i).setTm2200_yn(StringUtil.isNullToString(arr[44]));
				newList.get(i).setTm2230_yn(StringUtil.isNullToString(arr[45]));
				newList.get(i).setTm2300_yn(StringUtil.isNullToString(arr[46]));
				newList.get(i).setTm2330_yn(StringUtil.isNullToString(arr[47]));
				
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newList;
	}


	public String get_result_value(BgabGascrm01Dto m_vo, int m_row, int m_col, String m_emp ){
		String strRtn = "";
	
		// 媛���쓽 �뗮똿媛�
		String valColor="";
		String valToolTip="";
		String strKeySet="";



		// �붾㈃�대룞��prm
		strKeySet  = m_vo.getDoc_no();

		//�덉빟嫄대퀎 援щ텇���뺣낫
		//if(m_vo.getUse_yn().equals("Y")){		//�뺤씤�꾨즺
		//	valColor += colorEnd;
		//}else{
			if(StringUtil.isNullToString(m_vo.getReq_eeno()).equals(m_emp)){		//蹂몄씤�깅줉
				valColor += colorMy;
			}else{
				if(check_emp(m_row, m_col, m_vo.getReq_eeno(), m_vo.getTim_info_sbc())){
					intColNo = intColNo+1>2?0:intColNo+1;
				}
				valColor += colorIng[intColNo];
			}
		//}

		// tooltip  �뚯쓽�뺣낫
		valToolTip += StringUtil.isNullTrim(m_vo.getReq_dept_nm())+" - "+StringUtil.isNullTrim(m_vo.getReq_eeno())+"("+StringUtil.isNullTrim(m_vo.getReq_eeno_nm())+")";
		valToolTip = trimChar(valToolTip,"/,/");

		strRtn=valColor+"/,/"+valToolTip+"/,/"+strKeySet;


		return strRtn;
	}
	
	public boolean check_emp(int m_row, int m_col, String m_empno, String m_data){
		boolean checkRtn = false;

		int chkStartNo =100;
		int chkLastNo =0;

		if(objEmpChk[m_row][m_col]==null){
			for (int k=0; k<m_data.length(); k++){
				if("Y".equals(m_data.substring(k,k+1))){
					objEmpChk[m_row][k]=m_empno;

					if(chkStartNo > m_data.length()){
						chkStartNo =k;
					}
					chkLastNo=k;
				}
			}

			// �댁쟾�쒓컙 �ъ슜�� 泥댄겕
			if(chkStartNo < m_data.length() && chkStartNo > 0){
				if(objEmpChk[m_row][chkStartNo-1]!=null){
					if(!objEmpChk[m_row][chkStartNo-1].equals("")){
						checkRtn=true;
					}
				}
			}

			// �댄썑�쒓컙 �ъ슜�� 泥댄겕
			if(chkLastNo > 0 && chkLastNo < m_data.length()-1){
				if(objEmpChk[m_row][chkLastNo+1]!=null){
					if(!objEmpChk[m_row][chkLastNo+1].equals("")){
						checkRtn=true;
					}
				}
			}

		}
		return checkRtn;
	}
	public String trimChar(String date, String ch) {

        String _temp = "";
        try {
            for (int i = 0; i < date.length(); i++) {
                if (!date.substring(i, i + 1).equals(ch)) {
                    _temp = _temp + date.substring(i, i + 1);
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw e;
        }
        return _temp;
    }
	public String strNum(String str, String format) throws java.lang.Exception {
		try {
			if (str.trim()==null || str.trim().equals("")){
				str = "0";
			}
			str = trimChar(str,",").trim();
			if (Double.parseDouble(str)==0 && format.indexOf("0")==-1){
				return "";
			} else {
				return strNum(new java.math.BigDecimal(str).doubleValue(),format);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public static String strNum(double str, String format) throws java.lang.Exception {
		NumberFormat df = new java.text.DecimalFormat(format);
		return df.format(str);
	}
}
