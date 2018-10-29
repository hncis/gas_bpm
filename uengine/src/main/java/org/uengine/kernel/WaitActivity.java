package org.uengine.kernel;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.ejb.RemoveException;

import org.apache.commons.lang.StringUtils;
import org.metaworks.EnablingDependancy;
import org.metaworks.FieldDescriptor;
import org.metaworks.Type;
import org.metaworks.inputter.RadioInput;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.processmanager.TransactionContext;
import org.uengine.queue.messagequeue.MessageProcessorBean;
import org.uengine.scheduler.*;

import org.uengine.util.UEngineUtil;
import org.uengine.util.dao.DefaultConnectionFactory;
import java.sql.*;


/**
 * @author Jinyoung Jang
 */

public class WaitActivity extends ReceiveActivity{
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	private static final String WAITING_TYPE_WHILE = "WAIT_WHILE";
	private static final String WAITING_TYPE_PERIOD = "WAIT_PERIOD";
	private static final String WAITING_TYPE_UNTIL = "WAIT_UNTIL";
	
	//2009.08.21 added by sh.kim
	//schedule option added.
	public static void metaworksCallback_changeMetadata(Type type){
		type.setName("Wait Activity");
		
		FieldDescriptor fd = null;
			
		fd = type.getFieldDescriptor("Message");
		type.removeFieldDescriptor(fd);
		
		fd = type.getFieldDescriptor("Parameters");
		type.removeFieldDescriptor(fd);
		
		fd = type.getFieldDescriptor("MessageDefinition");
		type.removeFieldDescriptor(fd);
		
		fd = type.getFieldDescriptor("FromRole");
		type.removeFieldDescriptor(fd);
		
		fd = type.getFieldDescriptor("HowWait");
		fd.setInputter(new RadioInput(
				new String[]{
						"while for given 'millisecond'",
						"for the next occurrance set by 'Cron Expression'",
						"until given 'WaitUntil'",
				}, new Object[]{
						WAITING_TYPE_WHILE, 
						WAITING_TYPE_PERIOD, 
						WAITING_TYPE_UNTIL, 
				}
			)
		);

		
		fd = type.getFieldDescriptor("ScheCronExp");
		fd.setAttribute("dependancy", new EnablingDependancy("HowWait"){

			public boolean enableIf(Object dependencyFieldValue) {
				return WAITING_TYPE_PERIOD.equals(dependencyFieldValue);
			}
			
		});
		
		fd = type.getFieldDescriptor("WaitUntil");
		fd.setAttribute("dependancy", new EnablingDependancy("HowWait"){

			public boolean enableIf(Object dependencyFieldValue) {
				return WAITING_TYPE_UNTIL.equals(dependencyFieldValue);
			}
			
		});

		fd = type.getFieldDescriptor("MilliSecond");
		fd.setAttribute("dependancy", new EnablingDependancy("HowWait"){

			public boolean enableIf(Object dependencyFieldValue) {
				return WAITING_TYPE_WHILE.equals(dependencyFieldValue);
			}
			
		});

		type.setFieldOrder(new String[]{
			"HowWait", "MilliSecond", "WaitUntil", "ScheCronExp", "StatusCode", "Cost", "ActivityIcon"	
		});
		
		type.removeFieldDescriptor("Schedule"); //remove isSchedule due to the waiting type exceeds two kinds of selection
		
		
	}
	
	public WaitActivity(){
		super();
		setName("Wait");
		this.setScheCronExp(new ScheCronExp());
	}
	
	
	
	
	public String getMessage() {
		return new StringBuilder().append("onWaitAcitivity").append(this.getTracingTag()).append("Done").toString();
	}
	
	String howWait;
	
		public String getHowWait() {
			
			//consideration for deprecated option 'isSchedule'
			if(howWait==null){
				setHowWait(isSchedule() ? WAITING_TYPE_PERIOD:WAITING_TYPE_WHILE);
			}
			
			return howWait;
		}
	
		public void setHowWait(String waitingType) {
			this.howWait = waitingType;
		}

