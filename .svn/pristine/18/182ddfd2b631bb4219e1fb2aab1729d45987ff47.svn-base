package com.hncis.common.util;

/**
 * 파라미터 암호화 클래스 
 * MAP 과 연동시에 UserKey를 암호화 하고 복호화 하는 클래스
 * 
 * @author 6804401
 *
 */
public class ParameterSecurity {
	/**
	 * 암호화1 키
	 */
	public int m_nC1Key = 74102;
	/**
	 * 암호화2 키
	 */
	public int m_nC2Key = 12337;
	/**
	 * 암호화3 키
	 */
	public int m_nC3Key = 100;
	
	/**
	 * 생성자
	 */
	public ParameterSecurity() {
	}
	
	/**
	 * 입력받은 키정보를 암호화1 키에 set 한다.
	 * 
	 * @param nKey 키정보
	 */
	public void setKey1(int nKey) {
		m_nC1Key = nKey;
	}
	
	/**
	 * 입력받은 키정보를 암호화2 키에 set 한다.
	 * 
	 * @param nKey 키정보
	 */
	public void setKey2(int nKey) {
		m_nC2Key = nKey;
	}
	
	/**
	 * 생성자 - 암호화1,2 키를 set 한다.
	 * @param nKey1 키1
	 * @param nKey2 키2
	 */
	public ParameterSecurity(int nKey1, int nKey2) {
		m_nC1Key = nKey1;
		m_nC2Key = nKey2;
	}
	
	/**
	 * 생성자 - 암호화1,2,3 키를 set 한다.
	 * @param nKey1 키1
	 * @param nKey2 키2
	 * @param nKey3 키3
	 */
	public void setKey(int nKey1, int nKey2, int nKey3) {
		m_nC1Key = nKey1;
		m_nC2Key = nKey2;
		m_nC3Key = nKey3;
	}
	
	/**
	 * 아스키 코드값을 16진수형태로 변경한다. 
	 * 
	 * @param nVal 아스키 코드값
	 * @return 16진수 형태의 byte 자료
	 */
	public byte getHexaByte(int nVal) {
		char[] szHexaByte = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		if (nVal > 15) {
			nVal = 0;
		}
		return (byte)szHexaByte[nVal];
	}
	
	/**
	 * 아스키 코드값들을 16진수형태 값으로 변경하여 문자열형태로 Return 한다.
	 * 
	 * @param szSrc 아스키 코드값들
	 * @return 16진수형태의 문자열
	 */
	public String getValueToHex(int szSrc[]) {
		if (szSrc == null)
			return null;
		int nSrcLen = szSrc.length;
		byte szBuf[] = new byte[nSrcLen * 2];
		for (int i = 0; i < nSrcLen * 2; i++) {
			szBuf[i] = 0;
		}
		for (int i = 0; i < nSrcLen; i++) {
			szBuf[(i * 2) + 0] = getHexaByte(((int) (szSrc[i])) / 16);
			szBuf[(i * 2) + 1] = getHexaByte(((int) (szSrc[i])) % 16);
		}
		String sRet = new String(szBuf);
		return sRet;
	}
	
	/**
	 * 16진수형태의 자료를 아스키 코드값으로 Return 한다.
	 * 
	 * @param szSrc 16진수형태의 문자
	 * @return 아스키 코드값
	 */
	public byte[] getHexToValue(byte[] szSrc) {
		int nLen = szSrc.length;
		byte[] szDest = new byte[nLen / 2];
		char szChar[] = new char[2];
		for (int I = 0; I < nLen / 2; I++) {
			szChar[0] = (char) szSrc[I * 2];
			szChar[1] = (char) szSrc[I * 2 + 1];
			byte btDest = (byte) getHexToDecimal(szChar);
			int nDest = btDest < 0 ? (Byte.MAX_VALUE + 1) * 2 + btDest : btDest;
			szDest[I] = (byte) nDest;
		}
		String sRet = new String(szDest);
		return szDest;
	}
	
