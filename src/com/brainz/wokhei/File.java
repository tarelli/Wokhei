package com.brainz.wokhei;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.brainz.wokhei.shared.FileType;
import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class File {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private FileType type;

	@Persistent
	private Long orderid;

	@Persistent
	private Blob file;

	public File(Blob file, FileType fileType, Long orderId) {
		this.file=file;
		this.type=fileType;
		this.orderid=orderId;
	}

	public FileType getFileType() {
		return type;
	}

	public void setType(FileType fileType)
	{
		type=fileType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setOrderid(Long orderId) {
		this.orderid = orderId;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setFile(Blob file)
	{
		this.file=file;
	}

	public Blob getFile()
	{
		return file;
	}
}

