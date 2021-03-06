package com.maitianer.demo.common.utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/**
 * @Author Chen
 * @Date 2019/8/29 15:32
 */
public class AsianFontProvider extends XMLWorkerFontProvider {

    @Override
    public Font getFont(final String fontname, String encoding, float size, final int style) {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            return new Font(bfChinese, size, style);
        } catch (Exception e) {
        }
        return super.getFont(fontname, encoding, size, style);
    }
}
