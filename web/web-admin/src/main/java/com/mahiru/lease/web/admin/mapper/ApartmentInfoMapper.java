package com.mahiru.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mahiru.lease.model.entity.ApartmentInfo;
import com.mahiru.lease.model.entity.GraphInfo;
import com.mahiru.lease.model.enums.ItemType;
import com.mahiru.lease.model.enums.LeaseStatus;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mahiru.lease.web.admin.vo.graph.GraphVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.mahiru.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    IPage<ApartmentItemVo> selectPageApartmentItemByQuery(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo apartmentQueryVo);
}




