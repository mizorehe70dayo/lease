package com.mahiru.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mahiru.lease.common.exception.LeaseException;
import com.mahiru.lease.common.result.ResultCodeEnum;
import com.mahiru.lease.common.utils.TableAssociationUtils;
import com.mahiru.lease.model.entity.*;
import com.mahiru.lease.model.enums.ItemType;
import com.mahiru.lease.web.admin.mapper.*;
import com.mahiru.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.mahiru.lease.web.admin.vo.fee.FeeValueVo;
import com.mahiru.lease.web.admin.vo.graph.GraphVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
@AllArgsConstructor
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    private final GraphInfoService graphInfoService;
    private final ApartmentFacilityService apartmentFacilityService;
    private final ApartmentLabelService apartmentLabelService;
    private final ApartmentFeeValueService apartmentFeeValueService;

    private final ApartmentInfoMapper apartmentInfoMapper;
    private final GraphInfoMapper graphInfoMapper;
    private final LabelInfoMapper labelInfoMapper;
    private final FacilityInfoMapper facilityInfoMapper;
    private final FeeValueMapper feeValueMapper;
    private final RoomInfoMapper roomInfoMapper;

    /**
     * @author mahiru
     * @date 2024/10/27 18:26
     * @methodName saveOrUpdateApartmentInfo
     * @description 新增或更新公寓信息
     * @param apartmentSubmitVo
     * @return void
     */
    @Override
    public void saveOrUpdateApartmentInfo(ApartmentSubmitVo apartmentSubmitVo) {
        if (apartmentSubmitVo.getId() == null) {
            saveApartmentInfo(apartmentSubmitVo);
        } else {
            updateApartmentInfoById(apartmentSubmitVo);
        }
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:27
     * @methodName pageApartmentItemByQueryVo
     * @description 根据查询条件分页查询公寓列表
     * @param apartmentItemVoPage
     * @param apartmentQueryVo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.mahiru.lease.web.admin.vo.apartment.ApartmentItemVo>
     */
    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQueryVo(Page<ApartmentItemVo> apartmentItemVoPage,
                                                             ApartmentQueryVo apartmentQueryVo) {
        return apartmentInfoMapper.selectPageApartmentItemByQuery(apartmentItemVoPage, apartmentQueryVo);
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:27
     * @methodName getApartmentDetailById
     * @description 根据id获取公寓详情
     * @param id
     * @return com.mahiru.lease.web.admin.vo.apartment.ApartmentDetailVo
     */
    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        // 1. 获取公寓信息(为空返回null)
        ApartmentInfo apartmentInfo = super.getById(id);
        if (apartmentInfo == null) {
            return null;
        }

        // 2. 获取公寓信息
        List<GraphVo> graphVoList = graphInfoMapper.selectApartmentGraphVoListByItemIdAndType(id, ItemType.APARTMENT);
        List<LabelInfo> labelInfoList = labelInfoMapper.selectApartmentlabelInfoListByApartmentId(id);
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectApartmentFacilityInfoListByApartmentId(id);
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectApartmentFeeValueVoListByApartmentId(id);

        // 3. 封装数据
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();

        BeanUtils.copyProperties(apartmentInfo, apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueVoList);

        return apartmentDetailVo;
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:27
     * @methodName removeApartmentInfoById
     * @description 根据id删除公寓信息
     * @param id
     * @return void
     */
    @Override
    public void removeApartmentInfoById(Long id) {
        // 1. 判断公寓下是否有房间
        if (!isApartmentRoomCountZero(id)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }

        // 2. 删除公寓信息
        removeApartmentGraphInfo(id);
        TableAssociationUtils.removeTableAssociation(ApartmentFacility::getApartmentId, id, apartmentFacilityService);
        TableAssociationUtils.removeTableAssociation(ApartmentLabel::getApartmentId, id, apartmentLabelService);
        TableAssociationUtils.removeTableAssociation(ApartmentFeeValue::getApartmentId, id, apartmentFeeValueService);

        super.removeById(id);
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:27
     * @methodName isApartmentRoomCountZero
     * @description 判断公寓下是否有房间
     * @param id
     * @return boolean
     */
    private boolean isApartmentRoomCountZero(Long id) {
        return roomInfoMapper.selectCount(new LambdaQueryWrapper<RoomInfo>()
                .eq(RoomInfo::getApartmentId, id)) == 0;
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:28
     * @methodName updateApartmentInfoById
     * @description 根据id更新公寓信息
     * @param apartmentSubmitVo
     * @return void
     */
    private void updateApartmentInfoById(ApartmentSubmitVo apartmentSubmitVo) {
        super.save(apartmentSubmitVo);
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:28
     * @methodName saveApartmentInfo
     * @description 保存公寓信息
     * @param apartmentSubmitVo
     * @return void
     */
    private void saveApartmentInfo(ApartmentSubmitVo apartmentSubmitVo) {
        Long apartmentId = apartmentSubmitVo.getId();

        // 1. 删除原有数据
        removeApartmentGraphInfo(apartmentId);
        TableAssociationUtils.removeTableAssociation(ApartmentFacility::getApartmentId, apartmentId, apartmentFacilityService);
        TableAssociationUtils.removeTableAssociation(ApartmentLabel::getApartmentId, apartmentId, apartmentLabelService);
        TableAssociationUtils.removeTableAssociation(ApartmentFeeValue::getApartmentId, apartmentId, apartmentFeeValueService);

        // 2. 插入数据
        insertGraphInfo(apartmentSubmitVo);
        insertFacilities(apartmentSubmitVo.getFacilityInfoIds(), apartmentId);
        insertLabels(apartmentSubmitVo.getLabelIds(), apartmentId);
        insertFeeValues(apartmentSubmitVo.getFeeValueIds(), apartmentId);
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:28
     * @methodName removeApartmentGraphInfo
     * @description 删除公寓图片信息
     * @param apartmentId
     * @return void
     */
    private void removeApartmentGraphInfo(Long apartmentId) {
        graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>()
                .eq(GraphInfo::getItemType, ItemType.APARTMENT)
                .eq(GraphInfo::getItemId, apartmentId));
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:29
     * @methodName insertGraphInfo
     * @description 插入公寓图片信息
     * @param apartmentSubmitVo
     * @return void
     */
    private void insertGraphInfo(ApartmentSubmitVo apartmentSubmitVo) {
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)) {
            List<GraphInfo> graphInfoList = graphVoList.stream()
                    .map(graphVo -> {
                        GraphInfo graphInfo = new GraphInfo();
                        graphInfo.setItemType(ItemType.APARTMENT);
                        graphInfo.setItemId(apartmentSubmitVo.getId());
                        graphInfo.setName(graphVo.getName());
                        graphInfo.setUrl(graphVo.getUrl());
                        return graphInfo;
                    })
                    .collect(Collectors.toList());
            graphInfoService.saveBatch(graphInfoList);
        }
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:29
     * @methodName insertFacilities
     * @description 插入公寓配套设施关系
     * @param facilityInfoIds
     * @param apartmentId
     * @return void
     */
    private void insertFacilities(List<Long> facilityInfoIds, Long apartmentId) {
        if (!CollectionUtils.isEmpty(facilityInfoIds)) {
            List<ApartmentFacility> facilityList = facilityInfoIds.stream()
                    .map(facilityId -> ApartmentFacility.builder()
                            .apartmentId(apartmentId)
                            .facilityId(facilityId)
                            .build())
                    .collect(Collectors.toList());
            apartmentFacilityService.saveBatch(facilityList);
        }
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:30
     * @methodName insertLabels
     * @description 插入公寓标签关系
     * @param labelIds
     * @param apartmentId
     * @return void
     */
    private void insertLabels(List<Long> labelIds, Long apartmentId) {
        if (!CollectionUtils.isEmpty(labelIds)) {
            List<ApartmentLabel> apartmentLabelList = labelIds.stream()
                    .map(labelId -> ApartmentLabel.builder()
                            .apartmentId(apartmentId)
                            .labelId(labelId)
                            .build())
                    .collect(Collectors.toList());
            apartmentLabelService.saveBatch(apartmentLabelList);
        }
    }

    /**
     * @author mahiru
     * @date 2024/10/27 18:30
     * @methodName insertFeeValues
     * @description 插入公寓杂费值关系
     * @param feeValueIds
     * @param apartmentId
     * @return void
     */
    private void insertFeeValues(List<Long> feeValueIds, Long apartmentId) {
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            List<ApartmentFeeValue> apartmentFeeValueList = feeValueIds.stream()
                    .map(feeValueId -> ApartmentFeeValue.builder()
                            .apartmentId(apartmentId)
                            .feeValueId(feeValueId)
                            .build())
                    .collect(Collectors.toList());
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }
    }
}




