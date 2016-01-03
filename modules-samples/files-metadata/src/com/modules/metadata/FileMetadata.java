package com.modules.metadata;

import java.util.List;

/***
 * This is container class to store file meta data. Use object of this class to
 * store meta data of file and use this object to add meta data as new entry to
 * database
 *
 */
public class FileMetadata {

	private String fileName;
	private String fileSize;
	private String totalPages;
	private List<String> fileDomains;
	private List<Keyword> keywords;

	/***
	 * Default Constructor to create FileMetadata. Use <b>setters</b> and
	 * <b>getters</b> to add meta data of file
	 */
	public FileMetadata() {

	}

	/***
	 * Use this constructor to pass meta data at time of construction.
	 *
	 * @param fileName
	 * @param fileSize
	 * @param totalPages
	 * @param fileDomain
	 * @param keywords
	 */
	public FileMetadata(String fileName, String fileSize, String totalPages,
			List<String> fileDomains, List<Keyword> keywords) {

		this.fileName = fileName;
		this.fileSize = fileSize;
		this.totalPages = totalPages;
		this.fileDomains = fileDomains;
		this.keywords = keywords;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public List<String> getFileDomains() {
		return fileDomains;
	}

	public void setFileDomains(List<String> fileDomains) {
		this.fileDomains = fileDomains;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

}
