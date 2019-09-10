package com.maitianer.demo.common.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author Chen
 * @Date 2019/8/29 15:43
 */
public class HtmlToPdfUtils {

    public static void htmltoPdf(String content,HttpServletResponse response){
        Document document = new Document();
        try {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String( (new Date().getTime()+".pdf").getBytes(),  "iso-8859-1"));
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            //这里是必须要设置编码的，不然导出中文就会乱码。
            byte b[] = content.getBytes("UTF-8");
            //将字节数组包装到流中
            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais, Charset.forName("UTF-8"), new AsianFontProvider());

            bais.close();
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
