package com.cmp.entity.tcc;

import java.util.Date;

public class TccArticle {

	// 文章ID

	private Long id;
	// 文章标题

	private String title;
	// 文章分类ID

	private Long classificationID;

	private String classificationName;
	// 文章内容

	private String content;
	// 0：删除 1：可用

	private String enable;
	// 1：立即上线 0：没上线

	private String isPublish;
	// 创建人

	private Long createUser;
	// 创建时间

	private Date createTime;
	// 修改时间

	private Date updateTime;
	// 修改人

	private Long updateUser;

	private String username;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the classificationID
	 */
	public Long getClassificationID() {
		return classificationID;
	}

	/**
	 * @param classificationID
	 *            the classificationID to set
	 */
	public void setClassificationID(Long classificationID) {
		this.classificationID = classificationID;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the enable
	 */
	public String getEnable() {
		return enable;
	}

	/**
	 * @param enable
	 *            the enable to set
	 */
	public void setEnable(String enable) {
		this.enable = enable;
	}

	/**
	 * @return the isPublish
	 */
	public String getIsPublish() {
		return isPublish;
	}

	/**
	 * @param isPublish
	 *            the isPublish to set
	 */
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createUser
	 */
	public Long getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the updateUser
	 */
	public Long getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser
	 *            the updateUser to set
	 */
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the classificationName
	 */
	public String getClassificationName() {
		return classificationName;
	}

	/**
	 * @param classificationName
	 *            the classificationName to set
	 */
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
