package cn.six2six.outside.admin.service;

import cn.six2six.outside.dal.product.dao.ProductDAO;
import cn.six2six.outside.dal.product.mapping.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public Product selectOne(String productId){
        return productDAO.selectOne(productId);
    }

    public int deleteById(String productId){
        return productDAO.deleteById(productId);
    }

    public List<Product> selectAll(){
        return productDAO.selectAll();
    }

    public int update(Product product){
        return productDAO.updateById(product);
    }

    public int insert(Product product){
        return productDAO.insert(product);
    }

}
