package cn.six2six.outside.dal.product.service;

import cn.six2six.outside.dal.product.dao.ProductDAO;
import cn.six2six.outside.dal.product.mapping.Product;
import cn.six2six.outside.dal.product.mapping.ProductDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : ZengRong
 * @date : 2020-12-29 10:35
 * @description : 商品事务操作
 **/
@Service
@Transactional
public class ProductService {

    @Resource
    private ProductDAO productDAO;

    public int insert(Product product){
        return productDAO.insert(product);
    }

    public int update(Product product){
        return productDAO.updateById(product);
    }

    public int deleteById(String productId){
        return productDAO.deleteById(productId);
    }

    public int saveDetail(ProductDetail productDetail){
        return productDAO.saveDetail(productDetail);
    }

    public int modifyDetail(ProductDetail productDetail){
        return productDAO.modifyDetail(productDetail);
    }

    public int removeByIdDetail(String productDetailId){
        return productDAO.removeByIdDetail(productDetailId);
    }


}
