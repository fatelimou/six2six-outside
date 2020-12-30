package cn.six2six.outside.dal.product.biz;

import cn.six2six.outside.dal.product.dao.ProductDAO;
import cn.six2six.outside.dal.product.mapping.Product;
import cn.six2six.outside.dal.product.mapping.ProductDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductBiz {

    @Resource
    private ProductDAO productDAO;

    public Product selectOne(String productId){
        return productDAO.selectOne(productId);
    }

    public List<Product> selectAll(){
        return productDAO.selectAll();
    }

    public ProductDetail queryOneDetail(String productDetailId){
        return productDAO.selectOneDetail(productDetailId);
    }

    public List<ProductDetail> queryAllDetail(String productId){
        return productDAO.selectAllDetail(productId);
    }

}
