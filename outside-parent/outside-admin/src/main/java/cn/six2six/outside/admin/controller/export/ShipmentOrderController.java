package cn.six2six.outside.admin.controller.export;

import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.dal.export.biz.ShipmentOrderBiz;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:46
 * @description : 出货单 操作
 **/
@RestController
@CrossOrigin
@ApiModel("出货单操作")
@RequestMapping("outside/shipmentOrder")
public class ShipmentOrderController {

    @Resource
    private ShipmentOrderBiz shipmentOrderBiz;

    /**
     * 生成excel
     * @param response
     * @param shipmentOrderId
     * @return
     * @throws IOException
     */
    @GetMapping("export/{shipmentOrderId}")
    @ResponseBody
    public ResultBean getExcel(HttpServletResponse response,
                               @ApiParam(name = "出货单号", value = "shipmentOrderId", required = true) @PathVariable String shipmentOrderId) throws IOException {

        return shipmentOrderBiz.getExcel(response, shipmentOrderId);

    }


}
