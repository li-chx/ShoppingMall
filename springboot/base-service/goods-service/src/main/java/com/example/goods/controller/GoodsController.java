package com.example.goods.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.dto.GoodsDTO;
import com.example.goods.feign.BusinessFeignClient;
import com.example.goods.service.CategoryService;
import com.example.goods.service.GoodsService;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Tag(name = "商品接口", description = "商品信息管理相关接口")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private BusinessFeignClient businessFeignClient;

    @Resource
    DiscoveryClient discoveryClient; // 用于服务发现

    @Operation(summary = "新增商品", description = "添加新的商品信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_add")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "商品信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Goods.class)))
                 @RequestBody Goods goods) {
        goodsService.save(goods);
        return R.success();
    }

    @Operation(summary = "删除商品", description = "根据ID删除商品信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_delete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "商品ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        goodsService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除商品", description = "根据ID列表批量删除商品信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "商品ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        goodsService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改商品", description = "根据ID更新商品信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_update")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "商品信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Goods.class)))
                        @RequestBody Goods goods) {
        goodsService.updateById(goods);
        return R.success();
    }

    @Operation(summary = "更新商品销量", description = "根据商品ID更新商品的销量")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_update_gain_count")
    @PostMapping("/update/gain_count")
    public R updateGainCount(@Parameter(description = "商品ID", required = true, in = ParameterIn.QUERY)
                             @RequestParam Integer id,
                             @Parameter(description = "商品销量", required = true, in = ParameterIn.QUERY)
                             @RequestParam Integer gainCount) {
        if (goodsService.updateGainCount(id, gainCount)) {
            return R.success();
        } else {
            R<Object> result = R.error(ResultCodeEnum.PARAM_ERROR);
            result.setMsg("更新商品销量失败，请检查商品ID和销量值是否正确");
            return result;
        }
    }

    @Operation(summary = "根据ID查询商品", description = "获取指定ID的商品详细信息，包括商家和分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = GoodsDTO.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_select_by_id")
    @GlobalTransactional
    @GetMapping("/selectById/{id}")
    public R selectById(@Parameter(description = "商品ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        Goods goods = goodsService.getById(id);
        Business business = businessFeignClient.getBusinessById(goods.getBusinessId()).getData();
        Category category = categoryService.getById(goods.getCategoryId());
        GoodsDTO goodsDTO = new GoodsDTO(goods);
        goodsDTO.setBusinessName(business.getName());
        goodsDTO.setCategoryName(category.getName());
        return R.success(goodsDTO);
    }

    @Operation(summary = "查询所有商品", description = "根据条件获取所有商品信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Goods.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_select_all")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "商品查询条件", in = ParameterIn.QUERY)
                       Goods goods) {
        List<Goods> list = goodsService.selectAll(goods);
        return R.success(list);
    }

    @Operation(summary = "分页查询商品", description = "根据条件分页获取商品信息，包括商家和分类名称")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "name", description = "商品名称", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "")),
            @Parameter(name = "businessId", description = "商家ID", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "-1"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_select_page")
    @GlobalTransactional
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "商品查询条件", in = ParameterIn.QUERY)
                        Goods goods,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "") String name,
                        @RequestParam(defaultValue = "-1") Integer businessId) {
        log.info(businessId.toString());
        log.info("goodsBusinessId"+goods.getBusinessId());
        goods.setName(name);
        if(businessId != -1)
            goods.setBusinessId(businessId);
        PageInfo<Goods> page = goodsService.selectPage(goods, pageNum, pageSize);

        List<GoodsDTO> goodsDTOList=page.getList().stream()
                .map(GoodsDTO::new)
                .toList();

        Map<Integer, Category> categoryCache = goodsDTOList.stream()
                .map(GoodsDTO::getCategoryId)
                .distinct()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> categoryService.getById(id)
                ));

        goodsDTOList.forEach(item -> {
            var business = businessFeignClient.getBusinessById(item.getBusinessId()).getData();
            item.setBusinessName(business.getName());
            var category = categoryCache.get(item.getCategoryId());
            item.setCategoryName(category.getName());
        });
        var goodsDTOPage =  new PageInfo<>(goodsDTOList);
        goodsDTOPage.setTotal(page.getTotal());

        return R.success(goodsDTOPage);
    }

    @Operation(summary = "查询热门商品", description = "获取系统中前5个热门商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Goods.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_select_top5")
    @GetMapping("/selectTop5")
    public R selectTop5() {
        List<Goods> list = goodsService.selectTop5();
        return R.success(list);
    }

    @Operation(summary = "根据分类查询商品", description = "获取指定分类ID的所有商品")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Goods.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_select_by_category_id")
    @GetMapping("/selectByCategoryId/{id}")
    public R selectByCategoryId(@Parameter(description = "分类ID", required = true, in = ParameterIn.PATH)
                                @PathVariable Integer id) {
        List<Goods> list = goodsService.selectByCategoryId(id);
        return R.success(list);
    }

    @Operation(summary = "根据名称查询商品", description = "根据商品名称进行模糊查询并分页返回结果")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "goodsName", description = "商品名称", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_select_by_name")
    @GetMapping("/selectPageByName")
    public R<PageInfo<Goods>> selectPageByName(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "") String goodsName) {
        PageInfo<Goods> page = goodsService.selectPageByName(goodsName, pageNum, pageSize);
        return R.success(page);
    }

    @Operation(summary = "商品关键词搜索", description = "根据关键词对商品名进行模糊查询")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Goods.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "goods_search_by_keyword")
    @GetMapping("/search")
    public R searchByKeyword(@Parameter(description = "搜索关键词", required = true, in = ParameterIn.QUERY)
                             @RequestParam String keyword) {

        List<Goods> list = goodsService.selectByKeyword(keyword);
        return R.success(list);
    }





}