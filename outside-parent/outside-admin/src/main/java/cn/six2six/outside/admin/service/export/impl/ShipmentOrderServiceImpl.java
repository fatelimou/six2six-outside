package cn.six2six.outside.admin.service.export.impl;

import cn.six2six.outside.admin.service.export.ShipmentOrderService;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.ConvertUpMoneyUtils;
import cn.six2six.outside.dal.order.dao.ShipmentOrderDao;
import cn.six2six.outside.dal.export.dao.ShipOrderExcelDao;
import cn.six2six.outside.dal.user.mapping.ShipmentOrder;
import cn.six2six.outside.dal.export.mapping.ShipOrderToExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.sun.deploy.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:50
 * @description : 出货单 业务实现层
 **/
@Service
@Slf4j
public class ShipmentOrderServiceImpl implements ShipmentOrderService {


    @Autowired
    ShipmentOrderDao shipmentOrderDao;

    @Autowired
    ShipOrderExcelDao shipOrderExcelDao;




    /**
     * 根据出货单号查询 数据库中excel表所需信息
     * @param shipmentOrderId 出货单号
     * @return
     */
    @Override
    public ResultBean getExcel(HttpServletResponse response, String shipmentOrderId) throws IOException {

        List<ShipOrderToExcel> allExcel = shipOrderExcelDao.getAllExcel(shipmentOrderId);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode防止中文乱码
        String fileName = URLEncoder.encode("结算单", "UTF-8");
        response.setHeader("Content-disposition",  "attachment;filename=" + fileName + ".xlsx");
        ShipmentOrder shipmentOrder = getById(shipmentOrderId);


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

        OutputStream out=response.getOutputStream();

        out.flush();

        workbook.write(out);

        workbook.close();

        out.close();
        return ResultBean.success();
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
    public Cell getCell(XSSFRow row,int rowClum,Workbook workbook,String value){
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
    @Override
    public ShipmentOrder getById(String shipmentOrderId) {
        ShipmentOrder ShipmentOrder = shipmentOrderDao.getById(shipmentOrderId);
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
    public CellStyle addAttrRow(Workbook workbook,String alignment){
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
