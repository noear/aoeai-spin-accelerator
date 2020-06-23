package com.aoeai.spin.accelerator.generate.web.bean;

import lombok.Data;

import java.util.List;

/**
 * 表单字段
 */
@Data
public class FormField {

    /**
     * 字段类型：类名简称（无包名）
     */
    private String classShortName;

    /**
     * 字段类型：类名全称（有包名）
     */
    private String classFullName;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 效验标签列表
     */
    private List<String> checkTagList;
}
