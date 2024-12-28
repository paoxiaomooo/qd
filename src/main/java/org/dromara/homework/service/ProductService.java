package org.dromara.homework.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.homework.dto.ProductDto;
import org.dromara.homework.dto.ProductQueryDto;
import org.dromara.homework.entity.ImageEntity;
import org.dromara.homework.entity.ProductEntity;
import org.dromara.homework.entity.UserProductEntity;
import org.dromara.homework.mapper.ImageMapper;
import org.dromara.homework.mapper.ProductMapper;
import org.dromara.homework.mapper.UserProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private UserProductMapper userProductMapper;
    // 分页查询方法
    public IPage<ProductEntity> selectPage(ProductQueryDto productQueryDto) {
        // 创建分页对象
        Page<ProductEntity> productPage = new Page<>(productQueryDto.getPage(), productQueryDto.getPageSize());

        // 构建查询条件
        QueryWrapper<ProductEntity> queryWrapper = buildQueryWrapper(productQueryDto);

        // 查询分页数据，不包括图片
        Page<ProductEntity> page = productMapper.selectPage(productPage, queryWrapper);
        return page;
    }
    public IPage<ProductEntity> selectMyPage(ProductQueryDto productQueryDto,Integer id){
        //System.out.println(id);
        Page<UserProductEntity>Page=new Page<>(productQueryDto.getPage(), productQueryDto.getPageSize());
        QueryWrapper<UserProductEntity>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        IPage<UserProductEntity>userProductEntityPage=userProductMapper.selectPage(Page,queryWrapper);
        System.out.println(userProductEntityPage.getRecords());
        //提取id
        List<Integer> productIds = userProductEntityPage.getRecords().stream()
                .map(UserProductEntity::getProductId)
                .toList();
        //System.out.println(productIds.toString());
        if(productIds.isEmpty()){
            return new Page<ProductEntity>();
        }
        Page<ProductEntity>productEntityPage=new Page<>(productQueryDto.getPage(), productQueryDto.getPageSize());
        QueryWrapper<ProductEntity>queryWrapper1=new QueryWrapper<>();
        queryWrapper1.in("id",productIds);
        return productMapper.selectPage(productEntityPage,queryWrapper1);
    }
    public Integer addImage(MultipartFile file) throws IOException {
        ImageEntity image=new ImageEntity();
        image.setImage(file.getBytes());
        imageMapper.insert(image);
        return image.getId();
    }
    public byte[] getImage(Integer id){
        QueryWrapper<ImageEntity>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("product_id",id);
        return imageMapper.selectOne(queryWrapper).getImage();
    }
    public ProductEntity getProductById(Integer id){
        return productMapper.selectById(id);
    }
    public void deleteProductById(Integer id){
        productMapper.deleteById(id);
    }
    public void deleteImageById(Integer id){imageMapper.deleteById(id);}
    // 新增产品
    public void deleteProduct(Integer id){
        QueryWrapper<UserProductEntity>queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("product_id",id);
        userProductMapper.delete(queryWrapper1);
        QueryWrapper<ImageEntity>queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("product_id",id);
        imageMapper.delete(queryWrapper2);
        productMapper.deleteById(id);

    }
    public void updateByDto(Integer productId,ProductDto productDto){
        UpdateWrapper<ProductEntity>updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",productId);
        updateWrapper.set("name",productDto.getName());
        updateWrapper.set("price",productDto.getPrice());
        updateWrapper.set("description",productDto.getDescription());
        updateWrapper.set("category",productDto.getCategory());
        updateWrapper.set("stock",productDto.getStock());
        productMapper.update(updateWrapper);

        if (productDto.getImage_id() != 0) {
            // 查询当前绑定的图片ID
            QueryWrapper<ImageEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id", productId);
            ImageEntity currentImage = imageMapper.selectOne(queryWrapper);

            // 如果存在老图片并且老图片ID和新图片ID不同，删除老图片
            if (currentImage != null && !currentImage.getId().equals(productDto.getImage_id())) {
                imageMapper.delete(queryWrapper);
            }

            // 更新新图片的关联信息
            UpdateWrapper<ImageEntity> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.eq("id", productDto.getImage_id());
            updateWrapper1.set("product_id", productId);
            imageMapper.update(null, updateWrapper1);
        }


    }
    public void addProduct(ProductDto productDto,Integer id) throws IOException {
        // 如果上传了图片，则将其存储到产品实体中
        ProductEntity product=new ProductEntity();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setStock(productDto.getStock());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        productMapper.insert(product);
        UserProductEntity userProductEntity=new UserProductEntity();
        //用户名和产品id对应
        userProductEntity.setUserId(id);
        userProductEntity.setProductId(product.getId());
        userProductMapper.insert(userProductEntity);
         // 插入数据库
        UpdateWrapper<ImageEntity>updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",productDto.getImage_id());
        updateWrapper.set("product_id",product.getId());
        imageMapper.update(updateWrapper);
    }

    private QueryWrapper<ProductEntity> buildQueryWrapper(ProductQueryDto productQueryDto) {
        QueryWrapper<ProductEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(productQueryDto.getSearchQuery())) {
            queryWrapper.lambda()
                    .like(ProductEntity::getName, productQueryDto.getSearchQuery())
                    .or()
                    .like(ProductEntity::getDescription, productQueryDto.getSearchQuery());
        }
        if (StringUtils.hasText(productQueryDto.getCategory())) {
            queryWrapper.lambda()
                    .eq(ProductEntity::getCategory, productQueryDto.getCategory());
        }
        return queryWrapper;
    }
}

