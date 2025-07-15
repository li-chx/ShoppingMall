package com.example.dto;

import com.example.entity.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsDTO extends Goods {

    private String BusinessName;
    private String CategoryName;

    public GoodsDTO() {
        super();
    }

    public GoodsDTO(Goods goods) {
        super(goods);
    }

}
