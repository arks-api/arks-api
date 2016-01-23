public class Container 
{
	private String filepath;
	private String content;
	private int pageno;
	
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getFilepath() {
		return filepath;
	}
	
	public int getPageno() {
		return pageno;
	}
}
