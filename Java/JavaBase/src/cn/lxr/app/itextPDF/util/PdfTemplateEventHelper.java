package cn.lxr.app.itextPDF.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.pqsoft.base.pdfTemplate.config.EContractParam;
import com.pqsoft.base.pdfTemplate.entity.DocumentSetting;

public class PdfTemplateEventHelper extends PdfPageEventHelper {

	/**
	 * 文档标题
	 */
	public String title = "测试合同";
	
	/**
     * 页眉
     */
    public String header = "版本号：SFLC－HZT00－A/0";
 
	/**
	 * 基础字体对象
	 */
    public BaseFont bf = null;
    
    /**
     * 文档字体大小，页脚页眉最好和文本大小一致
     */
    public int presentFontSize = EContractParam.HEADER_FONT_SIZE;
    
    /**
     * 模板
     */
    public PdfTemplate footerTemplate;
	
	public PdfTemplateEventHelper() throws DocumentException, IOException {
		super();
		this.bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
	}

	public PdfTemplateEventHelper(String title, String header, BaseFont bf) throws DocumentException, IOException {
		super();
		this.title = title;
		this.header = header;
		if (bf == null) {
			this.bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		} else {
			this.bf = bf;
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setBf(BaseFont bf) {
		this.bf = bf;
	}

	public void setPresentFontSize(int presentFontSize) {
		this.presentFontSize = presentFontSize;
	}

	/**
	 * 初始化文档的全局变量
	 * 当打开一个文档时触发
	 */
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		footerTemplate = writer.getDirectContent().createTemplate(50, 50);// 模板
	}
	
	/**
	 * 初始化页面的设置参数
	 * 当一个页面初始化时触发
	 */
	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		//设置文件
		DocumentSetting setting = EContractParam.PDF_SETTING;
		setting.setTitle(title);
		try {
			PdfTemplateUtil.setDocumentDefault(document, setting);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加页眉页脚
	 * 在创建一个新页面完成但写入内容之前触发
	 */
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		try {
			//添加页眉页脚
			this.addPageEdge(writer, document);
			//添加水印
			this.addWaterMark(writer, document);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于释放一些资源
	 * 在文档关闭之前触发
	 */
	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		// 就是关闭文档的时候，将页脚模板替换成实际的 Y 值,至此，page x of y 制作完毕，完美兼容各种文档size。
        footerTemplate.beginText();
        footerTemplate.setFontAndSize(bf, presentFontSize);// 生成的模版的字体、颜色
        String foot2 = " " + (writer.getPageNumber() - 1); //页脚内容拼接  如  第1页/共2页
        footerTemplate.showText(foot2);// 模版显示的内容
        footerTemplate.endText();
        footerTemplate.closePath();
	}
	
	/**
	 * 添加页眉页脚
	 * @param writer
	 * @param document
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws DocumentException 
	 */
	public void addPageEdge(PdfWriter writer, Document document) throws MalformedURLException, IOException, DocumentException{
        //设置分页页眉页脚字体
        Font fontDetail = new Font(bf, presentFontSize, Font.NORMAL);// 数据体字体
        // 1.拿到当前的PdfContentByte
        PdfContentByte cb = writer.getDirectContent();
		
        // 2.写入页眉图片
        Image image = Image.getInstance("C:\\Users\\admin\\Desktop\\半电子合同\\合同模板\\山重租赁横版中英文简称.png");  
        image.scalePercent(EContractParam.HEADER_SCALEPERSENT);//依照比例缩放
        image.setAbsolutePosition(document.left(), document.top() - 8);
        cb.addImage(image);
        Image image2 = Image.getInstance("C:\\Users\\admin\\Desktop\\半电子合同\\合同模板\\页眉线.png");  
        image2.scalePercent(EContractParam.HEADER_LINE_SCALEPERSENT);//依照比例缩放
        image2.setAbsolutePosition(document.left(), document.top() - 15);
        cb.addImage(image2);
        
        // 3.写入页眉文字
        ColumnText.showTextAligned(writer.getDirectContent(),
        		Element.ALIGN_RIGHT, new Phrase(header, fontDetail),
        		document.right(), document.top() - 8, 0);
        
        // 4.创建页脚1
        int pageS = writer.getPageNumber();
        String foot1 = pageS + " / ";
        Phrase footer = new Phrase(foot1, fontDetail);
 
        // 5.计算前半部分的foot1的长度，后面好定位最后一部分的'Y页'这俩字的x轴坐标，字体长度也要计算进去 = len
        float len = bf.getWidthPoint(foot1, presentFontSize);
 
        // 6.写入页脚1，x轴就是(右margin+左margin + right() -left()- len)/2.0F
        // 再给偏移20F适合人类视觉感受，否则肉眼看上去就太偏左了
        // ,y轴就是底边界-20,否则就贴边重叠到数据体里了就不是页脚了；注意Y轴是从下往上累加的，最上方的Top值是大于Bottom好几百开外的。
        ColumnText.showTextAligned(
                        cb,
                        Element.ALIGN_CENTER,
                        footer,
                        (document.rightMargin() + document.right()
                                + document.leftMargin() - document.left() - len) / 2.0F + 20F,
                        document.bottom() - 20, 0);
 
        // 6.写入页脚2的模板（就是页脚的Y页这俩字）添加到文档中，计算模板的和Y轴,X=(右边界-左边界 - 前半部分的len值)/2.0F +
        // len ， y 轴和之前的保持一致，底边界-20
        cb.addTemplate(footerTemplate, (document.rightMargin() + document.right()
                        + document.leftMargin() - document.left()) / 2.0F + 20F,
                document.bottom() - 20); // 调节模版显示的位置
    }
	
	/**
	 * 添加水印
	 * @param writer
	 * @param document
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void addWaterMark(PdfWriter writer, Document document) throws DocumentException, MalformedURLException, IOException {
		//图片水印  
		Image image = Image.getInstance("C:\\Users\\admin\\Desktop\\半电子合同\\合同模板\\水印.png");  
		Rectangle rec = writer.getPageSize();
		float resizedWidth = image.getWidth();
		float resizedHeight = image.getHeight();
		float pageWidth = rec.getWidth();
		float pageHeight = rec.getHeight();
		//依照比例缩放
		image.scalePercent(EContractParam.WATERMARK_SCALEPERSENT);
		//让图片的中心点与页面的中心点进行重合
		image.setAbsolutePosition(pageWidth / 2 - (resizedWidth * EContractParam.WATERMARK_SCALEPERSENT) / 200, pageHeight / 2 - (resizedHeight * EContractParam.WATERMARK_SCALEPERSENT) / 200);
		//居于文件下层
		PdfContentByte under = writer.getDirectContentUnder();
		//设置透明度
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(EContractParam.WATERMARK_OPACITY);
		under.setGState(gs);
		under.addImage(image);  
	}
}
