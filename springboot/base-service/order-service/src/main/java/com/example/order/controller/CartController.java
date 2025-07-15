package com.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Cart;
import com.example.dto.CartDTO;
import com.example.order.feign.BusinessFeignClient;
import com.example.order.feign.GoodsFeignClient;
import com.example.order.service.CartService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    @Resource
    private CartService cartService;

    @Resource
    private GoodsFeignClient goodsFeignClient;

    @Resource
    private BusinessFeignClient businessFeignClient;

    /**
     * 新增
     */
    @SentinelResource(value = "cart_add")
    @PostMapping("/add")
    public R add(@RequestBody Cart cart) {
        Integer goodsId = cart.getGoodsId();
        Cart cart1= new Cart();
        cart1.setGoodsId(goodsId);
        List<Cart> carts = cartService.selectAll(cart1);
        if (carts != null && !carts.isEmpty()) {
            // 如果购物车中已存在该商品，则更新数量
            Cart existingCart = carts.get(0);
            existingCart.setNum(existingCart.getNum() + cart.getNum());
            cartService.updateById(existingCart);
            return R.success();
        }else{
            cartService.save(cart);
            return R.success();
        }

    }

    /**
     * 删除
     */
    @SentinelResource(value = "cart_delete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        cartService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "cart_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        log.info("Deleting carts with IDs: {}", ids);
        cartService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @SentinelResource(value = "cart_update")
    @PutMapping("/update")
    public R updateById(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "cart_select_by_id")
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Cart cart = cartService.getById(id);
        return R.success(cart);
    }

    /**
     * 查询所有
     */
    @SentinelResource(value = "cart_select_all")
    @GetMapping("/selectAll")
    public R selectAll(Cart cart) {
        List<Cart> list = cartService.selectAll(cart);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "cart_select_page")
    @GetMapping("/selectPage")
    public R selectPage(Cart cart,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Cart> page = cartService.selectPage(cart, pageNum, pageSize);
        List<CartDTO> cartDTOList = page.getList().stream()
                .map(CartDTO::new)
                .toList();

        cartDTOList.forEach(item -> {
            var goods = goodsFeignClient.selectGoodsById(item.getGoodsId()).getData();
            item.setGoodsName(goods.getName());
            item.setGoodsImg(goods.getImg());
            item.setGoodsPrice(goods.getPrice());
        });
        cartDTOList.forEach(item -> {
            var business = businessFeignClient.getBusinessById(item.getBusinessId()).getData();
            item.setBusinessName(business.getName());
        });

        return R.success(new PageInfo<>(cartDTOList));
    }

} 