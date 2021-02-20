package cn.six2six.outside.dal.export.biz;

import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.ConvertUpMoneyUtils;
import cn.six2six.outside.dal.export.bean.PicturesInfo;
import cn.six2six.outside.dal.export.dao.ShipOrderExcelDAO;
import cn.six2six.outside.dal.export.mapping.ShipOrderToExcel;
import cn.six2six.outside.dal.shipment.biz.ShipmentBiz;
import cn.six2six.outside.dal.shipment.mapping.ShipmentOrder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:50
 * @description : 导出excel 业务实现层
 **/
@Service
@Slf4j
public class ShipmentOrderBiz {


    @Resource
    private ShipmentBiz shipmentBiz;

    @Resource
    private ShipOrderExcelDAO shipOrderExcelDAO;

    /**
     * 生成excel状态返回
     * @param response
     * @param shipmentOrderId 出货单单号
     * @return
     * @throws IOException
     */
    public ResultBean resultExcelStatus(HttpServletResponse response, String shipmentOrderId) throws IOException {
        log.info("开始生成excel");
        int status = exprotExcel(response, shipmentOrderId);
        if(status == 0){
            log.info("查询数据失败，无法生成excel");
            return ResultBean.failed();
        }
        log.info("生成excel完成");
        return ResultBean.success();
    }

    /**
     * excel转pdf
     * @param response
     * @param shipmentOrderId
     * @return
     * @throws IOException
     */
    public ResultBean resultPDFStatus(HttpServletResponse response, String shipmentOrderId) throws Exception {
        response = setResponse(response);
        String fileName = URLEncoder.encode("结算单", "UTF-8");
        response.setHeader("Content-disposition",  "attachment;filename=" + fileName + ".pdf");
        Workbook workbook = getExcel(shipmentOrderId);


        Sheet sheet = workbook.getSheetAt(0);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);//此处根据excel大小设置pdf纸张大小
        PdfWriter writer = PdfWriter.getInstance(document, stream);
        document.setMargins(0, 0, 15, 15);//设置也边距
        document.open();
        float[] widths = getColWidth(sheet);

        PdfPTable table = new PdfPTable(widths);
        table.setWidthPercentage(90);
        int colCount = widths.length;
        BaseFont baseFont = BaseFont.createFont("Fonts\\simsun.ttc,0", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);//设置基本字体
        for (int r = sheet.getFirstRowNum(); r < sheet.getPhysicalNumberOfRows(); r++) {
            Row row = sheet.getRow(r);
            if(row != null){
                for (int c = row.getFirstCellNum(); (c < row.getLastCellNum() || c < colCount) && c > -1; c++){
                    if (c >= row.getPhysicalNumberOfCells()) {
                        PdfPCell pCell = new PdfPCell(new Phrase(""));
                        pCell.setBorder(0);
                        table.addCell(pCell);
                        continue;
                    }
                    Cell excelCell = row.getCell(c);
                    String value = "";
                    if (excelCell != null){
                        value = excelCell.toString().trim();
                        if (value != null && value.length() != 0) {
                            String dataFormat = excelCell.getCellStyle().getDataFormatString();//获取excel单元格数据显示样式
                            if (dataFormat != "General" && dataFormat != "@"){
                                try {
                                    String numStyle = getNumStyle(dataFormat);
                                    value = numFormat(numStyle, excelCell.getNumericCellValue());
                                } catch (Exception e) {

                                }
                            }
                            }
                        }
                    org.apache.poi.ss.usermodel.Font excelFont = getExcelFont(excelCell);

                    //HSSFFont excelFont = excelCell.getCellStyle().getFont(workbook);

                    /*Font pdFont = new Font(baseFont, excelFont.getFontHeightInPoints(),
                            excelFont.getFontHeight() == 500 ? com.itextpdf.text.Font.BOLD : com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);//设置单元格字体
                    */
                    com.itextpdf.text.Font pdFont = new com.itextpdf.text.Font(baseFont, excelFont.getFontHeightInPoints(),
                            excelFont.getFontHeight() == 500 ? com.itextpdf.text.Font.BOLD : com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
                    PdfPCell pCell = new PdfPCell(new Phrase(value, pdFont));
                    List<PicturesInfo> infos  = POIExtend.getAllPictureInfos(sheet, r, r, c, c, false);
                    if (!infos.isEmpty()){
                        pCell = new PdfPCell(Image.getInstance(infos.get(0).getPictureData()));
                        PicturesInfo info = infos.get(0);
                        System.out.println("最大行：" + info.getMaxRow() + "最小行：" + info.getMinRow() + "最大列:" + info.getMaxCol() + "最小列：" + info.getMinCol());;
                    }
                    boolean hasBorder = hasBorder(excelCell);
                    if (!hasBorder){
                        pCell.setBorder(0);
                    }
                    pCell.setHorizontalAlignment(getHorAglin(excelCell.getCellStyle().getAlignment()));
                    pCell.setVerticalAlignment(getVerAglin(excelCell.getCellStyle().getVerticalAlignment()));

                    pCell.setMinimumHeight(row.getHeightInPoints());
                    if (isMergedRegion(sheet, r, c)) {
                        int[] span = getMergedSpan(sheet, r, c);
                        if (span[0] == 1 && span[1] == 1) {//忽略合并过的单元格
                            continue;
                        }
                        pCell.setRowspan(span[0]);
                        pCell.setColspan(span[1]);
                        c = c + span[1] - 1;//合并过的列直接跳过
                    }

                    table.addCell(pCell);
                }
                }else {
                PdfPCell pCell = new PdfPCell(new Phrase(""));
                pCell.setBorder(0);
                pCell.setMinimumHeight(13);
                table.addCell(pCell);
                }
            }
        document.add(table);
        document.close();

        byte[] pdfByte = stream.toByteArray();
        stream.flush();
        stream.reset();
        stream.close();

        OutputStream out= response.getOutputStream();
        out.write(pdfByte);
        out.flush();
        closeIO(workbook,out);

        return ResultBean.success();
    }

