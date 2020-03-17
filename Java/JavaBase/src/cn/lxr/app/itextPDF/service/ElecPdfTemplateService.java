package cn.lxr.app.itextPDF.service;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import cn.lxr.itextPDF.util.PdfTemplateUtil;

public class ElecPdfTemplateService {

	/**
	 * 测试生成（一）
	 * 先生成文件，再插入水印及页眉
	 * 用于存在表单域模板的情况
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private static void testProduce() throws IOException, DocumentException {
		PdfReader reader = new PdfReader("C:\\Users\\admin\\Desktop\\半电子合同\\附件\\空白合同模板\\A07客户扣款授权书.pdf");
//		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:\\A07_temp1.pdf")); 
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); //文件存储于缓冲区，不用生成临时文件
		PdfStamper stamp = new PdfStamper(reader, bos); 
		PdfTemplateUtil.addWaterMarkPic(reader, stamp, "C:\\Users\\admin\\Desktop\\半电子合同\\合同模板\\水印.png", 22, 0.08f, 0);
		stamp.close();
		//拼接3段合同
		ArrayList<PdfReader> list = new ArrayList<PdfReader>();
		list.add(new PdfReader("C:\\Users\\admin\\Desktop\\半电子合同\\HZT00封面.pdf"));
		list.add(new PdfReader(bos.toByteArray()));
		list.add(new PdfReader("C:\\Users\\admin\\Desktop\\半电子合同\\HZT00尾页.pdf"));
		PdfTemplateUtil.appendPDF(list, "D:\\A07客户扣款授权书.pdf");
	}
	
	/**
	 * 测试生成合同(二)
	 * 生成文档时插入水印及页眉
	 * 如果不采用表单域模板，完全自动生成文档，使用此方法效率更高
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private static void testProduce_() throws IOException, DocumentException {
		//水印、页眉页脚
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); //文件存储于缓冲区，不用生成临时文件
		PdfWriter writer = PdfWriter.getInstance(document, bos);
		
		//添加水印和页码
        PdfPageEventHelper builder = new PdfTemplateEventHelper();
        writer.setPageEvent(builder);
        //打开文件
        document.open();
        document.add(new Paragraph("1 page"));          
        document.newPage();  
        document.add(new Paragraph("2 page"));          
        document.newPage();  
        document.add(new Paragraph("3 page"));          
        document.newPage();  
        document.add(new Paragraph("4 page"));  
        //关闭
        document.close();
		
		//拼接
		ArrayList<PdfReader> list = new ArrayList<PdfReader>();
		list.add(new PdfReader("C:\\Users\\admin\\Desktop\\半电子合同\\HZT00封面.pdf"));
		list.add(new PdfReader(bos.toByteArray()));
		list.add(new PdfReader("C:\\Users\\admin\\Desktop\\半电子合同\\HZT00尾页.pdf"));
		PdfTemplateUtil.appendPDF(list, "D:\\A07客户扣款授权书.pdf");
	}
	
	public static void main(String[] args) {
		try {
			testProduce();
//			testProduce_();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
