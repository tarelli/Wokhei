package com.brainz.wokhei;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class WokheiConfig implements IsSerializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private boolean orderKillswitch;

	@Persistent
	private boolean isSandBox;

	public WokheiConfig(boolean orderKillswitch, boolean isSandBox)
	{
		this.orderKillswitch = orderKillswitch;
		this.isSandBox=isSandBox;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getOrderKillswitch() {
		return orderKillswitch;
	}

	public boolean isSandBox() {
		return isSandBox;
	}

	public void setOrderKillswitch(boolean isOn) {
		this.orderKillswitch = isOn;
	}

	public void setSandBox(boolean sandBox) {
		this.isSandBox = sandBox;
	}

}
