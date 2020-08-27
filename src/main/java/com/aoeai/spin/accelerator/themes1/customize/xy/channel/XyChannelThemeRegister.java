package com.aoeai.spin.accelerator.themes1.customize.xy.channel;

import com.aoeai.spin.accelerator.generate1.IMapperFactory;
import com.aoeai.spin.accelerator.generate1.IPoFactory;
import com.aoeai.spin.accelerator.generate1.MapperFactory;
import com.aoeai.spin.accelerator.generate1.MapperXmlFactory;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.factory.XyChPageListQoFactory;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.factory.XyChServiceClassFactory;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.factory.XyChServiceImplClassFactory;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.factory.XyChVoFactory;
import com.aoeai.spin.accelerator.themes1.frame.AbstractThemeRegister;
import com.aoeai.spin.accelerator.themes1.frame.bean.Module;
import com.aoeai.spin.accelerator.themes1.frame.bean.ThemeType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 星芽-渠道模板注册
 * @author aoe
 * @date 2020/8/24
 */
@Component
public class XyChannelThemeRegister extends AbstractThemeRegister {

    @Resource
    private IPoFactory xyChannelPoFactory;

    /**
     * 获得模板类型
     *
     * @return
     */
    @Override
    protected ThemeType getThemeType() {
        ThemeType themeType = new ThemeType();
        themeType.setCode("xy-channel");
        themeType.setName("星芽-渠道");

        IMapperFactory mapperFactory = new MapperFactory(xyChannelPoFactory, "/themes/xy/channel1/config/mapper.yaml");
        XyChVoFactory voFactory = new XyChVoFactory(xyChannelPoFactory);
        XyChPageListQoFactory pageListQoFactory = new XyChPageListQoFactory(xyChannelPoFactory);
        XyChServiceClassFactory serviceClassFactory = new XyChServiceClassFactory(xyChannelPoFactory, voFactory, pageListQoFactory);
        XyChServiceImplClassFactory serviceImplClassFactory = new XyChServiceImplClassFactory(xyChannelPoFactory, serviceClassFactory, mapperFactory);

        List<Module> modules = Arrays.asList(
                new Module("PO", "持久化对象", xyChannelPoFactory),
                new Module("MapperClass", "Mybatis Mapper", mapperFactory),
                new Module("MapperXml", "Mybatis Mapper MXL",
                        new MapperXmlFactory(mapperFactory, "/themes/xy/channel1/config/mapper-xml.yaml")),
                new Module("Service", "服务接口", serviceClassFactory),
                new Module("ServiceImpl", "服务实现", serviceImplClassFactory),
                new Module("VO", "Web层返回对象", voFactory),
                new Module("PageListQO", "分页查询对象", pageListQoFactory)
        );

        themeType.setModules(modules);
        return themeType;
    }

}
