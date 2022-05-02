package com.example.srb.core.service;

import com.example.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author fangzheng
 * @since 2022-04-20
 */
public interface DictService extends IService<Dict> {

    public void importData(InputStream inputStream);
}
