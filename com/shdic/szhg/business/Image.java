package com.shdic.szhg.business;

import java.io.Serializable;

/************************************************************
 *  ����ժҪ	��
 *
 *  ����	������Դ
 *  ����ʱ��	��2013-7-23 ����05:19:47 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2013-7-23 ����05:19:47 	�޸��ˣ�����Դ
 *  	����	:
 ************************************************************/
public class Image implements Serializable {
	private String path;
	private String entryId;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	
}
