package org.dromara.homework.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.dromara.homework.dto.ProductDto;
import org.dromara.homework.dto.ProductQueryDto;
import org.dromara.homework.entity.ProductEntity;
import org.dromara.homework.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @RequestMapping("/page")
    public SaResult pageQuery(@RequestBody ProductQueryDto productQueryDto){

        return SaResult.data( productService.selectPage(productQueryDto));
    }
    @RequestMapping("/page/myProduct")
    public SaResult myPageQuery(@RequestBody ProductQueryDto productQueryDto){
       // System.out.println(StpUtil.getLoginIdAsInt());
        return SaResult.data( productService.selectMyPage(productQueryDto,StpUtil.getLoginIdAsInt()));

    }
    @RequestMapping("/add")
    public SaResult addProduct(ProductDto productDto) {

            // 创建一个新的产品实体
        try {
            // 调用 service 添加产品
            System.out.println(productDto);
            Integer id= StpUtil.getLoginIdAsInt();
            System.out.println(id);
            productService.addProduct(productDto,id);
        return SaResult.data("产品添加成功！");

        } catch (IOException e) {
            return SaResult.error("添加产品失败：" + e.getMessage());
        }
    }
    @RequestMapping("/delete/{productId}")
    public SaResult deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return SaResult.ok("删除成功");
    }

    @RequestMapping("/image/add")
    public SaResult addImage(MultipartFile file){
        try{
            Integer id=productService.addImage(file);
            return SaResult.data(id);
        }catch (IOException e) {
            return SaResult.error("文件上传失败：" + e.getMessage());
        }
    }
    @RequestMapping("/image/delete/{imageId}")
    public SaResult deleteById(@PathVariable Integer imageId){
        productService.deleteImageById(imageId);
        return SaResult.data("ok");

    }
    @RequestMapping("/update/{productId}")
    public SaResult updateProductById(@PathVariable Integer productId,@RequestBody ProductDto productDto){
        System.out.println(productId);
        productService.updateByDto(productId,productDto);
        return SaResult.ok("true");
    }
    @GetMapping("/image/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer productId) {
        try {
            // 获取产品信息
            byte[] bt= productService.getImage(productId);

            if (bt != null) {
                // 如果有图片，返回二进制数据
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 这里假设是 JPEG 图片，可以根据需要调整
                        .body(bt);
            } else {
                // 如果没有图片，返回 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
