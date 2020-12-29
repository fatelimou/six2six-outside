package cn.six2six.outside.admin.controller.Product;

import cn.six2six.outside.common.constant.IDEnum;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.IDMakerUtils;
import cn.six2six.outside.dal.product.biz.ProductBiz;
import cn.six2six.outside.dal.product.mapping.Product;
import cn.six2six.outside.dal.product.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品控制类
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductBiz productBiz;

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/selectOne",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean selectOne(@Param(value = "productId") String productId){
        // 根据id查询商品详情
        Product product = productBiz.selectOne(productId);
        ResultBean<Product> resultBean;
        // 如果查询商品不为空，提示操作成功
        if(product != null){
            resultBean = ResultBean.success();
            resultBean.setData(product);
        }else {
            // 否则提示操作失败
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean delete(@Param(value = "productId") String productId){
        // 根据商品id删除记录
        int result = productService.deleteById(productId);
        return getResultBean(result);
    }


    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean selectAll(){
        // 查询所有商品
        List<Product> products = productBiz.selectAll();
        ResultBean<List<Product>> resultBean;
        // 查询所有记录不为空且记录一条以上
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
        // 更新记录条数大于0条，提示操作成功
        return getResultBean(update);
    }



    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean insertProduct(Product product){
        // 创建唯一性id
        product.setProductId(IDMakerUtils.makeID(IDEnum.PRODUCT_ID));
        // 设置创建时间为当前时间
        product.setCreateTime(new Date());
        // 第一次插入时间也为当前时间
        product.setUpdateTime(product.getCreateTime());
        int result = productService.insert(product);
        return getResultBean(result);
    }

    private ResultBean getResultBean(int result){
        ResultBean<Product> resultBean;
        if(result > 0){
            resultBean = ResultBean.success();
        }else {
            resultBean = ResultBean.failed();
        }
        return resultBean;
    }

}
