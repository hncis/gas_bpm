package com.defaultcompany.web.strategy;

import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.DefaultConnectionFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class StrategyService {
	
	private StrategyDAO strategyDAO;
	
	private StrategySearcher strategySearcher;
	
	public StrategyService() {
		strategyDAO = new StrategyDAO(((DefaultConnectionFactory) DefaultConnectionFactory.create()).getDataSource());
		strategySearcher = new StrategySearcher();
	}
	
	public void update(int id, String name, int parentId, String type, String isDeleted) {
		Strategy strategy = this.getById(id);
		if (name != null) {
			strategy.setStrategyName(name);
		}
		if (type != null) {
			strategy.setType(type);
		}
		if (isDeleted != null) {
			strategy.setIsDeleted(isDeleted);
		}
		strategyDAO.update(strategy);
	}
	
	public void update(Strategy strategy) {
		strategyDAO.update(strategy);
	}
	
	public void move(int parentId ,int id) {
		strategyDAO.move(parentId, id);
		setChildrenCnt(id);
	}
	
	public void setChildrenCnt(int id){
		List<Strategy> strategyList = this.getList();
		int cnt  = getChildCnt(strategyList,Integer.parseInt(""+id), new ArrayList<Integer>());
		//strategyDAO.updateCMPLTINSTCNT(id, cnt);
	}
	
	public void setParentId(int parentId ,int id) {
		strategyDAO.setParentId(parentId, id);
		setChildrenCnt(id);
	}
	
	public Strategy getById(int id) {
		return strategyDAO.getById(id);
	}
	
	public List<Strategy> getChildStrategyListById(int id) {
		return strategyDAO.getChildStrategyListById(id);
	}
	
	public List<Strategy> getList() {
		return strategyDAO.getList();
	}
	
	public List<Integer> getInstanceIdListById(int id) {
		return strategyDAO.getInstanceIdListById(id);
	}
	
	public List<Integer> getStrategyIdListById(int id) {
		return recursive(this.getChildStrategyListById(id));
	}
	
	public List<Integer> getParentById(int id) {
		return strategyDAO.getParentIds(id);
	}
	
	private List<Integer> recursive(List<Strategy> strategys) {
		List<Integer> strategyIds = new ArrayList<Integer>();
		
		for (Strategy stg : strategys) {
			strategyIds.add(stg.getStrategyId());
			strategyIds.addAll(recursive(this.getChildStrategyListById(stg.getStrategyId())));
		}
		return strategyIds;
	}
	
	private int getChildCnt(List<Strategy> strategyList,int parentId,List<Integer> children) {
		for (int i = 0; i < strategyList.size(); i++) {
			Strategy strategyTmp = (Strategy)strategyList.get(i);
			List<Integer> parentIdList = strategyDAO.getParentIds(strategyTmp.getStrategyId());
			for (int j = 0; j < parentIdList.size(); j++) {
				int parentIdTmp = ((Integer)parentIdList.get(j)).intValue();
				if(parentId == parentIdTmp){
					children.add(parentId);
					getChildCnt(strategyList, strategyTmp.getStrategyId(), children);
				}
			}
		}
		
		return children.size();
	}
	
	private Hashtable<String, Item> setChildrenNode(Hashtable<String, Item> treeList, Item parentItem , Hashtable<String, Strategy> strategyList ) {
		List<Item> childItems = new ArrayList<Item>();

		for (Entry<String, Item> entry : treeList.entrySet()) {
			String key = entry.getKey();
			Item item = entry.getValue();
			
			if (item != null && item.getParent() !=null) {
				List<Integer> parentList = item.getParent();
				
				for (int i = 0; i < parentList.size(); i++) {
					String parentTmp = String.valueOf(parentList.get(i));
					if( parentTmp.equals(parentItem.getId())) {
						
						treeList = setChildrenNode(treeList, item , strategyList);
						Item newItem= (Item)item.clone();
						
						int childrenCnt=0;
						List<Item> childItemsTmp =  newItem.getChildren();
						for (int j = 0; j < childItemsTmp.size(); j++) {
							Item childItem = (Item)childItemsTmp.get(j);
							childrenCnt +=childItem.getChildrenCnt()+1;
						}
						newItem.setChildrenCnt(childrenCnt);
						
						Strategy strategy = ((Strategy)strategyList.get(newItem.getId()));
						
						String color = "";
						if (Strategy.STRATEGY_STATUS_COMPLETED.equals(strategy.getStatus())) {
							color = "strategyBg02";
						} else {
							color = "strategyBg01";
						}
						
						String label = strategy.getStrategyName();
						int divBorderSize=1;
						String divBorderColor="#CFCFCF";
						try{
							Calendar nowCal = Calendar.getInstance();
							nowCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH)+1, nowCal.get(Calendar.DAY_OF_MONTH),0,0,0);
												
							Calendar endDateCal = Calendar.getInstance();
							endDateCal.setTime(strategy.getEndDate());
							endDateCal.set(endDateCal.get(Calendar.YEAR), endDateCal.get(Calendar.MONTH)+1, endDateCal.get(Calendar.DAY_OF_MONTH),23,59,59);
							
							int isDelay = nowCal.getTime().compareTo(endDateCal.getTime()) ;
							if(isDelay == 1 && !Strategy.STRATEGY_STATUS_COMPLETED.equals(strategy.getStatus())){
								//label = "<font color=#213D4E style=font-size:11px>"+label+"</font>";
								label = "<font color=RED style=font-size:11px>"+label+"</font>";
							}
							
							if(strategySearcher.getStrategySearchList().containsKey(String.valueOf(strategy.getStrategyId()))){
								label = "<b>"+label+"</b>";
								divBorderSize=3;
								divBorderColor="#FFB400";
							}
						}catch (Exception e) {}
						
						String startDate = "";
						String endDate = "";
						try{
							startDate = strategy.getStartDate().toString().substring(0, 10);
							endDate = strategy.getEndDate().toString().substring(0, 10);
						}catch (Exception e) {}
						
						int completed=0;
						int requested=0;
						try{
							List<Instance> instanceList = getChildInstanceIdListById(treeList,newItem);
							requested = instanceList.size();
							for (int j = 0; j < instanceList.size(); j++) {
								Instance instance=instanceList.get(j);
								if("Completed".equals(instance.getStatus())){
									completed++;
								}
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						String name ="<div id='strategy"+newItem.getId()+"' "+
									 "onmouseover=\"enableTooltips('strategy"+newItem.getId()+"')\" style='background:url(../images/Common/" + color + ".gif) #fff bottom repeat-x; width:85px; border:"+divBorderSize+"px solid "+divBorderColor+"; "+("PROPOSED".equals(strategy.getStatus()) ? "FILTER:alpha(opacity=50);":"")+"'> "+
									 "<table><tr><td><img src='images/strategy_"+strategy.getType()+".gif' style='margin:3px' "+
									 "strtgDesc=\"Name=="+label+";Children Cnt=="+ childrenCnt +";Completed/Requested=="+completed+"/" + requested+";Status=="+strategy.getStatus()+";Group in charge=="+strategy.getPartName()+";Start Date=="+startDate+";End Date=="+endDate+";Description=="+strategy.getDescription()+";\">"+
									 "</td><td style='padding:3px;'><a>"+ label+ "</a></td></tr></table></div>";

						newItem.setName(name);
						
						childItems.add(newItem);
						treeList.put(String.valueOf(newItem.getId()), newItem);
					}
				}
			}
		}		

		parentItem.getChildren().addAll(childItems);
		
		return treeList;
	}
	
	private List<Instance> getChildInstanceIdListById(Hashtable<String, Item> treeList,Item item) {
		//List<Integer> strategyIdList= getStrategyIdListById(id);
		List<Integer> strategyIdList = getChildrenList(treeList,new ArrayList<Integer>(), item);
		strategyIdList.add(new Integer(""+item.getId()));
		return strategyDAO.getInstanceIdListById(strategyIdList);
	}
	
	private List<Integer> getChildrenList(Hashtable<String, Item> treeList,List<Integer> childItems,Item newParentItem){
		for (Entry<String, Item> entry : treeList.entrySet()) {
			String key = entry.getKey();
			Item item = entry.getValue();
			
			if (item != null && item.getParent() !=null){
				List<Integer> parentList =item.getParent();
				
				for (int i = 0; i < parentList.size(); i++) {
					String parentTmp = String.valueOf(parentList.get(i));
					if( parentTmp.equals(newParentItem.getId())) {
						childItems.add(new Integer(String.valueOf(item.getId())));
						childItems = getChildrenList(treeList,childItems, item);
					}
				}
			}
		}
		
		return childItems;
	}
	
	private void searchMap(Hashtable<String, Item> treeList,Item newParentItem){
		List<Item> childItems = new ArrayList<Item>();

		for (Entry<String, Item> entry : treeList.entrySet()) {
			String key = entry.getKey();
			Item item = entry.getValue();
			
			if (item != null && item.getParent() !=null){
				List<Integer> parentList = item.getParent();
				
				for (int i = 0; i < parentList.size(); i++) {
					String parentTmp = String.valueOf(parentList.get(i));
					if( parentTmp.equals(newParentItem.getId()) && isContainSearchItem(treeList,item)) {
						searchMap(treeList, item);
						
						Item newItem= (Item)item.clone();
						childItems.add(newItem);
					}
				}
			}
		}
		
		newParentItem.setChildren(childItems);
	}
		
	private boolean isContainSearchItem(Hashtable<String, Item> treeList,Item newParentItem){
		if(strategySearcher.getStrategySearchList().containsKey(String.valueOf(newParentItem.getId()))){
			return true;
		}else{
			for (Entry<String, Item> entry : treeList.entrySet()) {
				String key = entry.getKey();
				Item item = entry.getValue();
				
				if (item != null && item.getParent() !=null){
					List<Integer> parentList = item.getParent();
					
					for (int i = 0; i < parentList.size(); i++) {
						String parentTmp = String.valueOf(parentList.get(i));
						if( parentTmp.equals(newParentItem.getId())) {
							
							boolean isExist = isContainSearchItem(treeList, item);
							if(isExist) return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private void searchCondition(Item item, Strategy strategy) {
		
		if(UEngineUtil.isNotEmpty(strategySearcher.getName())){
			if(strategy.getStrategyName().indexOf((strategySearcher.getName()))>-1){
				strategySearcher.getStrategySearchList().put(""+item.getId(),item);
			}
		}else{
			boolean isPartIncluded = false;
			if(UEngineUtil.isNotEmpty(strategySearcher.getPartCode()) && 
			   strategySearcher.getPartCode().equals(strategy.getPartCode())){
				
			   isPartIncluded =true;
			}
			
			boolean isPeriodIncluded = false;
			if(UEngineUtil.isNotEmpty(strategySearcher.getPeriod()) &&
				strategy.getStartDate() !=null &&
				strategy.getEndDate() !=null){
				
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(strategy.getStartDate());
				startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),0,0,0);

				Calendar endDate = Calendar.getInstance();
				endDate.setTime(strategy.getEndDate()) ;			
				endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH),23,59,59);
				
				Calendar now = Calendar.getInstance();
				if(StrategySearcher.PERIOD_WEEK.equals(strategySearcher.getPeriod()) ){
					int day_of_week =now.get(Calendar.DAY_OF_WEEK);
					
					int cal_week=0;
					if(UEngineUtil.isNotEmpty(strategySearcher.getSelectedPeriod())){
						int selected_week_of_month = Integer.parseInt(strategySearcher.getSelectedPeriod());
						int week_of_month = now.get(Calendar.WEEK_OF_MONTH);
						
						cal_week=(selected_week_of_month - week_of_month)*7;
					}
					
					Calendar min_day = Calendar.getInstance();
					min_day.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), (now.get(Calendar.DAY_OF_MONTH)-(day_of_week-1))+cal_week,0,0,0);
									
					Calendar max_day = Calendar.getInstance();
					max_day.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), (now.get(Calendar.DAY_OF_MONTH)+(7-day_of_week))+cal_week,23,59,59);
				
					int start = min_day.getTime().compareTo(endDate.getTime()) ;
					int end = max_day.getTime().compareTo(endDate.getTime()) ;
					
					if(start < 1 && end > -1 ){
						isPeriodIncluded =true;
					}
				}else if(StrategySearcher.PERIOD_MONTH.equals(strategySearcher.getPeriod()) ){
					int month=0;
					if(UEngineUtil.isNotEmpty(strategySearcher.getSelectedPeriod())){
						month = Integer.parseInt(strategySearcher.getSelectedPeriod())-1;
					}else{
						month = now.get(Calendar.MONTH);
					}
					
					Calendar min_day = Calendar.getInstance();
					min_day.set(now.get(Calendar.YEAR), month,1 ,0,0,0);
					
					Calendar max_day = Calendar.getInstance();
					max_day.set(now.get(Calendar.YEAR), month, now.getActualMaximum(Calendar.DAY_OF_MONTH),23,59,59);
					
					int start = min_day.getTime().compareTo(strategy.getEndDate()) ;
					int end = max_day.getTime().compareTo(strategy.getEndDate()) ;
					
					if(start < 1 && end > -1 ){
						isPeriodIncluded =true;
					}
				}else if(StrategySearcher.PERIOD_YEAR.equals(strategySearcher.getPeriod()) ){
					int year=0;
					if(UEngineUtil.isNotEmpty(strategySearcher.getSelectedPeriod())){
						year = Integer.parseInt(strategySearcher.getSelectedPeriod());
					}else{
						year = now.get(Calendar.YEAR);
					}
					
					Calendar min_day = Calendar.getInstance();
					min_day.set(year, 0, 1, 0, 0, 0);
					
					Calendar max_day = Calendar.getInstance();
					max_day.set(year, 11, 31, 23, 59, 59);
					
					int start = min_day.getTime().compareTo(endDate.getTime()) ;
					int end = max_day.getTime().compareTo(endDate.getTime()) ;
					
					if(start < 1 && end > -1 ){
						isPeriodIncluded =true;
					}
				}else if(StrategySearcher.PERIOD_QUARTER.equals(strategySearcher.getPeriod()) ){
					int month=0;
					if(UEngineUtil.isNotEmpty(strategySearcher.getSelectedPeriod())){
						month = Integer.parseInt(strategySearcher.getSelectedPeriod());
						month *=3;
					}else{
						month = now.get(Calendar.MONTH);
					}
					
					int min_month=0;
					int max_month=0;				
					if(month <= 3){
						min_month=1;
						max_month=3;
					}else if(month >= 4 && month <= 6){
						min_month=4;
						max_month=6;					
					}else if(month >= 7 && month <= 9){
						min_month=7;
						max_month=9;					
					}else if(month >= 10 ){
						min_month=10;
						max_month=12;					
					}
					
					Calendar min_day = Calendar.getInstance();
					min_day.set(now.get(Calendar.YEAR), min_month,1 ,0,0,0);
					
					Calendar max_day = Calendar.getInstance();
					max_day.set(now.get(Calendar.YEAR), max_month, 31,23,59,59);
					
					int start = min_day.getTime().compareTo(endDate.getTime()) ;
					int end = max_day.getTime().compareTo(endDate.getTime()) ;
					
					if(start < 1 && end > -1 ){
						isPeriodIncluded =true;
					}
				}
			}
			
			if(UEngineUtil.isNotEmpty(strategySearcher.getPartCode())&&
			   UEngineUtil.isNotEmpty(strategySearcher.getPeriod())	){    // period  and part
				if(isPartIncluded &&isPeriodIncluded )
					strategySearcher.getStrategySearchList().put(""+item.getId(),item);
				
				if(strategySearcher.isIncludingIsNotCompleted()&& 
				  (Strategy.STRATEGY_STATUS_APPROVED.equals(strategy.getStatus()) ||Strategy.STRATEGY_STATUS_PROPOSED.equals(strategy.getStatus())) &&
				  isPartIncluded){

					Calendar today = Calendar.getInstance();
					today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH),0,0,0);
						
					Calendar endDate = Calendar.getInstance();
					endDate.setTime(strategy.getEndDate()) ;			
					endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)+1, endDate.get(Calendar.DAY_OF_MONTH));
					
					int start = today.getTime().compareTo(endDate.getTime()) ;
								
					if(start == 1 ){
						strategySearcher.getStrategySearchList().put(""+item.getId(),item);
					}
				}
				
			}else if(!UEngineUtil.isNotEmpty(strategySearcher.getPartCode())&&
					 UEngineUtil.isNotEmpty(strategySearcher.getPeriod())	){ // period  
				if(!isPartIncluded &&isPeriodIncluded )
					strategySearcher.getStrategySearchList().put(""+item.getId(),item);
				
				if(strategySearcher.isIncludingIsNotCompleted()&& 
				  (Strategy.STRATEGY_STATUS_APPROVED.equals(strategy.getStatus()) ||Strategy.STRATEGY_STATUS_PROPOSED.equals(strategy.getStatus()))){

					Calendar today = Calendar.getInstance();
					today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH),0,0,0);
								
					Calendar endDate = Calendar.getInstance();
					endDate.setTime(strategy.getEndDate()) ;			
					endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)+1, endDate.get(Calendar.DAY_OF_MONTH));
					
					int start = today.getTime().compareTo(endDate.getTime()) ;
										
					if(start == 1 ){
						strategySearcher.getStrategySearchList().put(""+item.getId(),item);
					}
				}
			}else if(UEngineUtil.isNotEmpty(strategySearcher.getPartCode())&&
					 !UEngineUtil.isNotEmpty(strategySearcher.getPeriod())	){ // part  
				if(isPartIncluded && !isPeriodIncluded )
					strategySearcher.getStrategySearchList().put(String.valueOf(item.getId()), item);
			}
		}
	}
	
	private Item setRootNode(Hashtable<String, Item> items, Hashtable<String, Strategy> strategyList) {
		Item rootItem = null;
		for (int i = 0; i < items.size(); i++) {
			rootItem = items.get(String.valueOf(i));
			if (rootItem != null) {
				break;
			}
		}
		
		if(strategySearcher.isStrategySearch() ){
			Item itemClone = (Item) rootItem.clone();
			searchMap(items, itemClone);
			rootItem = itemClone;
		}
		
		int childrenCnt=0;
		List<Item> childItemsTmp =  rootItem.getChildren();
		for (int j = 0; j < childItemsTmp.size(); j++) {
			Item childItem = (Item)childItemsTmp.get(j);
			childrenCnt +=childItem.getChildrenCnt()+1;
		}
		rootItem.setChildrenCnt(childrenCnt);
		
		Strategy strategy = ((Strategy)strategyList.get(String.valueOf(rootItem.getId())));
		
		int completed=0;
		int requested=0;
		try{
			List<Instance> instanceList = getChildInstanceIdListById(items,rootItem);
			requested = instanceList.size();
			for (int j = 0; j < instanceList.size(); j++) {
				Instance instance=instanceList.get(j);
				if("Completed".equals(instance.getStatus())){
					completed++;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		String name ="<div id='strategy"+rootItem.getId()+"' "+
									 "onmouseover=\"enableTooltips('strategy"+rootItem.getId()+"')\" style='background:url(../images/Common/strategyBg01.gif) repeat-x; white-space: nowrap; border:1px solid #ccc; padding:3px; width:60px;'> "+
									 "<table><tr><td><img src='images/strategy_"+strategy.getType()+".gif' style='margin:3px 0 0 3px;' "+
									 "strtgDesc='Name=="+strategy.getStrategyName()+";Children Cnt=="+ childrenCnt +";Completed/Requested=="+completed+"/" + requested+";Company=="+strategy.getComCode()+";'>"+
									 "</td><td><a style='text-decoration:none; font-weight:bold;'>"+
									 strategy.getStrategyName()+
									 "</td></tr></div>";
		
		rootItem.setName(name);
		
		return rootItem;
	}
	
	public String allStrategyToJSON() {
		return allStrategyToJSON("","","","",false);
	}
	
	public String allStrategyToJSON(String name,String selectedPeriod,String partCode,String period,boolean includingIsNotCompleted) {
		strategySearcher.setPartCode(partCode);
		strategySearcher.setPeriod(period);
		strategySearcher.setSelectedPeriod(selectedPeriod);
		strategySearcher.setIncludingIsNotCompleted(includingIsNotCompleted);
		strategySearcher.setName( name);
		
		List<Strategy> strategyList = this.getList();
		Hashtable<String, Item> items = new Hashtable<String, Item>(); 
		Hashtable<String, Strategy> strategyMap = new Hashtable<String, Strategy>();
		
		for (Strategy strategy : strategyList) {
			Item item = new Item();
			item.setId(String.valueOf(strategy.getStrategyId()));
			
			item.setType(strategy.getType());
			
			List<Integer> parentList = strategyDAO.getParentIds(strategy.getStrategyId());
			boolean isRoot=false;
			if(parentList.size()==0 ){ //is root
				parentList.add(-1);
				isRoot=true;
			}
			item.setParent(parentList);
			item.getData();
			
			items.put(String.valueOf(strategy.getStrategyId()),item);
			
			if(strategySearcher.isStrategySearch() && !isRoot )
				searchCondition(item,strategy);
			
			strategyMap.put(String.valueOf(strategy.getStrategyId()), strategy);
		}
		
        List<Item> jsonItem = new ArrayList<Item>();
		
        items = setChildrenNode(items, new Item("-1"), strategyMap);
		
        jsonItem.add(setRootNode(items, strategyMap));
        
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        
        String jsonStr = xstream.toXML(jsonItem);
		return jsonStr.substring(1, jsonStr.length() - 1);
	}

}
