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
    private Integer fileSize;
    private Integer totalPages;
    private List<String> fileDomains;
    private List<String> keywords;

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
    public FileMetadata(String fileName, Integer fileSize, Integer totalPages,
            List<String> fileDomains, List<String> keywords) {

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

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<String> getFileDomains() {
        return fileDomains;
    }

    public void setFileDomains(List<String> fileDomains) {
        this.fileDomains = fileDomains;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

}
