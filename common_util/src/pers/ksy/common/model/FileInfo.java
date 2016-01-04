package pers.ksy.common.model;

public class FileInfo {
	private String name;
	private String filename;
	private Long size;
	private String url;
	private String deleteUrl;
	private String deleteType;

	public FileInfo() {
		super();
	}

	public FileInfo(String name, String filename, Long size, String url) {
		super();
		this.name = name;
		this.filename = filename;
		this.size = size;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

}
