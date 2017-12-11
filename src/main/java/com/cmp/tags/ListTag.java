package com.cmp.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.cmp.sid.CmpDict;

public class ListTag extends BodyTagSupport{
	private static final long serialVersionUID = 1L;
	private Iterator<CmpDict> it;// 要迭代的对象
	private String name;
	private String key;
	private String val;
	
	public ListTag(){
		super();
	}
	
	public int doStartTag() throws JspException{
		try{
			if(StringUtils.isBlank(name) || StringUtils.isBlank(key) || StringUtils.isBlank(val)){
				return SKIP_BODY;
			}
			
			String[] keys=key.split(",");//key
			String[] vals=val.split(",");//value
			if(keys.length!=vals.length){
				return SKIP_BODY;
			}
			
			List<CmpDict> list = new ArrayList<CmpDict>();
			for(int i=0;i<keys.length;i++){
				CmpDict cmpDict=new CmpDict();
				cmpDict.setDictCode(keys[i]);
				cmpDict.setDictValue(vals[i]);
				list.add(cmpDict);
			}
			
			it = list.iterator();
			return continueNext();
		}catch(Exception e){
			throw new JspException("list标签出错："+e);
		}
	}
	
	@SuppressWarnings("deprecation")
	private int continueNext() {
		if (it.hasNext()) {
			pageContext.setAttribute(name, it.next(), PageContext.PAGE_SCOPE);
			return EVAL_BODY_TAG;
		}
		
		return SKIP_BODY;
	}
	
	public int doAfterBody() {
		return continueNext();
	}
	
	public int doEndTag() throws JspException{
		try{
			if (bodyContent != null) {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			}
		}catch(Exception e){
			throw new JspException("list标签出错："+e);
		}
		
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
}