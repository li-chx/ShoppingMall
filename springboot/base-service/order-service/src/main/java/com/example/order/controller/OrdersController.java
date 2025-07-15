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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "订单接口", description = "订单管理相关接口")
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

    @Operation(summary = "新增订单", description = "添加新的订单信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_add")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "订单信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Orders.class)))
                 @RequestBody Orders orders) {
        log.info("Adding new order: {}", orders);
        orders.setOrderId(orders.generateOrderId());
        ordersService.save(orders);
        return R.success();
    }

    @Operation(summary = "删除订单", description = "根据ID删除订单信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_delete")
    @DeleteMapping("/delete/{id}")
    public R removeById(@Parameter(description = "订单ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        ordersService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除订单", description = "根据ID列表批量删除订单信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "订单ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        ordersService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改订单", description = "根据ID更新订单信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_update")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "订单信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Orders.class)))
                        @RequestBody Orders orders) {
        ordersService.updateById(orders);
        return R.success();
    }

    @Operation(summary = "查询订单", description = "根据ID获取订单详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Orders.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_select_by_id")
    @GetMapping("/selectById/{id}")
    public R selectById(@Parameter(description = "订单ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        Orders orders = ordersService.getById(id);
        return R.success(orders);
    }

    @Operation(summary = "查询所有订单", description = "根据条件获取所有订单信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Orders.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_select_all")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "订单查询条件", in = ParameterIn.QUERY)
                       Orders orders) {
        List<Orders> list = ordersService.selectAll(orders);
        return R.success(list);
    }

    @Operation(summary = "分页查询订单", description = "根据条件分页获取订单信息，包括商品、商家和用户信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "goodsName", description = "商品名称", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "")),
            @Parameter(name = "orderId", description = "订单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "")),
            @Parameter(name = "businessId", description = "商家ID", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "orders_select_page")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "订单查询条件", in = ParameterIn.QUERY) Orders orders,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "") String goodsName,
                        @RequestParam(defaultValue = "") String orderId,
                        @RequestParam(defaultValue = "0") Integer businessId) {
        // 如果goodsName不为空，则根据商品名称查询
        PageInfo<Orders> ordersPageInfo = null;
        if (!goodsName.isEmpty()) {
            PageInfo<Goods> page = goodsFeignClient.selectPageByName(pageNum, pageSize, goodsName).getData();
            var goodsIds = new ArrayList<Integer>(page.getList().stream().map(Goods::getId).toList());
            ordersPageInfo = ordersService.selectPageByGoodIds(goodsIds, pageNum, pageSize);
        } else {
            log.info("businessId: {}", businessId);
            orders.setOrderId(orderId);
            orders.setBusinessId(businessId);
            ordersPageInfo = ordersService.selectPage(orders, pageNum, pageSize);
        }
        List<OrdersDTO> ordersDTOList = ordersPageInfo.getList().stream().map(OrdersDTO::new).toList();
        ordersDTOList.forEach(ordersDTO -> {
            if (ordersDTO.getGoodsId() != null) {
                var goods = goodsFeignClient.selectGoodsById(ordersDTO.getGoodsId()).getData();
                if (goods != null) {
                    ordersDTO.setGoodsName(goods.getName());
                    ordersDTO.setGoodsPrice(goods.getPrice());
                    ordersDTO.setGoodsImg(goods.getImg());
                }
            }
            if (ordersDTO.getBusinessId() != null) {
                var business = businessFeignClient.getBusinessById(ordersDTO.getBusinessId()).getData();
                if (business != null) {
                    ordersDTO.setBusinessName(business.getName());
                }
            }
            if (ordersDTO.getUserId() != null) {
                var user = userFeignClient.selectById(ordersDTO.getUserId()).getData();
                if (user != null) {
                    ordersDTO.setUsername(user.getUsername());
                    ordersDTO.setPhone(user.getPhone());
                }
            }
            if (ordersDTO.getAddressId() != null) {
                var address = userFeignClient.getAddressById(ordersDTO.getAddressId()).getData();
                if (address != null) {
                    ordersDTO.setUseraddress(address.getUseraddress());
                }
            }
        });
        var orderDTOPageInfo = new PageInfo<OrdersDTO>(ordersDTOList);
        orderDTOPageInfo.setPageNum(pageNum);
        orderDTOPageInfo.setPageSize(pageSize);
        orderDTOPageInfo.setTotal(ordersPageInfo.getTotal());
        return R.success(orderDTOPageInfo);
    }
}