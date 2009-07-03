package com.brainz.wokhei;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class WokheiConfig implements IsSerializable {

	@Persistent
	private Long id;

	@Persistent
	private boolean orderKillswitch;

	public WokheiConfig(long id, boolean orderKillswitch)
	{
		this.id = id;
		this.orderKillswitch = orderKillswitch;
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

	public void setOrderKillswitch(boolean isOn) {
		this.orderKillswitch = isOn;
	}

}