    /**
     * 计算合并单元格合并的跨行跨列数
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private static int[] getMergedSpan(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        int[] span = { 1, 1 };
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (firstColumn == column && firstRow == row) {
                span[0] = lastRow - firstRow + 1;
                span[1] = lastColumn - firstColumn + 1;
                break;
            }
        }
        return span;
    }

    /**
     * 判断单元格是否是合并单元格
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * excel垂直对齐方式映射到pdf对齐方式
     * @param aglin
     * @return
     */
    private static int getVerAglin(int aglin) {
        switch (aglin) {
            case 1:
                return com.itextpdf.text.Element.ALIGN_MIDDLE;
            case 2:
                return com.itextpdf.text.Element.ALIGN_BOTTOM;
            case 3:
                return com.itextpdf.text.Element.ALIGN_TOP;
            default:
                return com.itextpdf.text.Element.ALIGN_MIDDLE;
        }
    }

    /**
     * 判断excel单元格是否有边框
     * @param excelCell
     * @return
     */
    private static boolean hasBorder(Cell excelCell) {
        short top = excelCell.getCellStyle().getBorderTop();
        short bottom = excelCell.getCellStyle().getBorderBottom();
        short left = excelCell.getCellStyle().getBorderLeft();
        short right = excelCell.getCellStyle().getBorderRight();
        return top + bottom + left + right > 2;
    }

    /**
     * excel水平对齐方式映射到pdf水平对齐方式
     * @param aglin
     * @return
     */
    private static int getHorAglin(int aglin) {
        switch (aglin) {
            case 2:
                return com.itextpdf.text.Element.ALIGN_CENTER;
            case 3:
                return com.itextpdf.text.Element.ALIGN_RIGHT;
            case 1:
                return com.itextpdf.text.Element.ALIGN_LEFT;
            default:
                return com.itextpdf.text.Element.ALIGN_CENTER;
        }
    }

    /**
     * //获取字体
     * @param cell
     * @return
     */
    private static org.apache.poi.ss.usermodel.Font getExcelFont(Cell cell) {
        /*if (excelName.endsWith(".xls")){
            return ((HSSFCell)cell).getCellStyle().getFont(workbook);
        }*/
        return ((XSSFCell) cell).getCellStyle().getFont();
    }
        /**
         * 格式化数字
         * @param pattern
         * @param num
         * @return
         */
    private static String numFormat(String pattern, double num){
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(num);
    }

