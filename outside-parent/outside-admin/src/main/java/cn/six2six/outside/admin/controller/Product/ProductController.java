package cn.six2six.outside.admin.controller.Product;

import cn.six2six.outside.admin.service.ProductService;
import cn.six2six.outside.common.constant.IDEnum;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.IDMakerUtils;
import cn.six2six.outside.dal.product.mapping.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 商品控制类
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/selectOne",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean selectOne(@Param(value = "productId") String productId){
        Product product = productService.selectOne(productId);
        ResultBean<Product> resultBean;
        if(product != null){
            resultBean = ResultBean.success();
            resultBean.setData(product);
        }else {
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean delete(@Param(value = "productId") String productId){
        int result = productService.deleteById(productId);
        ResultBean<Product> resultBean;
        if(result > 0){
            resultBean = ResultBean.success();
        }else {
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }


    @RequestMapping(value = "/selectAll",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean selectAll(){
        List<Product> products = productService.selectAll();
        ResultBean<List<Product>> resultBean;
        if(products != null && products.size() > 0){
            resultBean = ResultBean.success();
            resultBean.setData(products);
        }else {
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean update(Product product){
        // 如果没传入更新时间，默认为当前时间
        if(product.getUpdateTime() == null){
            product.setUpdateTime(new Date());
        }
        int update = productService.update(product);
        ResultBean<Product> resultBean;
        if(update > 0){
            resultBean = ResultBean.success();
        }else {
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }



    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean insertProduct(Product product){
        product.setProductId(IDMakerUtils.makeID(IDEnum.PRODUCT_ID));
        product.setCreateTime(new Date());
        product.setUpdateTime(product.getCreateTime());
        int result = productService.insert(product);
        ResultBean<Product> resultBean;
        if(result > 0){
            resultBean = ResultBean.success();
        }else {
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }

}