	public void executeActivity(ProcessInstance instance) throws Exception {
		
		super.executeActivity(instance);
		
		final String instanceId = instance.getInstanceId();
		

		
		String howWait = getHowWait();
		
		if(GlobalContext.useEJB){
			MessageProcessorBean.queueMessage(getMessage(), instance.getInstanceId(), null);
		} else if ( WAITING_TYPE_WHILE.equals(howWait) ){
			final Thread waiter = new Thread(){

				public void run() {
					ProcessManagerFactoryBean pmfb = new ProcessManagerFactoryBean();
					ProcessManagerRemote pm = null;
					ProcessInstance instance = null;
					
					try {
						pm = pmfb.getProcessManager();
						instance = pm.getProcessInstance(instanceId);
					} catch (Exception e2) {
						try {
							pm.remove();
						} catch (RemoteException e) {
							e.printStackTrace();
						} catch (RemoveException e) {
							e.printStackTrace();
						}
						return;
					}

					try {
						Thread.sleep(getMilliSecond());
						fireComplete(instance);
						pm.applyChanges();
					} catch (Exception e) {
						try {
							pm.cancelChanges();
							pm.remove();
							
							pm = pmfb.getProcessManager();
							instance = pm.getProcessInstance(instanceId);

							fireFault(instance, e);
							
							pm.applyChanges();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} finally{
						try {
							pm.remove();
						} catch (RemoteException e) {
							e.printStackTrace();
						} catch (RemoveException e) {
							e.printStackTrace();
						}
					}
				}
				
			};
			
			instance.getProcessTransactionContext().addTransactionListener(new TransactionListener(){
				public void beforeCommit(TransactionContext tx) throws Exception {
				}
				public void beforeRollback(TransactionContext tx) throws Exception {
				}
				public void afterCommit(TransactionContext tx) throws Exception {
					waiter.start();
				}
				public void afterRollback(TransactionContext tx) throws Exception {
					waiter.start();
				}
			});

		} else if ( WAITING_TYPE_PERIOD.equals(howWait)  ) {
			
			Calendar modifyCal = new GregorianCalendar();
			
			// type == true is Manual Setting, false is Auto
			if (this.getScheCronExp().isType() == true) {
				ManualScheCronExp manualScheCronExp = this.getScheCronExp().getManualScheCronExp();
				
				String manualInput = evaluateContent(instance, manualScheCronExp.getManualInput()).toString();
				
				if (!UEngineUtil.isNotEmpty(manualInput)) {
				
					String cronExpression = manualScheCronExp.getCronExpression();
					modifyCal = SchedulerUtil.getCalendarByCronExpression(cronExpression);
					
				} else { // Input to ProcessVariable
					modifyCal = SchedulerUtil.getCalendarByCronExpression(manualInput);
				}
			} else { //Auto
					
				AutoScheCronExp autoScheCronExp = this.getScheCronExp().getAutoScheCronExp();
				
				String minutesStr = evaluateContent(instance, autoScheCronExp.getMinutes()).toString();
				String hoursStr = evaluateContent(instance, autoScheCronExp.getHours()).toString();
				String daysStr = evaluateContent(instance, autoScheCronExp.getDays()).toString();
				String monthsStr = evaluateContent(instance, autoScheCronExp.getMonths()).toString();
				String yearsStr = evaluateContent(instance, autoScheCronExp.getYears()).toString();
				
				int minutes = UEngineUtil.isNotEmpty(minutesStr) ? Integer.parseInt(minutesStr) : -1; 
				int hours = UEngineUtil.isNotEmpty(hoursStr) ? Integer.parseInt(hoursStr) : -1;
				int days = UEngineUtil.isNotEmpty(daysStr) ? Integer.parseInt(daysStr) : -1;
				int months = UEngineUtil.isNotEmpty(monthsStr) ? Integer.parseInt(monthsStr) : -1;
				int years = UEngineUtil.isNotEmpty(yearsStr) ? Integer.parseInt(yearsStr) : -1;
				
				/*
				 * 조건에는 없지만 DAY_OF_WEEK 의 경우 증가 시킨다고 해서 반드시 미래시간이 나오지 않는다. 같은 주상에서 일 ~ 월 만 반복할 뿐이다.
				 */
				
				if (years != -1) {
					modifyCal.set(Calendar.YEAR, modifyCal.get(Calendar.YEAR) + years);
				}
				
				if (months != -1) {
					modifyCal.set(Calendar.MONTH, modifyCal.get(Calendar.MONTH) + months);
				}
				
				if (days != -1) {
					modifyCal.set(Calendar.DAY_OF_MONTH, modifyCal.get(Calendar.DAY_OF_MONTH) + days);
				}
				
				if (hours != -1) {
					modifyCal.set(Calendar.HOUR_OF_DAY, modifyCal.get(Calendar.HOUR_OF_DAY) + hours);
				}
				
				if (minutes != -1) {
					modifyCal.set(Calendar.MINUTE, modifyCal.get(Calendar.MINUTE) + minutes);
				}	
				
				modifyCal.set(Calendar.MILLISECOND, 0);
			}
			
			System.out.println("schedule : \t" + modifyCal.getTime().toLocaleString());
			
//			instance.getProcessTransactionContext().addTransactionListener(
//					new ScheduleTransactionListener(instance, this.getTracingTag(), modifyCal)
//			);
			this.addSchedule(instance, this.getTracingTag(), modifyCal);

		} else if ( WAITING_TYPE_UNTIL.equals(howWait)  ) {
			
			Object waitUntil = getWaitUntil().get(instance, "");
			
			if (waitUntil != null) {
				Calendar cal = new GregorianCalendar();
				
				if (waitUntil.getClass() != Date.class
						&& waitUntil.getClass() != Calendar.class
						&& waitUntil.getClass() != GregorianCalendar.class) {
					throw new UEngineException("WaitUntil variable should be a Date type");
				}
				
				if (waitUntil.getClass() == Date.class) {
					cal.setTimeInMillis(((Date)waitUntil).getTime());
				} else if (waitUntil.getClass() == GregorianCalendar.class) {
					cal.setTime(((GregorianCalendar)waitUntil).getTime());
				}
				cal.set(Calendar.MILLISECOND, 0);
			
//				instance.getProcessTransactionContext().addTransactionListener(
//						new ScheduleTransactionListener(instance, this.getTracingTag(), cal)
//				);
				this.addSchedule(instance, this.getTracingTag(), cal);

			}else{
				fireComplete(instance); //if there's no given until-time, don't wait
			}
			
						
		}
			
	}
	
//	private class ScheduleTransactionListener implements TransactionListener {
//
//		private ProcessInstance instance;
//		private String tracingTag;
//		private Calendar cal;
//		
//		public ScheduleTransactionListener(ProcessInstance instance,
//				String tracingTag, Calendar modifyCal) {
//			this.instance = instance;
//			this.tracingTag = tracingTag;
//			this.cal = modifyCal;
//		}
//
//		public void afterCommit(TransactionContext tx) throws Exception {
//			addSchedule(this.instance, this.tracingTag, this.cal);
//		}
//
//		public void afterRollback(TransactionContext tx) throws Exception {	
//		}
//
//		public void beforeCommit(TransactionContext tx) throws Exception {
//		}
//
//		public void beforeRollback(TransactionContext tx) throws Exception {	
//		}
//		
//	}

	/**
	 * Save schedule in DB.
	 * @throws Exception 
	 */
	protected void addSchedule(ProcessInstance instance, String tracingTag, Calendar modifyCal) throws Exception { 		
		Connection conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		String sqlOldSchDelete =  "DELETE FROM SCHEDULE_TABLE WHERE INSTID = ? AND TRCTAG = ? ";
		String sqlSEQ = "SELECT MAX(SCHE_IDX) FROM SCHEDULE_TABLE";
		String sql = " INSERT INTO SCHEDULE_TABLE(SCHE_IDX, INSTID, TRCTAG, STARTDATE) VALUES(?, ?, ?, ?) "; 
        
		try {
//			conn = DefaultConnectionFactory.create().getConnection();
//			conn.setAutoCommit(false);
			conn = instance.getProcessTransactionContext().getConnection();
			
			pstmt = conn.prepareStatement(sqlOldSchDelete);
			pstmt.setString(1, instance.getInstanceId());
			pstmt.setString(2, tracingTag);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sqlSEQ);
			rs = pstmt.executeQuery();

			int max = 0;
			if (rs.next()) {
				max = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, max == 0 ? 1 : max + 1);
			pstmt.setString(2, instance.getInstanceId());
			pstmt.setString(3, tracingTag);
			pstmt.setTimestamp(4, new Timestamp(modifyCal.getTimeInMillis()));
			pstmt.executeUpdate();
			
//			conn.commit();
		} catch (Exception e) {
//			if (conn != null) try { conn.rollback(); } catch (Exception e1) { }
			throw e;
		}  finally {
        	if (rs!= null) try { rs.close(); } catch (Exception e) { }
            if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
//            if (conn != null) {
//            	conn.setAutoCommit(true);
//                try {
//                    conn.close();
//                } catch (Exception e) { }
//            }
        }

       
	}
	
