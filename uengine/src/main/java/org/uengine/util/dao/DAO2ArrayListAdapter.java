package org.uengine.util.dao;

import java.util.ArrayList;
import java.util.Iterator;

public class DAO2ArrayListAdapter extends ArrayList{
	
	IDAO dao;
		public IDAO getDao() {
			return dao;
		}
		public void setDao(IDAO dao) {
			this.dao = dao;
		}
	

	public DAO2ArrayListAdapter(IDAO dao){
		setDao(dao);
	}
	
	public Iterator iterator() {
		return new Iterator(){

			public void remove() {
			}

			public boolean hasNext() {
				try {
					return getDao().next();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			public Object next() {
				return getDao();
			}
			
		};
	}
	
	public int size() {
		return getDao().size();
	}
	
}
