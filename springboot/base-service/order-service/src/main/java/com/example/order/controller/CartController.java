package com.example.order.controller;

import com.example.common.R;
import com.example.entity.Cart;
import com.example.dto.CartDTO;
import com.example.order.feign.BusinessFeignClient;
import com.example.order.feign.GoodsFeignClient;
import com.example.order.service.CartService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private GoodsFeignClient goodsFeignClient;

    @Resource
    private BusinessFeignClient businessFeignClient;

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody Cart cart) {
        cartService.save(cart);
        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        cartService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        cartService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R updateById(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Cart cart = cartService.getById(id);
        return R.success(cart);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public R selectAll(Cart cart) {
        List<Cart> list = cartService.selectAll(cart);
        return R.success(list);
    }

    /**
     * 分页查询
     */
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