    /**
     * 获取excel单元格数据显示格式
     * @param dataFormat
     * @return
     * @throws Exception
     */
    private static String getNumStyle(String dataFormat) throws Exception {
        if (dataFormat == null || dataFormat.length() == 0){
            throw new Exception("");
        }
        if (dataFormat.indexOf("%") > -1){
            return dataFormat;
        } else{
            return dataFormat.substring(0, dataFormat.length()-2);
        }

    }

    /**
     * 获取excel中列数最多的行号
     * @param sheet
     * @return
     */
    private static int getMaxColRowNum(Sheet sheet) {
        int rowNum = 0;
        int maxCol = 0;
        for (int r = sheet.getFirstRowNum(); r < sheet.getPhysicalNumberOfRows(); r++) {
            Row row = sheet.getRow(r);
            if (row != null && maxCol < row.getPhysicalNumberOfCells()) {
                maxCol = row.getPhysicalNumberOfCells();
                rowNum = r;
            }
        }
        return rowNum;
    }

    /**
     * 获取excel中每列宽度的占比
     * @param sheet
     * @return
     */
    private static float[] getColWidth(Sheet sheet) {
        int rowNum = getMaxColRowNum(sheet);
        Row row = sheet.getRow(rowNum);
        int cellCount = row.getPhysicalNumberOfCells();
        int[] colWidths = new int[cellCount];
        int sum = 0;

        for (int i = row.getFirstCellNum(); i < cellCount; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                colWidths[i] = sheet.getColumnWidth(i);
                sum += sheet.getColumnWidth(i);
            }
        }

