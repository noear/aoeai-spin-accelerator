package com.aoeai.spin.accelerator.generate.persistent.bean;

import com.aoeai.spin.accelerator.generate.common.ITemplates;
import lombok.Data;

import java.io.File;

/**
 * @author aoe
 * @date 2020/6/21
 */
@Data
public class MapperXml implements ITemplates {

    /**
     * xml文件
     */
    private File file;

    /**
     * 模板文件路径
     */
    private String templates;

    private MapperClass mapperClass;
}
