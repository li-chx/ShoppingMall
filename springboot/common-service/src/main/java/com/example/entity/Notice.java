package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("notice")
@Schema(description = "通知实体")
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "通知ID", example = "1")
    private Integer id;

    /** 通知标题 */
    @Schema(description = "通知标题", example = "系统维护通知")
    private String title;

    /** 通知内容 */
    @Schema(description = "通知内容", example = "系统将于2023年10月1日凌晨2点进行例行维护，预计需要2小时")
    private String content;

    /** 通知时间 */
    @Schema(description = "通知时间", example = "2023-09-28 15:30:00")
    private String time;
}