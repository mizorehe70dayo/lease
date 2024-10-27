package com.mahiru.lease.web.admin.service;

import com.mahiru.lease.model.entity.ApartmentInfo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    void saveOrUpdateApartmentInfo(ApartmentSubmitVo apartmentSubmitVo);

    IPage<ApartmentItemVo> pageApartmentItemByQueryVo(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo apartmentQueryVo);

    ApartmentDetailVo getApartmentDetailById(Long id);

    void removeApartmentInfoById(Long id);
}
