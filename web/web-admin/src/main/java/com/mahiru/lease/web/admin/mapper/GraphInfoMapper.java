package com.mahiru.lease.web.admin.mapper;

import com.mahiru.lease.model.entity.GraphInfo;
import com.mahiru.lease.model.enums.ItemType;
import com.mahiru.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.mahiru.lease.model.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> selectApartmentGraphVoListByItemIdAndType(Long id, ItemType itemType);
}




