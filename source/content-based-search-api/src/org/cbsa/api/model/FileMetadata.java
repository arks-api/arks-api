package org.cbsa.api.model;

import java.util.List;

/***
 * This is container class to store file meta data. Use object of this class to
 * store meta data of file and use this object to add meta data as new entry to
 * database
 */
public class FileMetadata {

    private final String fileID;
    private final String fileName;
    private final String filePath;
    private final String fileSize;
    private final String totalPages;
    private final String fileDomain;
    private final List<Keyword> keywords;

    /***
     * Use this constructor to pass meta data at time of construction.
     *
     * @param fileID
     * @param fileName
     * @param filePath
     * @param fileSize
     * @param totalPages
     * @param fileDomains
     * @param keywords
     */
    public FileMetadata(String fileID, String fileName, String filePath,
            String fileSize, String totalPages, String fileDomains,
            List<Keyword> keywords) {

        this.fileID = fileID;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.totalPages = totalPages;
        this.fileDomain = fileDomains;
        this.keywords = keywords;
    }

    public String getFileID() {
        return fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public String getFileDomain() {
        return fileDomain;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

}
