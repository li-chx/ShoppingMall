package com.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Cart;
import com.example.dto.CartDTO;
import com.example.order.feign.BusinessFeignClient;
import com.example.order.feign.GoodsFeignClient;
import com.example.order.service.CartService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "购物车管理", description = "购物车相关操作接口")
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
    @Operation(summary = "添加购物车项", description = "向购物车添加商品")
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
    @Operation(summary = "删除购物车项", description = "根据ID删除购物车中的商品")
    public R deleteById(
            @Parameter(name = "id", description = "购物车项ID", required = true, in = ParameterIn.PATH)
            @PathVariable Integer id) {
        cartService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "cart_delete_batch")
    @DeleteMapping("/delete/batch")
    @Operation(summary = "批量删除购物车项", description = "根据ID列表批量删除购物车项")
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
    @Operation(summary = "更新购物车项", description = "修改购物车项信息，如数量等")
    public R updateById(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "cart_select_by_id")
    @GetMapping("/selectById/{id}")
    @Operation(summary = "查询单个购物车项", description = "根据ID查询购物车项详情")
    public R selectById(
            @Parameter(name = "id", description = "购物车项ID", required = true, in = ParameterIn.PATH)
            @PathVariable Integer id) {
        Cart cart = cartService.getById(id);
        return R.success(cart);
    }

    /**
     * 查询所有
     */
    @SentinelResource(value = "cart_select_all")
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有购物车项", description = "根据条件查询购物车项列表")
    public R selectAll(
            @Parameter(name = "cart", description = "购物车查询条件", in = ParameterIn.QUERY)
            Cart cart) {
        List<Cart> list = cartService.selectAll(cart);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "cart_select_page")
    @GetMapping("/selectPage")
    @GlobalTransactional
    @Operation(summary = "分页查询购物车", description = "分页查询购物车项，包含商品和商家信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY)
    })
    public R selectPage(
            @Parameter(name = "cart", description = "购物车查询条件", in = ParameterIn.QUERY)
            Cart cart,
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