package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;

@tk.mybatis.mapper.annotation.RegisterMapper
public interface BaseMapper<T> extends Mapper<TbBrand>, InsertListMapper<TbBrand>, DeleteByIdsMapper<TbBrand> {
}
