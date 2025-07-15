package com.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.dto.OrdersDTO;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.order.feign.BusinessFeignClient;
import com.example.order.feign.GoodsFeignClient;
import com.example.order.feign.UserFeignClient;
import com.example.order.service.OrdersService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService ordersService;
    @Resource
    private GoodsFeignClient goodsFeignClient;
    @Resource
    private BusinessFeignClient businessFeignClient;
    @Resource
    private UserFeignClient userFeignClient;

    /**
     * 新增
     */
    @SentinelResource(value = "orders_add")
    @PostMapping("/add")
    public R add(@RequestBody Orders orders) {
        ordersService.save(orders);
        return R.success();
    }

    /**
     * 删除
     */
    @SentinelResource(value = "orders_delete")
    @DeleteMapping("/delete/{id}")
    public R removeById(@PathVariable Integer id) {
        ordersService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "orders_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        ordersService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @SentinelResource(value = "orders_update")
    @PutMapping("/update")
    public R updateById(@RequestBody Orders orders) {
        ordersService.updateById(orders);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "orders_select_by_id")
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Orders orders = ordersService.getById(id);
        return R.success(orders);
    }

    /**
     * 查询所有
     */
    @SentinelResource(value = "orders_select_all")
    @GetMapping("/selectAll")
    public R selectAll(Orders orders) {
        List<Orders> list = ordersService.selectAll(orders);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "orders_select_page")
    @GetMapping("/selectPage")
    public R selectPage(Orders orders,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "") String goodsName) {
        // 如果goodsName不为空，则根据商品名称查询
        PageInfo<Orders> ordersPageInfo = null;
        if (!goodsName.isEmpty()) {
            PageInfo<Goods> page = goodsFeignClient.selectPageByName(pageNum, pageSize, goodsName).getData();
            var goodsIds = new ArrayList<Integer>(page.getList().stream().map(Goods::getId).toList());
            ordersPageInfo = ordersService.selectPageByGoodIds(goodsIds,pageNum,pageSize);
        } else {
            ordersPageInfo = ordersService.selectPage(orders, pageNum, pageSize);
        }
        List<OrdersDTO> ordersDTOList = ordersPageInfo.getList().stream().map(OrdersDTO::new).toList();
        ordersDTOList.forEach(ordersDTO -> {
            var goods = goodsFeignClient.selectGoodsById(ordersDTO.getGoodsId()).getData();
            if (goods != null) {
                ordersDTO.setGoodsName(goods.getName());
                ordersDTO.setGoodsPrice(goods.getPrice());
            }
            var business = businessFeignClient.getBusinessById(ordersDTO.getBusinessId()).getData();
            if (business != null) {
                ordersDTO.setBusinessName(business.getName());
            }
            var user = userFeignClient.selectById(ordersDTO.getUserId()).getData();
            if (user != null) {
                ordersDTO.setUsername(user.getUsername());
                ordersDTO.setPhone(user.getPhone());
            }
            var address = userFeignClient.getAddressById(ordersDTO.getAddressId()).getData();
            if (address != null) {
                ordersDTO.setUseraddress(address.getUseraddress());
            }
        });
        var orderDTOPageInfo =  new PageInfo<OrdersDTO>(ordersDTOList);
        orderDTOPageInfo.setPageNum(pageNum);
        orderDTOPageInfo.setPageSize(pageSize);
        orderDTOPageInfo.setTotal(ordersPageInfo.getTotal());
        return R.success(orderDTOPageInfo);
    }

} 