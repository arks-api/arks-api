
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HadoopCopyToLocal
{
	public static void main(String[] args) throws IOException 
	{
		Configuration configuration = new Configuration();
		configuration.addResource(new Path("/usr/local/hadoop/etc/hadoop/core-site.xml"));
		configuration.addResource(new Path("/usr/local/hadoop/etc/hadoop/hdfs-site.xml"));
		FileSystem hdfs =FileSystem.get(configuration);
		
		Path home = hdfs.getHomeDirectory();
		Path src = new Path("hdfs://localhost:9000/opt/dataset/android_tutorial.pdf");
		
		Path dest = new Path("/home/rahul/temp_DIR/XYZA.pdf");
		hdfs.copyToLocalFile(src, dest);
	}
}
