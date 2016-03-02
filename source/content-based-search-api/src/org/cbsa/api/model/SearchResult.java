package org.cbsa.api.model;

public abstract class SearchResult {

    private String resultType;
    private String filePath;
    private String fileContent;
    private int pageNumber;

    public String getresultType() {
        return resultType;
    }

    public void setresultType(String resultType) {
        this.resultType = resultType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
