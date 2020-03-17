package cn.lxr.util.baseUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	/**
	 * 创建文件路径
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createDirectory(String path) {
		boolean flag = true;
		try {
			File wf = new File(path);
			if (!wf.exists()) {
				wf.mkdirs();
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
  
  
  /**
	 * 下载文件
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-9-4  上午09:47:32
	 */
	public static HttpServletResponse download(String path, HttpServletResponse response) {
        try  {
            //  path是指欲下载的文件的路径。
           File file  =   new  File(path);
            //  取得文件名。
           String filename  =  file.getName();
            //  取得文件的后缀名。
//           String ext  =  filename.substring(filename.lastIndexOf( "." )  +   1 ).toUpperCase();

            //  以流的形式下载文件。
           InputStream fis  =   new  BufferedInputStream( new  FileInputStream(path));
            byte [] buffer  =   new   byte [fis.available()];
           fis.read(buffer);
           fis.close();
            //  清空response
           response.reset();
            //  设置response的Header
           response.setCharacterEncoding("UTF-8");
           response.addHeader( "Content-Disposition" ,  "attachment;filename="  +  new  String(filename.getBytes("gb2312"),"iso8859-1"));
           response.addHeader( "Content-Length" , file.length()+"");
           OutputStream toClient  =   new  BufferedOutputStream(response.getOutputStream());
           response.setContentType( "application/octet-stream" );
           toClient.write(buffer);
           // 关闭流
           toClient.close();
           fis.close();
       }  catch  (IOException ex) {
    	   ex.printStackTrace();
       }
        return  response;
   } 

}
	