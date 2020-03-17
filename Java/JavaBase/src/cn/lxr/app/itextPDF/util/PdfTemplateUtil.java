package cn.lxr.app.itextPDF.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.pqsoft.base.pdfTemplate.config.EContractParam;
import com.pqsoft.base.pdfTemplate.entity.DocumentSetting;
import com.pqsoft.skyeye.api.SkyEye;

public class PdfTemplateUtil {

	
	/**
	 * @Title 创建pdf文件
	 * @Description 用于创建空白pdf或测试pdf
	 */
	public static void createPdf() throws FileNotFoundException, DocumentException,
	com.itextpdf.text.DocumentException {
		//创建一
		Document pdf = new Document(PageSize.A4, 48, 48, 60, 15);// 其余4个参数，设置了页面的4个边距
		//创建二
//		Rectangle rect = new Rectangle(PageSize.B5.rotate());  
//		rect.setBackgroundColor(BaseColor.ORANGE);  
//		Document pdf = new Document(rect);  
		
		PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("D:\\temp1.pdf"));
//		setPassword(writer, "000", "111");
		pdf.open();
		pdf.add(new Paragraph("Hello World!!!"));
		pdf.close();
	}
	
	/**
	 * 
	 * @Title 文件设置
	 * @Description 给PDF文件设置文件属性
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public static void setDocumentDefault(Document document, DocumentSetting setting) throws DocumentException, FileNotFoundException {
		if (setting == null) {
			return;
		}
		/*============文档属性=============*/
        //标题
		if (setting.getTitle() != null) {
			document.addTitle(setting.getTitle());
		}
        //作者
		if (setting.getAuthor() != null) {
			document.addAuthor(setting.getAuthor());
		}
        //主题
		if (setting.getSubject() != null) {
			document.addSubject(setting.getSubject());
		}
        //关键字
		if (setting.getKeywords() != null) {
			document.addKeywords(setting.getKeywords());
		}
        //创建时间
        if (setting.isCreationDate()) {
        	document.addCreationDate();
        }
        //应用程序
        if (setting.getCreator() != null) {
        	document.addCreator(setting.getCreator());
        }
	}
	
	/**
	 * @Title 添加密码
	 * @throws DocumentException 
	 */
	public static void setPassword(PdfWriter writer, String userPassword, String ownerPassword) throws DocumentException {
		// 设置密码  
		writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(),  
		        PdfWriter.ALLOW_SCREENREADERS,  
		        PdfWriter.STANDARD_ENCRYPTION_128);  
	}
	
	/**
	 * 
	 * @Title 添加图片水印(居中)
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static void addWaterMarkPicCenter(PdfReader reader, PdfStamper stamp, String imagePath, float scalePersent, float opacity, int pageNum) throws DocumentException, IOException {
		//图片水印  
		Image image = Image.getInstance(imagePath);  
		//设置透明度
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(opacity);
		for (int i = pageNum; i <= reader.getNumberOfPages(); i++) {
			if (i <= 0) {
				i = 1;//负数回0
			}
			Rectangle rec = reader.getPageSize(i);
			float resizedWidth = image.getWidth();
			float resizedHeight = image.getHeight();
			float pageWidth = rec.getWidth();
			float pageHeight = rec.getHeight();
			//依照比例缩放
			image.scalePercent(scalePersent);
			//让图片的中心点与页面的中心点进行重合
			image.setAbsolutePosition(pageWidth / 2 - (resizedWidth * scalePersent) / 200, pageHeight / 2 - (resizedHeight * scalePersent) / 200);
			//居于文件下层
			PdfContentByte under = stamp.getUnderContent(i);  
			under.setGState(gs);
			under.addImage(image);  
			if (pageNum > 0) {//小于0时所有页面添加水印
				break;
			} 
		}
	}
	
	/**
	 * 
	 * @Title 添加文字水印
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static void addWaterMarkWord(PdfReader reader, PdfStamper stamp, String text, float x, float y, float rotation, float opacity, int pageNum) throws DocumentException, IOException {
		//设置透明度
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(opacity);
		for (int i = pageNum; i <= reader.getNumberOfPages(); i++) {
			if (i <= 0) {
				i = 1;//负数回0
			}
			PdfContentByte over = stamp.getOverContent(i);  
			over.beginText();  
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);  
			over.setFontAndSize(bf, 18);  
//			over.setTextMatrix(130, 130);  
//			over.setTextMatrix(0, 1, -1, 0, 100, 300);
			over.showTextAligned(Element.ALIGN_LEFT, text, x, y, rotation);  
//			over.showText("Text at position 100,300, rotated 90 degrees.");
			over.setGState(gs);
			over.endText();  
			if (pageNum > 0) {//小于0时所有页面添加水印
				break;
			}
		}
	}
	
	/**
	 * @Title 添加页眉
	 */
	public static void addHeader(PdfReader reader, PdfStamper stamp, String text) {
		//图片水印  
				Image image = Image.getInstance(imagePath);  
				//设置透明度
				PdfGState gs = new PdfGState();
				gs.setFillOpacity(opacity);
				for (int i = pageNum; i <= reader.getNumberOfPages(); i++) {
					if (i <= 0) {
						i = 1;//负数回0
					}
					Rectangle rec = reader.getPageSize(i);
					float resizedWidth = image.getWidth();
					float resizedHeight = image.getHeight();
					float pageWidth = rec.getWidth();
					float pageHeight = rec.getHeight();
					//依照比例缩放
					image.scalePercent(scalePersent);
					//让图片的中心点与页面的中心点进行重合
					image.setAbsolutePosition(pageWidth / 2 - (resizedWidth * scalePersent) / 200, pageHeight / 2 - (resizedHeight * scalePersent) / 200);
					//居于文件下层
					PdfContentByte under = stamp.getUnderContent(i);  
					under.setGState(gs);
					under.addImage(image);  
					if (pageNum > 0) {//小于0时所有页面添加水印
						break;
					} 
				}
		
	}
	
	/**
	 * 
	 * @Title 拼接pdf
	 * @Description 按顺序追加pdf
	 * @param readers
	 * @param OutputFilePath
	 */
	public static String appendPDF(List<PdfReader> readers, String OutputFilePath)
			throws IOException, DocumentException {
		FileOutputStream out = new FileOutputStream(OutputFilePath);
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		PdfContentByte cb = writer.getDirectContent();

		int pageOfCurrentReaderPDF = 0;
		Iterator<PdfReader> iteratorPDFReader = readers.iterator();
		// Loop through the PDF files and add to the output.
		while (iteratorPDFReader.hasNext()) {
			PdfReader pdfReader = iteratorPDFReader.next();
			// Create a new page in the target for each source page.
			while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
				document.newPage();
				pageOfCurrentReaderPDF++;
				PdfImportedPage page = writer.getImportedPage(pdfReader,
						pageOfCurrentReaderPDF);
				cb.addTemplate(page, 0, 0);
			}
			pageOfCurrentReaderPDF = 0;
		}
		out.flush();
		document.close();
		out.close();
		return OutputFilePath;
	}

	/**
	 * 
	 * @Title 合并pdf
	 * @Description 在 第一个pdf的某位置插入第二个pdf
	 * @param filePath1
	 * @param filePath2
	 * @param OutputFilePath
	 * @param indexOfFilePath1
	 */
	public static String mergePDF(String filePath1, String filePath2,
			String OutputFilePath, int indexOfFilePath1) throws IOException,
			DocumentException {
		PdfReader reader1 = new PdfReader(filePath1);
		PdfReader reader2 = new PdfReader(filePath2);
		FileOutputStream out = new FileOutputStream(OutputFilePath);
		mergePDF(reader1, reader2, out, indexOfFilePath1);
		return OutputFilePath;
	}
	
	/**
	 * 
	 * @Title 合并pdf
	 * @Description 在 第一个pdf的某位置插入第二个pdf
	 * @param filePath1
	 * @param filePath2
	 * @param OutputFilePath
	 * @param indexOfFilePath1
	 */
	public static FileOutputStream mergePDF(PdfReader reader1, PdfReader reader2,
			FileOutputStream out, int indexOfFilePath1) throws IOException,
			DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);
		
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		int index = 0;
		for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
			if (i == indexOfFilePath1) {
				for (int j = 1; j <= reader2.getNumberOfPages(); j++) {
					document.newPage();
					PdfImportedPage page = writer.getImportedPage(reader2, j);
					cb.addTemplate(page, 0, 0);
					index++;
				}
			}
			document.newPage();
			PdfImportedPage page = writer.getImportedPage(reader1, i);
			cb.addTemplate(page, 0, 0);
			index++;
		}
		out.flush();
		document.close();
		out.close();
		return out;
	}

	/**
	 * 
	 * @Title 选择保留页面
	 * @Description 删除未选中的几页
	 * @param inputFilePath
	 * @param pageNums
	 * @param outputFilePath
	 */
	public static String removePages(String inputFilePath, String pageNums,
			String outputFilePath) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(inputFilePath);
		reader.selectPages(pageNums);
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
				outputFilePath));
		stamp.close();
		reader.close();
		return outputFilePath;
	}
	
	/**
	 * 提取PDF所有表单域的名称
	 * 
	 * @param inputPath
	 * @return
	 * @throws Exception
	 */
	public static List<String> parseAcrobat(String inputPath) throws Exception {
		try {
			FileInputStream input = new FileInputStream(new File(inputPath));
			return parseAcrobat(input);
		} catch (FileNotFoundException e) {
			throw new Exception("AcrobatENG-007: 准备PDF输入流时出错。参考："
					+ e.getMessage());
		}
	}

	/**
	 * 提取PDF所有表单域的名称
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<String> parseAcrobat(InputStream input) throws Exception {
		List<String> acrobatContext = new ArrayList<String>();
		try {
			PdfReader reader = new PdfReader(input);
			PdfStamper stamp = new PdfStamper(reader, null);
			AcroFields afields = stamp.getAcroFields();
			for (Iterator iterator = afields.getFields().keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				if (key == null || key.length() <= 0) {
					continue;
				}
				// 取表单中的字段
				acrobatContext.add(key);
			}
		} catch (IOException e) {
			throw new Exception(
					"AcrobatENG-005: 处理PDF表单时错误，可能是读取的文件不存在或输出流异常。参考："
							+ e.getMessage());
		} catch (DocumentException e) {
			throw new Exception("AcrobatENG-006: 填充PDF表单时发生异常。参考："
					+ e.getMessage());
		}
		return acrobatContext;
	}
	
	
	public static void main(String[] args) {
		try {
//			1.测试生成pdf
			createPdf();
//			2.测试抓取表单域
//			List<String> parseAcrobat = parseAcrobat("D:\\\\temp.pdf");
//			for (String key : parseAcrobat) {
//				System.out.println(key);
//			}
//			3.测试拼接
//			java.util.List<PdfReader> readers = new ArrayList<PdfReader>();
//			PdfReader reader1 = new PdfReader("D:\\\\temp.pdf");
//			PdfReader reader2 = new PdfReader("D:\\\\\\\\temp1.pdf");
//			readers.add(reader1);
//			readers.add(reader2);
//			appendPDF(readers, "D:\\\\\\\\tempss.pdf");
//			4.测试合并
//			mergePDF("D:\\\\山重租赁工作服管理办法（2016年7月27日修订）.pdf", "D:\\\\temp.pdf", "D:\\\\tempmm.pdf", 2);
//			5.测试选定页面
//			removePages("D:\\\\tempmm.pdf", "1,3", "D:\\\\tempmm1.pdf");
//			6.测试文件设置
//			DocumentSetting setting = EContractParam.PDF_SETTING;
//			setting.setTitle("测试合同Z00");
//			Document pdf = new Document(PageSize.A4);
//			PdfWriter.getInstance(pdf, new FileOutputStream("D:\\temp0.pdf"));
//			pdf.open();
//			setDocumentDefault(pdf, setting);
//			7.测试中文字体,解决中文不能显示问题
//			（1）使用iTextAsian.jar中的字体 
//	        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//			（2）使用Windows系统字体(TrueType)
//	        BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
//			（3）使用资源字体(ClassPath) .ttc文件是多个.ttf集合，输入路径时需要指定 ,num (如simsun.ttc,0)
//	        BaseFont bfChinese = BaseFont.createFont("F:\\sflc_workplace\\SFLC-BASE4.0\\src\\main\\java\\com\\pqsoft\\base_sign\\simsun.ttc,0", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED); 
//	        8.测试蓝色字体
//	        Font blueFont = new Font(bfChinese);
//	        blueFont.setColor(BaseColor.BLUE);
//			pdf.add(new Paragraph("你好世界!!!", blueFont));
//			pdf.close();
//			9.测试水印
			PdfReader reader = new PdfReader("D:\\\\tempmm.pdf");
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:\\\\\\\\tempmmm1.pdf")); 
			addWaterMarkPicCenter(reader, stamp, "F:\\sflc_workplace\\SFLC-BASE4.0\\src\\main\\java\\com\\pqsoft\\base\\pdfTemplate\\resource\\水印.png", 10, 0.1f, 2);
			addWaterMarkWord(reader, stamp, "DUPLICATE", 20, 20, 45, 0.1f, 1);
			stamp.close(); 
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
