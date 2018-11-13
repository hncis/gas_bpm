package org.uengine.web.monitoring.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.uengine.kernel.RoleMapping;
import org.uengine.util.UEngineUtil;
import org.uengine.web.monitoring.controller.MonitoringController;
import org.uengine.web.monitoring.dao.MonitoringDAO;
import org.uengine.web.monitoring.vo.MonitoringVO;

import com.google.gson.JsonObject;

import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.data.LineData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.dataset.LineDataset;

@Service("monitoringService")
public class MonitoringServiceImpl implements MonitoringService {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="monitoringDAO")
	private MonitoringDAO monitoringDAO;
	
	@Override
	public Object getChartData(String type) throws Exception {
		Object data = null;
		ColorAutoDeployerTest cadt = new ColorAutoDeployerTest();
		
		/*
		 * type(화면아이디)에 따라서 제공하는 데이터가 달라진다.  
		 * 1) newworkstatus: 신규업무현황 - MonitoringController.MC_type1
		 * 2) processingdelaystatusbytask: 업무별 처리 지연현황 -MonitoringController.MC_type2
		 * 3) progressstatusbytask: 업무별 진행현황 - MonitoringController.MC_type3
		 * 4) taskcompletedaveragetime: 평균 업무완료 시간 추이 - MonitoringController.MC_type4
		 */
		List<MonitoringVO> listMonitoringVo = null;
		if(type.equals(MonitoringController.MC_type1)){
			listMonitoringVo = monitoringDAO.getNewWorkStatus();
			data = canvasSevendayJSONDataMaker(listMonitoringVo, type, cadt);
		}else if(type.equals(MonitoringController.MC_type2)){
			listMonitoringVo = monitoringDAO.getProcessingDelayStatusByTask();
			data = canvasJSONDataMaker(listMonitoringVo, type);
			// data = (BarData)getProcessingDelayStatusByTask(new BarData(), listMonitoringVo, cadt);
		}else if(type.equals(MonitoringController.MC_type3)){
			listMonitoringVo = monitoringDAO.getProgressStatusByTask();
			data = canvasJSONDataMaker(listMonitoringVo, type);
		}else if(type.equals(MonitoringController.MC_type4)){
			listMonitoringVo = monitoringDAO.getTaskCompletedAverageTime();
			data = canvasSevendayJSONDataMaker(listMonitoringVo, type, cadt);
		}else{
			
		}
		
		return data;	
	}
	
	@Override
	public Object getComboBoxData(List<RoleMapping> roleInfoList)
			throws Exception {
		JSONArray datas = new JSONArray();
		Iterator<RoleMapping> roleInfoListIterator = roleInfoList.iterator();
		while(roleInfoListIterator.hasNext()){
			RoleMapping roleMapping = roleInfoListIterator.next();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("groupName", roleMapping.getGroupName());
			jsonObject.put("groupId", roleMapping.getGroupId());
			datas.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("datas", datas);
		return result;
	}
	
		

	private Object getSevenDaysDate(List<MonitoringVO> monitoringVoList,
									ColorAutoDeployerTest cadt, String type) throws Exception {
		Object result = null;
		BarData barData = null;
		LineData lineData = null;
		if(type.equals(MonitoringController.MC_type1)){
			barData = new BarData();	
		}else if(type.equals(MonitoringController.MC_type4)){
			lineData = new LineData(); 
		}
		
		List<String> dateStore = new ArrayList<String>();
		
		int dayCount = 6;
		
		for(int i =0; i<= dayCount; i++){
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, -(dayCount-i));
			Date date = calendar.getTime();
			SimpleDateFormat innerFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = innerFormat.format(date);
			
			dateStore.add(dateString);
			if(type.equals(MonitoringController.MC_type1)){
				barData.addLabel(dateString);		
			}else if(type.equals(MonitoringController.MC_type4)){
				lineData.addLabel(dateString);	
			}
		}
		
		
		// 각 DefName 별로 분류함
		Map<String, List<MonitoringVO>> monitoringVoManagerList = new HashMap<String, List<MonitoringVO>>();
		Iterator<MonitoringVO> monitoringVoListIterator = monitoringVoList.iterator();
		while(monitoringVoListIterator.hasNext()){
			
				MonitoringVO monitoringVoObject = monitoringVoListIterator.next();
				if(monitoringVoManagerList.containsKey(monitoringVoObject.getDefName())){
					List<MonitoringVO> monitoringVOList = monitoringVoManagerList.get(monitoringVoObject.getDefName());
					monitoringVOList.add(monitoringVoObject);
					monitoringVoManagerList.put(monitoringVoObject.getDefName(), monitoringVOList);
				}else{
					List<MonitoringVO> monitoringVOList = new ArrayList<MonitoringVO>();
					monitoringVOList.add(monitoringVoObject);
					monitoringVoManagerList.put(monitoringVoObject.getDefName(), monitoringVOList);
				}
		}

		Iterator<String> monitoringVoManagerKeyList = monitoringVoManagerList.keySet().iterator();
		
		while(monitoringVoManagerKeyList.hasNext()){
			String mapKey = monitoringVoManagerKeyList.next();
			List<MonitoringVO> monitoringVOList = monitoringVoManagerList.get(mapKey);
			
			Iterator<String> dataStoreIterator = dateStore.iterator();
			
			
			BarDataset barDataSet = null;
			LineDataset lineDataSet = null;
			
			boolean isInit = true;
			int testCount =1;
			while(dataStoreIterator.hasNext()){
				String eachDate = dataStoreIterator.next();
				Iterator<MonitoringVO> eachMonitoringVoListIterator = monitoringVOList.iterator();
				
				
				int monitoringTransValue = 0;
				while(eachMonitoringVoListIterator.hasNext()){
					MonitoringVO monitoringVOObject = eachMonitoringVoListIterator.next();
					Date monitoringDate = null;
					if(isInit){
						int tempStore = monitoringVOObject.hashCode();
						if(type.equals(MonitoringController.MC_type1)){
							barDataSet = new BarDataset();
							barDataSet.setLabel(monitoringVOObject.getDefName());
							barDataSet.addBackgroundColor(cadt.transColor(cadt.getColor(tempStore)));
							monitoringDate = monitoringVOObject.getStartedDate();
							isInit =false;
						}else if(type.equals(MonitoringController.MC_type4)){
							lineDataSet = new LineDataset();
							lineDataSet.setLabel(monitoringVOObject.getDefName());							
							lineDataSet.setBackgroundColor(cadt.transColor(cadt.getColor(tempStore)));
							lineDataSet.setBorderColor(cadt.transColor(cadt.getColor(tempStore)));
							lineDataSet.setFill(false);
							monitoringDate = monitoringVOObject.getFinishedDate();
							isInit =false;
						}
					}else{
						
					}
					if(type.equals(MonitoringController.MC_type1)){
						monitoringDate = monitoringVOObject.getStartedDate();
						
					}else if(type.equals(MonitoringController.MC_type4)){
						monitoringDate = monitoringVOObject.getFinishedDate();
						
					}
					SimpleDateFormat innerFormat = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = innerFormat.format(monitoringDate);
					if(eachDate.equals(dateString)){
						if(type.equals(MonitoringController.MC_type1)){
							monitoringTransValue =  monitoringVOObject.getTotalCount();	
						}else if(type.equals(MonitoringController.MC_type4)){
							monitoringTransValue = monitoringVOObject.getWorkingDayAVG();
						}
						
					}
				}	
				
				if(type.equals(MonitoringController.MC_type1)){
					if(monitoringTransValue == 0){
						barDataSet.addData(0);
					}else{
						barDataSet.addData(monitoringTransValue);	
						monitoringTransValue = 0;
					}
				}else if(type.equals(MonitoringController.MC_type4)){
					if(monitoringTransValue == 0){
						lineDataSet.addData(0);
						/*
						Random rand = new Random(System.currentTimeMillis());
						int testDate = rand.nextInt(30+ testCount);
						lineDataSet.addData(testDate);
						*/
					}else{
						lineDataSet.addData(monitoringTransValue);	
						monitoringTransValue = 0;
					}
				}
				testCount++;
					
			}
			if(type.equals(MonitoringController.MC_type1)){
				result = barData.addDataset(barDataSet);
			}else if(type.equals(MonitoringController.MC_type4)){
				result = lineData.addDataset(lineDataSet);
			}
			
		}
	return result;
	
	
		
	}
	private BarData getProcessingDelayStatusByTask(BarData barData, List<MonitoringVO> listMonitoringVo, ColorAutoDeployerTest cadt){
		
		try {
		Iterator<MonitoringVO> listMonitoringVoIterator = listMonitoringVo.iterator();
		
		BarDataset dataset = new BarDataset();
		int hashCodeNum = dataset.hashCode();
			while(listMonitoringVoIterator.hasNext()){
				MonitoringVO monitoringVoObject = listMonitoringVoIterator.next();
				barData.addLabel(monitoringVoObject.getDefName());			
				dataset.addData(monitoringVoObject.getDelayedCount());
				//dataset.addData(2);
				hashCodeNum++;
				dataset.addBackgroundColor(cadt.transColor(cadt.getColor(hashCodeNum)));
				
			}
			barData.addDataset(dataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return barData;
	}
	
	private JSONObject canvasJSONDataMaker(List<MonitoringVO> listMonitoringVo, String type){
		
		JSONArray datas = new JSONArray();
        
		Iterator<MonitoringVO> listMonitoringVoIterator = listMonitoringVo.iterator();
		while(listMonitoringVoIterator.hasNext()){
			MonitoringVO monitoringVoObject = listMonitoringVoIterator.next();
			JSONObject jsonObject = new JSONObject();
			if(type.equals(MonitoringController.MC_type3)){
				jsonObject.put("y", monitoringVoObject.getTotalCount());	
			}else if(type.equals(MonitoringController.MC_type2)){
				jsonObject.put("y", monitoringVoObject.getDelayedCount());
			}else{
				
			}
			
			jsonObject.put("label", monitoringVoObject.getDefName());
			datas.add(jsonObject);
		}
        JSONObject result = new JSONObject();
        result.put("datas", datas);
		return result;
	} 
	
	private JSONObject canvasSevendayJSONDataMaker(List<MonitoringVO> listMonitoringVo, String type, 
			ColorAutoDeployerTest cadt) {
		JSONArray result = new JSONArray();
		JSONObject resultObject = new JSONObject();
			Map<String, List<MonitoringVO>> monitoringVoManagerList = new HashMap<String, List<MonitoringVO>>();
			Iterator<MonitoringVO> monitoringVoListIterator = listMonitoringVo.iterator();
			while(monitoringVoListIterator.hasNext()){
				
					MonitoringVO monitoringVoObject = monitoringVoListIterator.next();
					if(monitoringVoManagerList.containsKey(monitoringVoObject.getDefName())){
						List<MonitoringVO> monitoringVOList = monitoringVoManagerList.get(monitoringVoObject.getDefName());
						monitoringVOList.add(monitoringVoObject);
						monitoringVoManagerList.put(monitoringVoObject.getDefName(), monitoringVOList);
					}else{
						List<MonitoringVO> monitoringVOList = new ArrayList<MonitoringVO>();
						monitoringVOList.add(monitoringVoObject);
						monitoringVoManagerList.put(monitoringVoObject.getDefName(), monitoringVOList);
					}
			}
			Iterator<String> monitoringVoManagerKeyList = monitoringVoManagerList.keySet().iterator();
			
			while(monitoringVoManagerKeyList.hasNext()){
				String mapKey = monitoringVoManagerKeyList.next();
				List<MonitoringVO> monitoringVOList = monitoringVoManagerList.get(mapKey);
				
				boolean isExistLabel = false;
				
				Map<String, Integer> jsonEachData = new HashMap<String, Integer>();
				int dayCount = 6;
				for(int i =0; i<= dayCount; i++){
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_WEEK, -(dayCount-i));
					Date date = calendar.getTime();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String eachDate = dateFormat.format(date);
					jsonEachData.put(eachDate, 0);
					
					
					JSONArray datas = new JSONArray();
					Iterator<MonitoringVO> monitoringVOListIterator = monitoringVOList.iterator();
					while(monitoringVOListIterator.hasNext()){
						
						MonitoringVO monitoringVoObject = monitoringVOListIterator.next();
						int monitoringTransValue = 0;
						Date monitoringDate = null;
						if(type.equals(MonitoringController.MC_type1)){
							monitoringDate = monitoringVoObject.getStartedDate();						
						}else if(type.equals(MonitoringController.MC_type4)){
							monitoringDate = monitoringVoObject.getFinishedDate();
						}
						SimpleDateFormat innerFormat = new SimpleDateFormat("yyyy-MM-dd");
						String dateString = innerFormat.format(monitoringDate);
						if(eachDate.equals(dateString)){
							if(type.equals(MonitoringController.MC_type1)){
								monitoringTransValue =  monitoringVoObject.getTotalCount();
								
							}else if(type.equals(MonitoringController.MC_type4)){
								monitoringTransValue = monitoringVoObject.getWorkingDayAVG();
							}else{
								
							}
							jsonEachData.put(eachDate, monitoringTransValue);
						}
					}
					}
				   JSONArray datas = new JSONArray();
				   Vector<String> sortVector = new Vector<String>(jsonEachData.keySet());
				   Collections.sort(sortVector);
				   Iterator<String> mapValueList = sortVector.iterator();
				   boolean isNotAllZero = false;
				   while(mapValueList.hasNext()){
					   String mapValueKey = mapValueList.next();					   
					   int mapValue = jsonEachData.get(mapValueKey);
					   JSONObject jsonObject = new JSONObject();
					   jsonObject.put("x", mapValueKey);
					   jsonObject.put("y", mapValue);
					   datas.add(jsonObject);
					   if(mapValue != 0){
						   isNotAllZero = true;
					   }
				   }
				   if(isNotAllZero){
					   JSONObject jsonArrayObject = new JSONObject();
					   jsonArrayObject.put("datas", datas);
					   jsonArrayObject.put("label", mapKey);
					  
					   try {
						jsonArrayObject.put("color", cadt.getColor(result.hashCode()));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					   result.add(jsonArrayObject);	
				   }
			}
			
			resultObject.put("result", result);
		return resultObject;
	}

	@Override
	public Object getProcessingStatusByTaskData(Map<String, String> map)
			throws Exception {
		List<MonitoringVO> listMonitoringVo = null;
		List<MonitoringVO> afterMonitoringVoList = new ArrayList<MonitoringVO>();
		List<String> beforePartNameList = new ArrayList<String>();
		List<String> afterPartNameList = new ArrayList<String>();
		List<String> beforeProcessTypeList = new ArrayList<String>();
		List<String> afterProcessTypeList = new ArrayList<String>();
		listMonitoringVo = monitoringDAO.getProcessingStatusByTask(map);
		Iterator<MonitoringVO> iteratorMonitoringVo = listMonitoringVo.iterator();
		while(iteratorMonitoringVo.hasNext()){
			MonitoringVO monitoringVo = iteratorMonitoringVo.next();
			String editProcessPath = monitoringVo.getPath();
			String editProcessPath2 = monitoringVo.getPath();
			editProcessPath = editProcessPath.substring(5);
			int subStringStartIndex = editProcessPath.indexOf(">");
			editProcessPath = editProcessPath.substring(0, subStringStartIndex);
			monitoringVo.setDefName(editProcessPath);
			
			int subStringStartIndex2 = editProcessPath2.lastIndexOf(">") + 1;
			editProcessPath2 = editProcessPath2.substring(subStringStartIndex2);
			monitoringVo.setPath(editProcessPath2);
			
			beforePartNameList.add(monitoringVo.getPartName());
			beforeProcessTypeList.add(editProcessPath);
			afterMonitoringVoList.add(monitoringVo);
		}
		for(String eachPartName : beforePartNameList){
			if(!afterPartNameList.contains(eachPartName)){
				afterPartNameList.add(eachPartName);
			}
		}
		
		for(String eachProcessPath : beforeProcessTypeList){
			if(!afterProcessTypeList.contains(eachProcessPath)){
				afterProcessTypeList.add(eachProcessPath);
			}
		}
		
		Collections.sort(afterPartNameList);
		Collections.sort(afterProcessTypeList);
		
        JSONObject result = new JSONObject();
        JSONArray resultArray = new JSONArray();
        Iterator<String> afterPartNameListIterator  = afterPartNameList.iterator();
        while(afterPartNameListIterator.hasNext()){
        	String eachPartName = afterPartNameListIterator.next();
        	Iterator<String> afterProcessTypeListIterator = afterProcessTypeList.iterator();
        	JSONObject depthOneObject = new JSONObject();
        	JSONArray depthOneArray = new JSONArray();
        	depthOneObject.put("partName", eachPartName);
        	while(afterProcessTypeListIterator.hasNext()){
        		String eachProcessPath = afterProcessTypeListIterator.next();
        		Iterator<MonitoringVO> iteratorMonitoringVO = afterMonitoringVoList.iterator();
        		JSONObject depthTwoObject = new JSONObject();
            	depthTwoObject.put("processType", eachProcessPath);
            	JSONArray depthTwoArray = new JSONArray();
                while(iteratorMonitoringVO.hasNext()){
                	MonitoringVO monitoringVO = iteratorMonitoringVO.next();
                	if (UEngineUtil.isNotEmpty(monitoringVO.getPartName()) &&
                			UEngineUtil.isNotEmpty(monitoringVO.getDefName())) {
                		if (eachPartName.equals(monitoringVO.getPartName())){
            				if(eachProcessPath.equals(monitoringVO.getDefName())){
            					JSONObject depthThreeObject = new JSONObject();
            					depthThreeObject.put("path", monitoringVO.getPath());
            					depthThreeObject.put("workingDayAVG", monitoringVO.getWorkingDayAVG());
            					depthThreeObject.put("workingDayMin", monitoringVO.getWorkingDayMin());
            					depthThreeObject.put("workingDayMax", monitoringVO.getWorkingDayMax());
            					depthThreeObject.put("totalCount", monitoringVO.getTotalCount());
            					depthTwoArray.add(depthThreeObject);
            				}
                		}
                	}
                }
                depthTwoObject.put("depthTwoArray", depthTwoArray);
                depthOneArray.add(depthTwoObject);
        	}
        	depthOneObject.put("depthOneArray", depthOneArray);
        	resultArray.add(depthOneObject);
        }
        result.put("datas", resultArray);
     
		return result;
	}

}
