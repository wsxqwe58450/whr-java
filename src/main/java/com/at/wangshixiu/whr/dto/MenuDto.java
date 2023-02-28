package com.at.wangshixiu.whr.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @Author 王世秀
 * @Date 2023/2/28
 * @Description 返回给前端的菜单类
 */
@Data
public class MenuDto implements Serializable {
    private String id;
    private String menuName;
    private String parentId;
    private String componentUrl;
    private String type;
    private String buttonName;
    private String permissions;
    private List<MenuDto> children;
}