        float[] colWidthPer = new float[cellCount];
        for (int i = row.getFirstCellNum(); i < cellCount; i++) {
            colWidthPer[i] = (float) colWidths[i] / sum * 100;
        }
        return colWidthPer;
    }

    /**
     * 输出excel文件
     * @param response
     * @param shipmentOrderId 出货单号
     * @throws IOException
     */
    private int exprotExcel(HttpServletResponse response, String shipmentOrderId) throws IOException{
        response = setResponse(response);
        String fileName = URLEncoder.encode("结算单", "UTF-8");
        response.setHeader("Content-disposition",  "attachment;filename=" + fileName + ".xlsx");
        XSSFWorkbook workbook = getExcel(shipmentOrderId);
        if(workbook == null){
            return 0;
        }
        OutputStream out=response.getOutputStream();

        out.flush();

        workbook.write(out);

        closeIO(workbook,out);

        return 1;
    }

    /**
     * 设置response  Content-Type类型和编码
     * @param response
     * @return
     */
    private HttpServletResponse setResponse(HttpServletResponse response){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode防止中文乱码
        return response;
    }

    /**
     * 关流
     * @param workbook
     * @param out
     */
    private void closeIO(Workbook workbook,OutputStream out) throws IOException{
        if(workbook != null){
            workbook.close();
        }

        if(out != null){
            out.close();
        }

    }

    /**
     * 生成excel
     * @param shipmentOrderId 出货单号
     * @return
     */
    private XSSFWorkbook getExcel(String shipmentOrderId)  {

        List<ShipOrderToExcel> allExcel = shipOrderExcelDAO.getAllExcel(shipmentOrderId);

        if(allExcel.size() < 0 || allExcel == null){
            return null;
        }

        ShipmentOrder shipmentOrder = queryById(shipmentOrderId);

        if(shipmentOrder == null){
            return null;
        }

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("结算单");//获取工作表
        //设置表头宽度
        sheet.setColumnWidth(0, 20 * 180);
        sheet.setColumnWidth(1, 20 * 180);
        sheet.setColumnWidth(2, 20 * 180);
        sheet.setColumnWidth(3, 20 * 180);
        sheet.setColumnWidth(4, 20 * 150);
        sheet.setColumnWidth(5, 20 * 180);
        sheet.setColumnWidth(6, 20 * 400);
        sheet.setColumnWidth(7, 20 * 180);


        System.out.println(shipmentOrder.getShipmentOrderId());

        Row rowTop = sheet.createRow(0);
        rowTop.setHeight((short) 500);
        Cell cellTop = rowTop.createCell(0);
        //设置单元格内容
        cellTop.setCellValue("附件2");

        //设置标题
        Row rowTitle = sheet.createRow(1);
        rowTitle.setHeight((short) 600);
        Cell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue("四川澜集川结算单");
        cellTitle.setCellStyle(addAttr(workbook));
        //合并单元格
        sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 0, 7));  //起始行号，终止行号， 起始列号，终止列号

        //设置单号，日期
        Row rowOrderId = sheet.createRow(2);
        rowOrderId.setHeight((short) 300);
        Cell cellOrderId = rowOrderId.createCell(0);
        cellOrderId.setCellValue("单号："+shipmentOrder.getShipmentOrderId());
        cellOrderId.setCellStyle(addAttrRow(workbook,"LEFT"));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 0, 3));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 4, 7));

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd");
        Cell cellDate = rowOrderId.createCell(4);
        cellDate.setCellValue("日期："+df.format(date) + "       ");
        cellDate.setCellStyle(addAttrRow(workbook,"RIGHT"));

        //设置进货公司，付款方式
        Row rowCompany = sheet.createRow(3);
        rowCompany.setHeight((short) 300);
        Cell cellCompany = rowCompany.createCell(0);
        cellCompany.setCellValue("进货公司：");
        cellCompany.setCellStyle(addAttrRow(workbook,"LEFT"));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(3, 3, 0, 3));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(3, 3, 4, 7));

        Cell cellPay = rowCompany.createCell(4);
        cellPay.setCellValue("付款方式：                ");
        cellPay.setCellStyle(addAttrRow(workbook,"RIGHT"));

        XSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);//在现有行号后追加数据
        Cell cell = getCell(row,0,workbook,"序号");
        cell = getCell(row,1,workbook,"商品名");
        cell = getCell(row,2,workbook,"规格");
        cell = getCell(row,3,workbook,"单位");
        cell = getCell(row,4,workbook,"结算数量");
        cell = getCell(row,5,workbook,"单价");
        cell = getCell(row,6,workbook,"金额");
        cell = getCell(row,7,workbook,"备注");


        for (int i = 0; i < allExcel.size(); i++) {
            row = sheet.createRow(sheet.getLastRowNum()+1);
            cell = getCellTwo(row,0, workbook,i+1+"");
            cell = getCellTwo(row,1, workbook,allExcel.get(i).getItemName());
            cell = getCellTwo(row,2, workbook,allExcel.get(i).getModel());
            cell = getCellTwo(row,3, workbook,allExcel.get(i).getUnit());
            cell = getCellTwo(row,4, workbook,allExcel.get(i).getQuantity());
            cell = getCellTwo(row,5, workbook,allExcel.get(i).getPrice());
            cell = getCellTwo(row,6, workbook,"￥   "+allExcel.get(i).getTotalPrice());
            cell = getCellTwo(row,7, workbook,"        ");

        }

        //设置合计
        row = sheet.createRow(sheet.getLastRowNum()+1);
        row.setHeight((short) 300);
        Cell cellTotal = row.createCell(0);
        cellTotal.setCellValue("合计金额");
        //设置字体样式
        CellStyle cellStyleTotal = workbook.createCellStyle();
        cellStyleTotal.setFont(addAttrBold(workbook));
        cellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        cellStyleTotal.setAlignment(HorizontalAlignment.CENTER);  //  水平居中
        cellTotal.setCellStyle(cellStyleTotal);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 1, 4)); //起始行号，终止行号， 起始列号，终止列号
        sheet.addMergedRegionUnsafe(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 6, 7));

        Cell cellNumChina = row.createCell(1);
        ConvertUpMoneyUtils convertUpMoney = new ConvertUpMoneyUtils();
        cellNumChina.setCellValue( convertUpMoney.toChina(allExcel.get(0).getTotalPrice()));
        cellNumChina.setCellStyle(cellStyleTotal);

        Cell cellNum = row.createCell(6);
        cellNum.setCellValue("￥" + allExcel.get(0).getTotalPrice());
        cellNum.setCellStyle(cellStyleTotal);

        //设置提示信息
        row = sheet.createRow(sheet.getLastRowNum()+1);//在现有行号后追加数据

        row.setHeight((short) 600);
        Cell cellHint = row.createCell(0);
        CellStyle cellStyleHint = workbook.createCellStyle();
        cellStyleHint.setFont(addAttrBold(workbook));
        cellStyleHint.setWrapText(true);  //自动换行
        cellHint.setCellStyle(cellStyleHint);
        cellHint.setCellValue("1 保管应严格按照本提货单注明的品名丶规格丶数量及车号发货。2本提货单即货权凭证，属列内容等同于合同。3未经本公司认可，任何人不得变更结算单内容，否则结算单视为无效");
        sheet.addMergedRegionUnsafe(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 7));

        //设置类型，提货车号
        row = sheet.createRow(sheet.getLastRowNum()+1);//在现有行号后追加数据
        row.setHeight((short) 300);
        Cell cellType = row.createCell(0);
        cellType.setCellValue("自联存根、黄联财务、红联客户");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(addAttrBold(workbook));
        cellType.setCellStyle(cellStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 3));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 4, 7));

        Cell cellPlate = row.createCell(4);
        cellPlate.setCellValue("提货车号：");
        cellPlate.setCellStyle(cellStyle);


        //设置制单 发货人 审核人 收货人
        row = sheet.createRow(sheet.getLastRowNum()+1);//在现有行号后追加数据
        Cell cellButtom = row.createCell(0);
        cellButtom.setCellStyle(cellStyle);
        cellButtom.setCellValue("制单：                    发货人：                     审核人：                       收货人：");
        sheet.addMergedRegionUnsafe(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 7));

        return workbook;
    }


    /**
     * 订单数据字体样式设置
     * @param row
     * @param rowClum 列
     * @param workbook
     * @param value 值
     * @return
     */
    private Cell getCellTwo(XSSFRow row,int rowClum,Workbook workbook,String value){
        Cell cell = row.createCell(rowClum);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(addAttrBold(workbook));
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //  水平居中
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

    /**
     *表头内容样式设置
     * @param workbook
     * @param row
     * @param rowClum 列
     * @param value
     * @return
     */
    private Cell getCell(XSSFRow row,int rowClum,Workbook workbook,String value){
        Cell cell = row.createCell(rowClum);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(addAttrBold(workbook));
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //  水平居中
        cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * 根据id 查询出货单信息
     * @param shipmentOrderId 出货单号
     * @return 出货数据
     */
    public ShipmentOrder queryById(String shipmentOrderId) {
        ShipmentOrder ShipmentOrder = shipmentBiz.queryById(shipmentOrderId);
        return ShipmentOrder;
    }

    /**
     * 字体加粗
     * @param workbook
     * @return
     */
    private Font addAttrBold(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("黑体");
        return font;
    }


    /**
     * 表格样式设置
     * @return
     */
    private WriteCellStyle setWriteCellStyle(){
        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteFont.setBold(true);
        contentWriteFont.setFontName("黑体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return contentWriteCellStyle;
    }


    /**
     * 单号,日期,进货公司，付款方式内容样式设置
     * @param workbook
     * @param alignment  内容水平居左
     *
     * @return
     */
    private CellStyle addAttrRow(Workbook workbook,String alignment){
        Font font = addAttrBold(workbook);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if("LEFT".equals(alignment)){
            cellStyle.setAlignment(HorizontalAlignment.LEFT);  //内容左对齐
        }else if("RIGHT".equals(alignment)){
            cellStyle.setAlignment(HorizontalAlignment.RIGHT); //内容右对齐
        }
        cellStyle.setBorderRight(BorderStyle.NONE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 标题边框内容样式
     * @param workbook
     * @return
     */
    private CellStyle addAttr(Workbook workbook){
        Font font = addAttrBold(workbook);
        font.setFontHeight((short) 400);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //  水平居中
        cellStyle.setFont(font);
        return cellStyle;
    }

}
