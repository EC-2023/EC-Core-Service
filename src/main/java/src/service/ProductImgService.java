
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.ProductImg;
import src.repository.IProductImgRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductImgService {
    private  IProductImgRepository productimgRepository;
    @Autowired
    public ProductImgService(IProductImgRepository productimgRepository) {
        this.productimgRepository = productimgRepository;
    }

    public List<ProductImg> getAll() {
        return (List<ProductImg>) productimgRepository.findAll();
    }

    public Optional<ProductImg> getOne(UUID id) {
        return productimgRepository.findById(id);
    }

    public ProductImg create(ProductImg productimg) {
        return productimgRepository.save(productimg);
    }

    public ProductImg update(UUID id, ProductImg productimg) {
        ProductImg existingProductImg = productimgRepository.findById(id).orElse(null);
        if (existingProductImg != null) {

            productimgRepository.save(existingProductImg);
        }
        return existingProductImg;
    }

    public void remove(UUID id) {
        productimgRepository.deleteById(id);
    }
}
