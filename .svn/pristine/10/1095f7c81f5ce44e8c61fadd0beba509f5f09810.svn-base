package com.hncis.common.exception;

public interface IHncisException {
	/**
     * error code getter
     * @return errorcode
    */
   public abstract String getCode();
   /**
    * message getter
    * @return error message
    */
   public abstract String getMessage();
   /**
    * logg 쓸여부 getter
    * @return true 로그남김 ,false 로그 안남김 
    */
   public abstract boolean isWriteLog();
   /**
    * logg 쓸여부 setter
    * @param isWriteLog logg 쓸여부 
    */
   public abstract void setWriteLog(boolean isWriteLog);
   /**
    * logg 쓸여부 setter
    * @param userObject 사용자 정의 object
    */
   public abstract void setUserObject(Object userObject);
   /**
    * logg 쓸여부 getter
    * @return user object
    */
   public abstract Object getUserObject();
}