	/**
	 * 입력받은 문자와 키로 해당 자료를 암호화 한다.
	 * 
	 * @param btSrc 암호화 할 문자의 byte
	 * @param Key 암호화 키
	 * @return 암호화된 문자열
	 */
	public String getEncrypt(byte btSrc[], int Key) {
		int nSrcLen = btSrc.length;
		long nKey2 = Key;
		int FirstResult[] = new int[nSrcLen];
		for (int i = 0; i < nSrcLen; i++) {
			FirstResult[i] = 0;
		}
		int nLen = btSrc.length;
		for (int i = 0; i < nLen; i++) {
			byte btByte = (byte) btSrc[i];
			int cSrc = btByte < 0 ? (Byte.MAX_VALUE + 1) * 2 + btByte : btByte;
			long nXor = ((long) nKey2) / ((long) 256);
			byte btTmp = (byte) (cSrc ^ nXor);
			FirstResult[i] = btTmp < 0 ? (Byte.MAX_VALUE + 1) * 2 + btTmp
					: btTmp;
			byte cFirstResult = (byte) FirstResult[i];
			int nFirstResult = cFirstResult < 0 ? (Byte.MAX_VALUE + 1) * 2
					+ cFirstResult : cFirstResult;
			long nFirstResultKey = (long) (nFirstResult + nKey2);
			nKey2 = (nFirstResultKey) * m_nC1Key + m_nC2Key;
		}
		String sRet = "";
		sRet = getValueToHex(FirstResult);
		return sRet;
	}
	
	/**
	 * 입력받은 char 16진수 값을 10진수로 변경한다.
	 * 
	 * @param szSrc 변경할 16진수 char 배열
	 * @return 10진수
	 */
	public int getHexToDecimal(char[] szSrc) {
		int nRet = 0;
		int nLen = szSrc.length;
		for (int i = 0; i < nLen; i++) {
			byte cChar = (byte) szSrc[i];
			nRet = nRet * 16;
			nRet += getHexToDecimal(cChar);
		}
		return nRet;
	}
	
	/**
	 * 입력받은 char 16진수 값을 10진수로 변경한다.
	 * @param cChar 변경항 16진수 char
	 * @return 10진수
	 */
	public int getHexToDecimal(byte cChar) {
		if (cChar == 'A' || cChar == 'a')
			return 10;
		if (cChar == 'B' || cChar == 'b')
			return 11;
		if (cChar == 'C' || cChar == 'c')
			return 12;
		if (cChar == 'D' || cChar == 'd')
			return 13;
		if (cChar == 'E' || cChar == 'e')
			return 14;
		if (cChar == 'F' || cChar == 'f')
			return 15;
		return (cChar - 48);
	}
	
	/**
	 * 암호화 된 문자열과 키 정보를 받아 복호화 된 문자열을 Return 한다. 
	 * 
	 * @param szSrc 암호화 된 문자열의 Byte
	 * @param Key 암호화 키
	 * @return 복호화 된 문자열
	 */
	public String getDecrypt(byte szSrc[], int Key) {
		if (szSrc == null)
			return null;
		int nSrcLen = szSrc.length;
		byte FirstResult[] = new byte[nSrcLen / 2];
		for (int i = 0; i < nSrcLen / 2; i++) {
			FirstResult[i] = 0;
		}
		int nLen = 0;
		FirstResult = getHexToValue(szSrc);
		byte szFirstResult[] = FirstResult;
		byte szBuf[] = new byte[nSrcLen / 2];
		for (int i = 0; i < nSrcLen / 2; i++) {
			szBuf[i] = 0;
		}
		byte szResult[] = new byte[nSrcLen / 2];
		for (int i = 0; i < nSrcLen / 2; i++) {
			szResult[i] = 0;
		}
		int nKey = Key < 0 ? (Integer.MAX_VALUE + 1) * 2 + Key : Key;
		long nKey2 = (long) nKey;
		for (int I = 0; I < nSrcLen / 2; I++) {
			int nVal = szFirstResult[I] < 0 ? (Byte.MAX_VALUE + 1) * 2
					+ szFirstResult[I] : szFirstResult[I];
			long nFirstResult = ((long) nVal);
			long nXor = (nKey2 / (long) 256);
			long nXorResult = nFirstResult ^ nXor;
			szResult[I] = (byte) (nXorResult);
			byte cFirstResult = ((byte) szFirstResult[I]);
			long cFirstResultKey = (nFirstResult + nKey2);
			nKey2 = cFirstResultKey * m_nC1Key + m_nC2Key;
		}
		String sRet = new String(szResult);
		return sRet;
	}
}
