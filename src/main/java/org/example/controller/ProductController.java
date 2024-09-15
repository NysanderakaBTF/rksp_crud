package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getImage")
    public ResponseEntity<Resource> getImage() throws IOException {
        Path path = Paths.get("./MIREA_Gerb_Colour.png");
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
        return mappingResponseProduct(productService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Product>> readAll() {
        return mappingResponseListProduct(productService.readAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> readByCategoryId(@PathVariable Long id) {
        return mappingResponseListProduct(productService.readByCategoryId(id));
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return mappingResponseProduct(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Product> mappingResponseProduct(Product product) {
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private ResponseEntity<List<Product>> mappingResponseListProduct(List<Product> products) {
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
