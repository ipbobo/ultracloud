package com.cmp.entity.tcc;

import java.util.Date;

public class TccVmExpiration implements java.io.Serializable {

	private static final long serialVersionUID = 4255436732451638999L;

	private Long id;

	private Integer days;

	private Long modifierId;

	private Date modifierDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public Date getModifierDate() {
		return modifierDate;
	}

	public void setModifierDate(Date modifierDate) {
		this.modifierDate = modifierDate;
	}

}