	/**
	 * @deprecated rather use waitingType
	 */
	@Deprecated
	boolean isSchedule;
		@Deprecated
		public boolean isSchedule() {
			return isSchedule;
		}
		@Deprecated
		public void setSchedule(boolean isSchedule) {
			this.isSchedule = isSchedule;
		}

	long milliSecond;
		public long getMilliSecond() {
			return milliSecond;
		}
		public void setMilliSecond(long milliSecond) {
			this.milliSecond = milliSecond;
		}
		
	ScheCronExp scheCronExp;
		public ScheCronExp getScheCronExp() {
			return scheCronExp;
		}
	
		public void setScheCronExp(ScheCronExp scheCronExp) {
			this.scheCronExp = scheCronExp;
		}
		
	ProcessVariable waitUntil;
		public ProcessVariable getWaitUntil() {
			return waitUntil;
		}
		public void setWaitUntil(ProcessVariable waitUntil) {
			this.waitUntil = waitUntil;
		}

	protected void onReceive(ProcessInstance instance, Object payload) throws Exception {
		Thread.sleep(getMilliSecond());
		
		super.onReceive(instance, payload);
	}

	protected void afterExecute(ProcessInstance instance) throws Exception {
		super.afterExecute(instance);
	}

	@Override
	public ValidationContext validate(Map options) {
		ValidationContext vc = super.validate(options);

		if (WAITING_TYPE_WHILE.equals(this.getHowWait()) && this.getMilliSecond() < 1000) {
			vc.add("MilliSecond must be higher than 1000");
		}
		
		if (WAITING_TYPE_UNTIL.equals(this.getHowWait())) {
			if (this.waitUntil == null) {
				vc.add("ProcessVariable('" + WAITING_TYPE_UNTIL + "') doesn't exist!!");
			} else if (this.waitUntil != null) {
				if (this.waitUntil.getClass() != ValueProcessVariable.class) {
					ProcessVariable pvv = this.getProcessDefinition().getProcessVariable(this.waitUntil.getName());
					if (pvv.getType() != Calendar.class) {
						vc.add("ProcessVariable('" + pvv.getDisplayName() + "') is not a date format!!");	
					}
				}
			}
		}
		
		if (WAITING_TYPE_PERIOD.equals(this.getHowWait()) && this.getScheCronExp() != null) {
			if (this.getScheCronExp().isType() == true && this.getScheCronExp().getManualScheCronExp() != null) {
				
				if (this.getScheCronExp().getManualScheCronExp().getManualInput() == null) {
					
					String dayOfMonth = this.getScheCronExp().getManualScheCronExp().getDayOfMonth();
					String dayOfWeek = this.getScheCronExp().getManualScheCronExp().getDayOfWeek();

					int questionMarkCount = StringUtils.countMatches(dayOfMonth + dayOfWeek, "?");

					if (questionMarkCount != 1) {
						vc.add("Scheduler Cron Expression Error\n\"Day Of Month\" and \"Day Of Week\", one must necessarily be \"?\"");
					}
					
				} else {
					
					String scriptSmt = this.getScheCronExp().getManualScheCronExp().getManualInput();
					while (true) {
						int findStartTag = scriptSmt.indexOf("<%");
						if (findStartTag != -1) {
							int findEndTag = scriptSmt.indexOf("%>");

							String tagName = scriptSmt.substring(findStartTag + 2, findEndTag);

							ProcessVariable pv = this.getProcessDefinition().getProcessVariable(tagName);

							if (pv == null) {
								vc.add("ProcessVariable('" + tagName+ "') doesn't exist!!");}

							String tag = scriptSmt.substring(findStartTag,findEndTag + 2);

							scriptSmt = scriptSmt.replace(tag, "");

						} else {
							break;
						}
					}
				}
				
			} else if (this.getScheCronExp().isType() == false && this.getScheCronExp().getAutoScheCronExp() != null) {
				
				List<String> _options = new ArrayList<String>();
				_options.add(this.getScheCronExp().getAutoScheCronExp().getDays());
				_options.add(this.getScheCronExp().getAutoScheCronExp().getHours());
				_options.add(this.getScheCronExp().getAutoScheCronExp().getMinutes());
				_options.add(this.getScheCronExp().getAutoScheCronExp().getMonths());
				_options.add(this.getScheCronExp().getAutoScheCronExp().getYears());
				
				boolean isAllNull = true;
				for (String s : _options) {
					if (s != null) {
						isAllNull = false;
						break;
					}
				}
				
				if (!isAllNull){
					for (String s : _options) {
						if (s != null) {
							String scriptSmt = s;
							
							while (true) {
								int findStartTag = scriptSmt.indexOf("<%");
								if (findStartTag != -1) {
									int findEndTag = scriptSmt.indexOf("%>");
	
									String tagName = scriptSmt.substring(findStartTag + 2, findEndTag);
	
									ProcessVariable pv = this.getProcessDefinition().getProcessVariable(tagName);
	
									if (pv == null) {
										vc.add("ProcessVariable('" + tagName+ "') doesn't exist!!");}
	
									String tag = scriptSmt.substring(findStartTag,findEndTag + 2);
	
									scriptSmt = scriptSmt.replace(tag, "");
	
								} else {
									break;
								}
							} // while (true) {
						}
					} // for (String s : _options) {
				} else {
					vc.add("one must necessarily be value");
				}
			}
		}
		
		return vc;
	}

}