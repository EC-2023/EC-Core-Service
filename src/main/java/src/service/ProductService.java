
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Product;
import src.repository.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private  IProductRepository productRepository;
    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> getOne(UUID id) {
        return productRepository.findById(id);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(UUID id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {

            productRepository.save(existingProduct);
        }
        return existingProduct;
    }

    public void remove(UUID id) {
        productRepository.deleteById(id);
    }
}
